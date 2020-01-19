package org.princelle.lp1project.Routes;

import org.apache.tomcat.util.json.JSONParser;
import org.princelle.lp1project.Entities.Colocation;
import org.princelle.lp1project.Entities.Task;
import org.princelle.lp1project.Exceptions.ResourceNotFoundException;
import org.princelle.lp1project.Repositories.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import javax.ws.rs.*;
import java.util.List;

@Component
@Path("/api")
public class TaskResource {

	@Autowired
	private TaskRepository taskRepository;

	@GET
	@Produces("application/json")
	@Path("/tasks")
	public List<Task> getAllTasks() {
		return taskRepository.findAll();
	}

	@GET
	@Produces("application/json")
	@Path("/tasks/{id}")
	public ResponseEntity<Task> getTaskById(@PathParam(value = "id") Long task_id) throws ResourceNotFoundException {
		Task task = taskRepository.findById(task_id)
				.orElseThrow(() -> new ResourceNotFoundException("Task not found :: " + task_id));
		return ResponseEntity.ok().body(task);
	}

	@POST
	@Produces("application/json")
	@Consumes("application/json")
	@Path("/tasks")
	@PostMapping("/tasks")
	public Task createTask(Task service) {
		return taskRepository.save(service);
	}
}
