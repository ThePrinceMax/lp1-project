package org.princelle.lp1project.Routes;

import org.apache.tomcat.util.json.JSONParser;
import org.princelle.lp1project.Entities.Colocation;
import org.princelle.lp1project.Entities.Person;
import org.princelle.lp1project.Entities.Task;
import org.princelle.lp1project.Exceptions.ResourceNotFoundException;
import org.princelle.lp1project.Repositories.ColocationRepository;
import org.princelle.lp1project.Repositories.PersonRepository;
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

	@Autowired
	private ColocationRepository colocationRepository;

	@Autowired
	private PersonRepository personRepository;

	@GET
	@Produces("application/json")
	@Path("/tasks")
	public List<Task> getAllTasks() {
		return taskRepository.findAll();
	}

	@GET
	@Produces("application/json")
	@Path("/tasks/{id}")
	public Task getTaskById(@PathParam(value = "id") Long task_id) throws ResourceNotFoundException {
		Task task = taskRepository.findById(task_id)
				.orElseThrow(() -> new ResourceNotFoundException("Task not found :: " + task_id));
		return task;
	}

	@GET
	@Produces("application/json")
	@Path("/tasks/coloc/{colocId}")
	public List<Task> getAllTasksbyColoc(@PathParam(value = "colocId") Long colocId) throws ResourceNotFoundException {
		Colocation coloc = colocationRepository.findById(colocId)
				.orElseThrow(() -> new ResourceNotFoundException("Colocation not found :: " + colocId));

		return taskRepository.findAllByColoc(coloc);
	}

	@GET
	@Produces("application/json")
	@Path("/tasks/pending")
	public List<Task> getAllPendingTasks() {
		return taskRepository.findTasksByFinishDateIsNull();
	}

	@GET
	@Produces("application/json")
	@Path("/tasks/coloc/{colocId}/pending")
	public List<Task> getAllPendingTasksbyColoc(@PathParam(value = "colocId") Long colocId) throws ResourceNotFoundException {
		Colocation coloc = colocationRepository.findById(colocId)
				.orElseThrow(() -> new ResourceNotFoundException("Colocation not found :: " + colocId));

		return taskRepository.findTasksByColocAndFinishDateIsNull(coloc);
	}

	@GET
	@Produces("application/json")
	@Path("/tasks/proposed")
	public List<Task> getAllProposedTasks() {
		return taskRepository.findTasksByProposedIsTrue();
	}

	@GET
	@Produces("application/json")
	@Path("/tasks/coloc/{colocId}/proposed")
	public List<Task> getAllProposedTasksbyColoc(@PathParam(value = "colocId") Long colocId) throws ResourceNotFoundException {
		Colocation coloc = colocationRepository.findById(colocId)
				.orElseThrow(() -> new ResourceNotFoundException("Colocation not found :: " + colocId));

		return taskRepository.findTasksByProposedIsTrueAndColoc(coloc);
	}

	@GET
	@Produces("application/json")
	@Path("/tasks/users/{userId}/proposed")
	public List<Task> getAllProposedTasksToUser(@PathParam(value = "userId") Long userId) throws ResourceNotFoundException {
		Person user = personRepository.findById(userId)
				.orElseThrow(() -> new ResourceNotFoundException("Person not found :: " + userId));

		return taskRepository.findTasksByProposedIsTrueAndToPerson(user);
	}

	@GET
	@Produces("application/json")
	@Path("/tasks/users/{userId}/pending")
	public List<Task> getAllPendingTasksToUser(@PathParam(value = "userId") Long userId) throws ResourceNotFoundException {
		Person user = personRepository.findById(userId)
				.orElseThrow(() -> new ResourceNotFoundException("Person not found :: " + userId));

		return taskRepository.findTasksByToPersonAndFinishDateIsNull(user);
	}

	@GET
	@Produces("application/json")
	@Path("/tasks/users/{userId}")
	public List<Task> getAllTasksToUser(@PathParam(value = "userId") Long userId) throws ResourceNotFoundException {
		Person user = personRepository.findById(userId)
				.orElseThrow(() -> new ResourceNotFoundException("Person not found :: " + userId));

		return taskRepository.findAllByToPerson(user);
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
