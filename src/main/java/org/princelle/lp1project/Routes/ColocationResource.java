package org.princelle.lp1project.Routes;

import org.princelle.lp1project.Entities.Colocation;
import org.princelle.lp1project.Entities.Person;
import org.princelle.lp1project.Exceptions.ResourceNotFoundException;
import org.princelle.lp1project.Repositories.ColocationRepository;
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
public class ColocationResource {

	@Autowired
	private ColocationRepository colocRepository;

	@Autowired
	private PersonRepository personRepository;

	@GetMapping(value = "/api/colocs", produces = "application/json")
	public List<Colocation> getAllColcations() {
		return colocRepository.findAll();
	}

	@GetMapping(value = "/api/colocs/{id}", produces = "application/json")
	public Colocation getColocationById(@PathVariable(value = "id") Long colocId) throws ResourceNotFoundException {
		Colocation coloc = colocRepository.findById(colocId)
				.orElseThrow(() -> new ResourceNotFoundException("Colocation not found :: " + colocId));
		return coloc;
	}

	@PostMapping(value = "/api/colocs", produces = "application/json", consumes = "application/json")
	public Colocation createColocation(@Valid @RequestBody Colocation coloc) {
		return colocRepository.save(coloc);
	}

	@PostMapping(value = "/api/colocs/{id}/members/{userID}", produces = "application/json", consumes = "application/json")
	public List<Person> addUsertoColocation(@PathVariable(value = "id") Long colocId, @PathVariable(value = "userID") Long userID) throws ResourceNotFoundException {
		Colocation coloc = colocRepository.findById(colocId)
				.orElseThrow(() -> new ResourceNotFoundException("Colocation not found :: " + colocId));
		Person user = personRepository.findById(userID)
				.orElseThrow(() -> new ResourceNotFoundException("User not found :: " + userID));

		user.setColoc(coloc);
		personRepository.save(user);

		List<Person> users = personRepository.findByColocId(coloc.getId());
		return users;
	}

	@GetMapping(value = "/api/colocs/{id}/members", produces = "application/json")
	public List<Person> getMembersByColocationID(@PathVariable(value = "id") Long colocId) throws ResourceNotFoundException {
		Colocation coloc = colocRepository.findById(colocId)
				.orElseThrow(() -> new ResourceNotFoundException("Colocation not found :: " + colocId));
		List<Person> users = personRepository.findByColocId(coloc.getId());
		return users;
	}

	@PutMapping(value = "/api/colocs/{id}", produces = "application/json", consumes = "application/json")
	public ResponseEntity<Colocation> updateColocation(@PathVariable(value = "id") Long colocId,
										   @Valid @RequestBody Colocation colocDetails) throws ResourceNotFoundException {
		Colocation coloc = colocRepository.findById(colocId)
				.orElseThrow(() -> new ResourceNotFoundException("Colocation not found :: " + colocId));

		if (colocDetails.getName() != null){
			coloc.setName(colocDetails.getName());
		}

		final Colocation colocEdited = colocRepository.save(coloc);
		return ResponseEntity.ok(colocEdited);
	}

	@DeleteMapping(value = "/api/colocs/{id}", produces = "application/json")
	public Map<String, Boolean> deleteColocation(@PathVariable(value = "id") Long colocId) throws ResourceNotFoundException {
		Colocation user = colocRepository.findById(colocId)
				.orElseThrow(() -> new ResourceNotFoundException("Colocation not found :: " + colocId));

		colocRepository.delete(user);
		Map<String, Boolean> response = new HashMap<>();
		response.put("deleted", Boolean.TRUE);
		return response;
	}

	@DeleteMapping(value = "/api/colocs/{id}/members/{userID}", produces = "application/json")
	public List<Person> deleteUserFromColocation(@PathVariable(value = "id") Long colocId, @PathVariable(value = "userID")  Long userId) throws ResourceNotFoundException {
		Colocation coloc = colocRepository.findById(colocId)
				.orElseThrow(() -> new ResourceNotFoundException("Colocation not found :: " + colocId));

		Person user = personRepository.findById(userId)
				.orElseThrow(() -> new ResourceNotFoundException("User not found :: " + userId));

		if (user.getColoc().getId() != coloc.getId()){
			throw new ResourceNotFoundException("User :: " + userId + " not found in Colocation :: " + colocId);
		}

		user.setColoc(null);
		personRepository.save(user);

		List<Person> users = personRepository.findByColocId(coloc.getId());
		return users;
	}
}