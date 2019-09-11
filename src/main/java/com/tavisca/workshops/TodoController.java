package com.tavisca.workshops;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(origins = "*", allowedHeaders="*")	
@RequestMapping("/api/v1")

public class TodoController {

	public JSONArray tasks;

	public TodoController() {
		this.tasks = new JSONArray();
	}

	@GetMapping("/demo")
	public String demo() {
		try {
			return (new JSONObject("{ 'A' : 'b' }")).toString();
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	@GetMapping("/tasks")
	public String getAllTasks() {
		return this.tasks.toString();
	}

	@PutMapping("/tasks/add")
	public String addNewTask(@RequestBody String todo) {
		int length = this.tasks.length();
		boolean exists = false;
		try {
			JSONObject abc = new JSONObject(todo);
			for (int i = 0; i < length; i++) {
				JSONObject temp = (JSONObject) this.tasks.get(i);
				if (temp.getString("name").equals(abc.getString("name"))) {
					exists = true;
				}

			}
			if (!exists) {
				this.tasks.put(abc);
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return this.tasks.toString();
	}

	@DeleteMapping("/tasks/delete")
	public String deleteTask(@RequestBody String todo) {
		JSONArray newArr = new JSONArray();
		int length = this.tasks.length();
		for (int i = 0; i < length; i++) {
			try {
				JSONObject abc = new JSONObject(todo);
				JSONObject temp = (JSONObject) this.tasks.get(i);
				if (!temp.getString("name").equals(abc.getString("name"))) {
					newArr.put(temp);
				}
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		this.tasks = newArr;
		return this.tasks.toString();
	}

	@PatchMapping("tasks/update")
	public String updateTask(@RequestBody String requestData) {
		JSONArray newArr = new JSONArray();

		int length = this.tasks.length();

		try {
			JSONObject abc = new JSONObject(requestData);
			for (int i = 0; i < length; i++) {
				JSONObject temp = (JSONObject) this.tasks.get(i);
				if (temp.getString("name").equals(abc.getString("previousName"))) {
					newArr.put(new JSONObject("{'name':" +abc.getString("updatedName") + "}"));
				} else {
					newArr.put(temp);
				}
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		this.tasks = newArr;
		return this.tasks.toString();
	}
}
