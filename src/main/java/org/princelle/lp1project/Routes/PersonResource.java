package org.princelle.lp1project.Routes;

import org.princelle.lp1project.Entities.Person;
import org.princelle.lp1project.Entities.Task;
import org.princelle.lp1project.Exceptions.AlreadyExistsException;
import org.princelle.lp1project.Exceptions.ResourceNotFoundException;
import org.princelle.lp1project.Repositories.PersonRepository;
import org.princelle.lp1project.Repositories.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api")
public class PersonResource {

	@Autowired
	private PersonRepository personRepository;

	@Autowired
	private TaskRepository taskRepository;

	private BCryptPasswordEncoder bCryptPasswordEncoder;

	public PersonResource(PersonRepository applicationUserRepository,
						  BCryptPasswordEncoder bCryptPasswordEncoder,
						  TaskRepository taskRepository) {
		this.personRepository = applicationUserRepository;
		this.taskRepository = taskRepository;
		this.bCryptPasswordEncoder = bCryptPasswordEncoder;
	}

	@GetMapping(value = "/", produces = "application/json")
	public String hello() {
		return "Hello ! Welcome on the API for the LP1 Project.";
	}

	@GetMapping(value = "/users", produces = "application/json")
	public List<Person> getAllUsers() {
		return personRepository.findAll();
	}

	@GetMapping(value = "/users/{id}", produces = "application/json")
	public Person getUserById(@PathVariable(value = "id") Long userId) throws ResourceNotFoundException {
		Person person = personRepository.findById(userId)
				.orElseThrow(() -> new ResourceNotFoundException("User not found :: " + userId));
		return person;
	}

	@GetMapping(value = "/users/pseudo/{pseudo}", produces = "application/json")
	public Person getUserByPseudo(@PathVariable(value = "pseudo") String pseudo) throws ResourceNotFoundException  {
		if (!personRepository.existsPersonByPseudo(pseudo)) {
			throw new ResourceNotFoundException("User not found w/ pseudo :: " + pseudo);
		}

		return personRepository.findPersonByPseudo(pseudo);
	}

	@GetMapping(value = "/users/nocoloc", produces = "application/json")
	public List<Person> getPersonWithNoColoc() {
		return personRepository.findByColocIsNull();
	}

	@PostMapping(value = "/signup", produces = "application/json", consumes = "application/json")
	public Person createUser(@Valid @RequestBody Person person) throws AlreadyExistsException {
		person.setPassword(bCryptPasswordEncoder.encode(person.getPassword()));
		if (personRepository.existsPersonByEmailId(person.getEmailId())) {
			throw new AlreadyExistsException("Person with emailId :: " + person.getEmailId() + " already exists.");
		}
		if (personRepository.existsPersonByPseudo(person.getPseudo())) {
			throw new AlreadyExistsException("Person with pseudo :: " + person.getPseudo() + " already exists.");
		}
		return personRepository.save(person);
	}

	@PutMapping(value = "/users/{id}", produces = "application/json", consumes = "application/json")
	public ResponseEntity<Person> updateUser(@PathVariable(value = "id") Long userId,
											 @Valid @RequestBody Person personDetails) throws ResourceNotFoundException {
		Person person = personRepository.findById(userId)
				.orElseThrow(() -> new ResourceNotFoundException("User not found :: " + userId));

		if (personDetails.getEmailId() != null){
			person.setEmailId(personDetails.getEmailId());
		}

		if (personDetails.getFirstName() != null){
			person.setFirstName(personDetails.getFirstName());
		}

		if (personDetails.getLastName() != null){
			person.setLastName(personDetails.getLastName());
		}

		if (personDetails.getScore() != null){
			person.setScore(personDetails.getScore());
		}

		if (personDetails.getPseudo() != null){
			person.setPseudo(personDetails.getPseudo());
		}

		if (personDetails.getPassword() != null){
			person.setPassword(personDetails.getPassword());
		}

		final Person personEdited = personRepository.save(person);
		return ResponseEntity.ok(personEdited);
	}

	@DeleteMapping(value = "/users/{id}", produces = "application/json")
	public Map<String, Boolean> deleteUser(@PathVariable(value = "id") Long userId) throws ResourceNotFoundException {
		Person person = personRepository.findById(userId)
				.orElseThrow(() -> new ResourceNotFoundException("User not found :: " + userId));

		for (Task task: taskRepository.findAllByToPerson(person)) {
			task.setToPerson(null);
			taskRepository.save(task);
		}

		for (Task task: taskRepository.findAllByFromPerson(person)) {
			task.setFromPerson(null);
			taskRepository.save(task);
		}

		personRepository.delete(person);
		Map<String, Boolean> response = new HashMap<>();
		response.put("deleted", Boolean.TRUE);
		return response;
	}
}
