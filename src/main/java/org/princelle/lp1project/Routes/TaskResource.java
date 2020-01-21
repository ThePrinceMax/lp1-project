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
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin(origins = "*")
public class TaskResource {

	@Autowired
	private TaskRepository taskRepository;

	@Autowired
	private ColocationRepository colocationRepository;

	@Autowired
	private PersonRepository personRepository;

	@GetMapping(value = "/api/tasks", produces = "application/json")
	public List<Task> getAllTasks() {
		return taskRepository.findAll();
	}

	@GetMapping(value = "/api/tasks/{id}", produces = "application/json")
	public Task getTaskById(@PathVariable(value = "id") Long task_id) throws ResourceNotFoundException {
		Task task = taskRepository.findById(task_id)
				.orElseThrow(() -> new ResourceNotFoundException("Task not found :: " + task_id));
		return task;
	}

	@GetMapping(value = "/api/tasks/coloc/{colocId}", produces = "application/json")
	public List<Task> getAllTasksbyColoc(@PathVariable(value = "colocId") Long colocId) throws ResourceNotFoundException {
		Colocation coloc = colocationRepository.findById(colocId)
				.orElseThrow(() -> new ResourceNotFoundException("Colocation not found :: " + colocId));

		return taskRepository.findAllByColoc(coloc);
	}

	@GetMapping(value = "/api/tasks/not_attributed", produces = "application/json")
	public List<Task> getAllTasksNotAttributed() {
		return taskRepository.findTasksByToPersonIsNull();
	}

	@GetMapping(value = "/api/tasks/coloc/{colocId}/not_attributed", produces = "application/json")
	public List<Task> getAllTasksNotAttributedbyColoc(@PathVariable(value = "colocId") Long colocId) throws ResourceNotFoundException {
		Colocation coloc = colocationRepository.findById(colocId)
				.orElseThrow(() -> new ResourceNotFoundException("Colocation not found :: " + colocId));

		return taskRepository.findTasksByColocAndToPersonIsNull(coloc);
	}

	@GetMapping(value = "/api/tasks/pending", produces = "application/json")
	public List<Task> getAllPendingTasks() {
		return taskRepository.findTasksByFinishDateIsNull();
	}

	@GetMapping(value = "/api/tasks/coloc/{colocId}/pending", produces = "application/json")
	public List<Task> getAllPendingTasksByColoc(@PathVariable(value = "colocId") Long colocId) throws ResourceNotFoundException {
		Colocation coloc = colocationRepository.findById(colocId)
				.orElseThrow(() -> new ResourceNotFoundException("Colocation not found :: " + colocId));

		return taskRepository.findTasksByColocAndFinishDateIsNull(coloc);
	}

	@GetMapping(value = "/api/tasks/proposed", produces = "application/json")
	public List<Task> getAllProposedTasks() {
		return taskRepository.findTasksByProposedIsTrue();
	}

	@GetMapping(value = "/api/tasks/coloc/{colocId}/proposed", produces = "application/json")
	public List<Task> getAllProposedTasksByColoc(@PathVariable(value = "colocId") Long colocId) throws ResourceNotFoundException {
		Colocation coloc = colocationRepository.findById(colocId)
				.orElseThrow(() -> new ResourceNotFoundException("Colocation not found :: " + colocId));

		return taskRepository.findTasksByProposedIsTrueAndColoc(coloc);
	}

	@GetMapping(value = "/api/tasks/users/to/{userId}/proposed", produces = "application/json")
	public List<Task> getAllProposedTasksToUser(@PathVariable(value = "userId") Long userId) throws ResourceNotFoundException {
		Person user = personRepository.findById(userId)
				.orElseThrow(() -> new ResourceNotFoundException("Person not found :: " + userId));

		return taskRepository.findTasksByProposedIsTrueAndToPerson(user);
	}

	@GetMapping(value = "/api/tasks/users/to/{userId}/pending", produces = "application/json")
	public List<Task> getAllPendingTasksToUser(@PathVariable(value = "userId") Long userId) throws ResourceNotFoundException {
		Person user = personRepository.findById(userId)
				.orElseThrow(() -> new ResourceNotFoundException("Person not found :: " + userId));

		return taskRepository.findTasksByToPersonAndFinishDateIsNull(user);
	}

	@GetMapping(value = "/api/tasks/users/to/{userId}", produces = "application/json")
	public List<Task> getAllTasksToUser(@PathVariable(value = "userId") Long userId) throws ResourceNotFoundException {
		Person user = personRepository.findById(userId)
				.orElseThrow(() -> new ResourceNotFoundException("Person not found :: " + userId));

		return taskRepository.findAllByToPerson(user);
	}

	@GetMapping(value = "/api/tasks/users/from/{userId}/proposed", produces = "application/json")
	public List<Task> getAllProposedTasksFromUser(@PathVariable(value = "userId") Long userId) throws ResourceNotFoundException {
		Person user = personRepository.findById(userId)
				.orElseThrow(() -> new ResourceNotFoundException("Person not found :: " + userId));

		return taskRepository.findTasksByProposedIsTrueAndFromPerson(user);
	}

	@GetMapping(value = "/api/tasks/users/from/{userId}/pending", produces = "application/json")
	public List<Task> getAllPendingTasksFromUser(@PathVariable(value = "userId") Long userId) throws ResourceNotFoundException {
		Person user = personRepository.findById(userId)
				.orElseThrow(() -> new ResourceNotFoundException("Person not found :: " + userId));

		return taskRepository.findTasksByFromPersonAndFinishDateIsNull(user);
	}

	@GetMapping(value = "/api/tasks/users/from/{userId}", produces = "application/json")
	public List<Task> getAllTasksFromUser(@PathVariable(value = "userId") Long userId) throws ResourceNotFoundException {
		Person user = personRepository.findById(userId)
				.orElseThrow(() -> new ResourceNotFoundException("Person not found :: " + userId));

		return taskRepository.findAllByFromPerson(user);
	}

	@PostMapping(value = "/api/tasks", produces = "application/json", consumes = "application/json")
	public Task createTask(@Valid @RequestBody Task service) {
		return taskRepository.save(service);
	}

	@PutMapping(value = "/api/tasks/{id}", produces = "application/json", consumes = "application/json")
	public Task updateTask(@PathVariable(value = "id") Long taskId,
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

	@PutMapping(value = "/api/tasks/{id}/valid/to", produces = "application/json", consumes = "application/json")
	public Task validTo(@PathVariable(value = "id") Long taskId) throws ResourceNotFoundException {
		Task service = taskRepository.findById(taskId)
				.orElseThrow(() -> new ResourceNotFoundException("Task not found :: " + taskId));

		if (service.getValidFrom() == false) {
			Date today = new Date();
			service.setFinishDate(today);

			service.setValidTo(true);
		}

		return taskRepository.save(service);
	}

	@PutMapping(value = "/api/tasks/{id}/valid/from", produces = "application/json", consumes = "application/json")
	public Task validFrom(@PathVariable(value = "id") Long taskId) throws ResourceNotFoundException {
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

	@PutMapping(value = "/api/tasks/{id}/valid/to/rev", produces = "application/json", consumes = "application/json")
	public Task validToRev(@PathVariable(value = "id") Long taskId) throws ResourceNotFoundException {
		Task service = taskRepository.findById(taskId)
				.orElseThrow(() -> new ResourceNotFoundException("Task not found :: " + taskId));

		if (!service.getValidFrom()){
			service.setFinishDate(null);

			service.setValidTo(false);

			service.setValidFrom(false);
		}

		return taskRepository.save(service);
	}

	@PutMapping(value = "/api/tasks/{id}/valid/from/rev", produces = "application/json", consumes = "application/json")
	public Task validFromRev(@PathVariable(value = "id") Long taskId) throws ResourceNotFoundException {
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

	@DeleteMapping(value="/api/tasks/{id}", produces = "application/json")
	public Map<String, Boolean> deleteTask(@PathVariable(value = "id") Long taskId) throws ResourceNotFoundException {
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

	@PostMapping(value = "/api/tasks/{id}/uploadImage", produces = "application/json")
	public Task uploadImage(@PathVariable(value = "id") Long taskId, @RequestParam("file") MultipartFile file) throws ResourceNotFoundException {
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
