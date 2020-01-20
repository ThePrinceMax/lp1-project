package org.princelle.lp1project.Routes;

import org.princelle.lp1project.Entities.Colocation;
import org.princelle.lp1project.Entities.Person;
import org.princelle.lp1project.Entities.Task;
import org.princelle.lp1project.Exceptions.ResourceNotFoundException;
import org.princelle.lp1project.Repositories.ColocationRepository;
import org.princelle.lp1project.Repositories.PersonRepository;
import org.princelle.lp1project.Repositories.TaskRepository;
import org.princelle.lp1project.Utils.StorageManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import javax.ws.rs.*;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
	@Path("/tasks/not_attributed")
	public List<Task> getAllTasksNotAttributed() {
		return taskRepository.findTasksByToPersonIsNull();
	}

	@GET
	@Produces("application/json")
	@Path("/tasks/coloc/{colocId}/not_attributed")
	public List<Task> getAllTasksNotAttributedbyColoc(@PathParam(value = "colocId") Long colocId) throws ResourceNotFoundException {
		Colocation coloc = colocationRepository.findById(colocId)
				.orElseThrow(() -> new ResourceNotFoundException("Colocation not found :: " + colocId));

		return taskRepository.findTasksByColocAndToPersonIsNull(coloc);
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
	public List<Task> getAllPendingTasksByColoc(@PathParam(value = "colocId") Long colocId) throws ResourceNotFoundException {
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
	public List<Task> getAllProposedTasksByColoc(@PathParam(value = "colocId") Long colocId) throws ResourceNotFoundException {
		Colocation coloc = colocationRepository.findById(colocId)
				.orElseThrow(() -> new ResourceNotFoundException("Colocation not found :: " + colocId));

		return taskRepository.findTasksByProposedIsTrueAndColoc(coloc);
	}

	@GET
	@Produces("application/json")
	@Path("/tasks/users/to/{userId}/proposed")
	public List<Task> getAllProposedTasksToUser(@PathParam(value = "userId") Long userId) throws ResourceNotFoundException {
		Person user = personRepository.findById(userId)
				.orElseThrow(() -> new ResourceNotFoundException("Person not found :: " + userId));

		return taskRepository.findTasksByProposedIsTrueAndToPerson(user);
	}

	@GET
	@Produces("application/json")
	@Path("/tasks/users/to/{userId}/pending")
	public List<Task> getAllPendingTasksToUser(@PathParam(value = "userId") Long userId) throws ResourceNotFoundException {
		Person user = personRepository.findById(userId)
				.orElseThrow(() -> new ResourceNotFoundException("Person not found :: " + userId));

		return taskRepository.findTasksByToPersonAndFinishDateIsNull(user);
	}

	@GET
	@Produces("application/json")
	@Path("/tasks/users/to/{userId}")
	public List<Task> getAllTasksToUser(@PathParam(value = "userId") Long userId) throws ResourceNotFoundException {
		Person user = personRepository.findById(userId)
				.orElseThrow(() -> new ResourceNotFoundException("Person not found :: " + userId));

		return taskRepository.findAllByToPerson(user);
	}

	@GET
	@Produces("application/json")
	@Path("/tasks/users/from/{userId}/proposed")
	public List<Task> getAllProposedTasksFromUser(@PathParam(value = "userId") Long userId) throws ResourceNotFoundException {
		Person user = personRepository.findById(userId)
				.orElseThrow(() -> new ResourceNotFoundException("Person not found :: " + userId));

		return taskRepository.findTasksByProposedIsTrueAndFromPerson(user);
	}

	@GET
	@Produces("application/json")
	@Path("/tasks/users/from/{userId}/pending")
	public List<Task> getAllPendingTasksFromUser(@PathParam(value = "userId") Long userId) throws ResourceNotFoundException {
		Person user = personRepository.findById(userId)
				.orElseThrow(() -> new ResourceNotFoundException("Person not found :: " + userId));

		return taskRepository.findTasksByFromPersonAndFinishDateIsNull(user);
	}

	@GET
	@Produces("application/json")
	@Path("/tasks/users/from/{userId}")
	public List<Task> getAllTasksFromUser(@PathParam(value = "userId") Long userId) throws ResourceNotFoundException {
		Person user = personRepository.findById(userId)
				.orElseThrow(() -> new ResourceNotFoundException("Person not found :: " + userId));

		return taskRepository.findAllByFromPerson(user);
	}

	@POST
	@Produces("application/json")
	@Consumes("application/json")
	@Path("/tasks")
	@PostMapping("/tasks")
	public Task createTask(Task service) {
		return taskRepository.save(service);
	}

	@PUT
	@Produces("application/json")
	@Consumes("application/json")
	@Path("/tasks/{id}")
	public Task updateTask(@PathParam(value = "id") Long taskId,
											 @Valid @RequestBody Task taskDetails) throws ResourceNotFoundException {
		Task task = taskRepository.findById(taskId)
				.orElseThrow(() -> new ResourceNotFoundException("Task not found :: " + taskId));

		if (task.getFinishDate() == null) { // Task not achieved
			if (taskDetails.getTitle() != null){
				task.setTitle(taskDetails.getTitle());
			}
			if (taskDetails.getDescription() != null){
				task.setDescription(taskDetails.getDescription());
			}
			if (taskDetails.getCost() != null){
				task.setCost(taskDetails.getCost());
			}
			if (taskDetails.getProposed() != null) {
				task.setProposed(taskDetails.getProposed());
			}
			if (taskDetails.getColoc() != null){
				task.setColoc(taskDetails.getColoc());
			}
			if (taskDetails.getFromPerson() != null){
				task.setFromPerson(taskDetails.getFromPerson());
			}
			if (taskDetails.getToPerson() != null){
				task.setToPerson(taskDetails.getToPerson());
			}
		} else {
			if (taskDetails.getPicture() != null){
				task.setPicture(taskDetails.getPicture());
			}
		}

		return taskRepository.save(task);
	}

	@PUT
	@Produces("application/json")
	@Consumes("application/json")
	@Path("/tasks/{id}/valid/to")
	public Task validTo(@PathParam(value = "id") Long taskId) throws ResourceNotFoundException {
		Task service = taskRepository.findById(taskId)
				.orElseThrow(() -> new ResourceNotFoundException("Task not found :: " + taskId));

		if (service.getValidFrom() == false) {
			Date today = new Date();
			service.setFinishDate(today);

			service.setValidTo(true);
		}

		return taskRepository.save(service);
	}

	@PUT
	@Produces("application/json")
	@Consumes("application/json")
	@Path("/tasks/{id}/valid/from")
	public Task validFrom(@PathParam(value = "id") Long taskId) throws ResourceNotFoundException {
		Task service = taskRepository.findById(taskId)
				.orElseThrow(() -> new ResourceNotFoundException("Task not found :: " + taskId));

		if (service.getValidTo() && !service.getValidFrom()) {
			Person personTo = service.getToPerson();
			personTo.setScore(personTo.getScore() + service.getCost());

			personRepository.save(personTo);
		}

		if (!service.getValidFrom()){
			Date today = new Date();
			service.setFinishDate(today);
		}

		service.setValidFrom(true);

		return taskRepository.save(service);
	}

	@PUT
	@Produces("application/json")
	@Consumes("application/json")
	@Path("/tasks/{id}/valid/to/rev")
	public Task validToRev(@PathParam(value = "id") Long taskId) throws ResourceNotFoundException {
		Task service = taskRepository.findById(taskId)
				.orElseThrow(() -> new ResourceNotFoundException("Task not found :: " + taskId));

		if (!service.getValidFrom()){
			service.setFinishDate(null);

			service.setValidTo(false);

			service.setValidFrom(false);
		}

		return taskRepository.save(service);
	}

	@PUT
	@Produces("application/json")
	@Consumes("application/json")
	@Path("/tasks/{id}/valid/from/rev")
	public Task validFromRev(@PathParam(value = "id") Long taskId) throws ResourceNotFoundException {
		Task service = taskRepository.findById(taskId)
				.orElseThrow(() -> new ResourceNotFoundException("Task not found :: " + taskId));

		service.setValidFrom(false);

		service.setFinishDate(null);

		if (service.getValidTo()) {
			service.setValidTo(false);

			Person personTo = service.getToPerson();
			personTo.setScore(personTo.getScore() - service.getCost());

			personRepository.save(personTo);
		}

		return taskRepository.save(service);
	}

	@DELETE
	@Produces("application/json")
	@Path("/tasks/{id}")
	public Map<String, Boolean> deleteTask(@PathParam(value = "id") Long taskId) throws ResourceNotFoundException {
		Task task = taskRepository.findById(taskId)
				.orElseThrow(() -> new ResourceNotFoundException("Task not found :: " + taskId));

		// We remove score from To User if Task removed
		if (task.getFinishDate() != null) {
			Person personTo = task.getToPerson();
			Integer score = personTo.getScore();
			personTo.setScore(score - task.getCost());
			personRepository.save(personTo);
		}

		taskRepository.delete(task);
		Map<String, Boolean> response = new HashMap<>();
		response.put("deleted", Boolean.TRUE);
		return response;
	}

	@POST
	@Produces("application/json")
	@Path("/tasks/{id}/image")
	@PostMapping("/tasks/{id}/uploadImage")
	public Task uploadImage(@PathParam(value = "id") Long taskId, @RequestParam("file") MultipartFile file) throws ResourceNotFoundException {
		Task task = taskRepository.findById(taskId)
				.orElseThrow(() -> new ResourceNotFoundException("Task not found :: " + taskId));

		String url = "";

		try {
			url = StorageManager.upload(file, task);
		} catch (Exception e) {
			e.printStackTrace();
		}

		task.setPicture(url);

		return taskRepository.save(task);
	}
}
