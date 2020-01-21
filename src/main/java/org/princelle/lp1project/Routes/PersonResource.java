package org.princelle.lp1project.Routes;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;
import javax.ws.rs.*;

import org.princelle.lp1project.Entities.Person;
import org.princelle.lp1project.Repositories.PersonRepository;
import org.princelle.lp1project.Exceptions.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Component
@Path("/api")
public class PersonResource {

	@Autowired
	private PersonRepository personRepository;

	@GET
	@Produces("application/json")
	@Path("/")
	public String hello() {
		return "Hello ! Welcome on the API for the LP1 Project.";
	}

	@GET
	@Produces("application/json")
	@Path("/users")
	public List<Person> getAllUsers() {
		return personRepository.findAll();
	}

	@GET
	@Produces("application/json")
	@Path("/users/{id}")
	public Person getUserById(@PathParam(value = "id") Long userId) throws ResourceNotFoundException {
		Person person = personRepository.findById(userId)
				.orElseThrow(() -> new ResourceNotFoundException("User not found :: " + userId));
		return person;
	}

	@GET
	@Produces("application/json")
	@Consumes("application/json")
	@Path("/users/nocoloc")
	public List<Person> getPersonWithNoColoc() throws ResourceNotFoundException {
		return personRepository.findByColocIsNull();
	}

	@POST
	@Produces("application/json")
	@Consumes("application/json")
	@Path("/users")
	@PostMapping("/users")
	public Person createUser(Person person) {
		return personRepository.save(person);
	}

	@PUT
	@Produces("application/json")
	@Consumes("application/json")
	@Path("/users/{id}")
	public ResponseEntity<Person> updateUser(@PathParam(value = "id") Long userId,
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

		final Person personEdited = personRepository.save(person);
		return ResponseEntity.ok(personEdited);
	}

	@DELETE
	@Produces("application/json")
	@Path("/users/{id}")
	public Map<String, Boolean> deleteUser(@PathParam(value = "id") Long userId) throws ResourceNotFoundException {
		Person person = personRepository.findById(userId)
				.orElseThrow(() -> new ResourceNotFoundException("User not found :: " + userId));

		personRepository.delete(person);
		Map<String, Boolean> response = new HashMap<>();
		response.put("deleted", Boolean.TRUE);
		return response;
	}
}