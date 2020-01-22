package org.princelle.lp1project.Routes;

import org.princelle.lp1project.Entities.Person;
import org.princelle.lp1project.Exceptions.ResourceNotFoundException;
import org.princelle.lp1project.Repositories.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
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
	public Person getUserByPseudo(@PathVariable(value = "pseudo") String pseudo) {
		return personRepository.findPersonByPseudo(pseudo);
	}

	@GetMapping(value = "/users/nocoloc", produces = "application/json")
	public List<Person> getPersonWithNoColoc() {
		return personRepository.findByColocIsNull();
	}

	@PostMapping(value = "/signup", produces = "application/json", consumes = "application/json")
	public Person createUser(@Valid @RequestBody Person person) {
		return personRepository.save(person);
	}

	@GetMapping(value = "/users/{id}", produces = "application/json", consumes = "application/json")
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

		final Person personEdited = personRepository.save(person);
		return ResponseEntity.ok(personEdited);
	}

	@DeleteMapping(value = "/users/{id}", produces = "application/json")
	public Map<String, Boolean> deleteUser(@PathVariable(value = "id") Long userId) throws ResourceNotFoundException {
		Person person = personRepository.findById(userId)
				.orElseThrow(() -> new ResourceNotFoundException("User not found :: " + userId));

		personRepository.delete(person);
		Map<String, Boolean> response = new HashMap<>();
		response.put("deleted", Boolean.TRUE);
		return response;
	}
}