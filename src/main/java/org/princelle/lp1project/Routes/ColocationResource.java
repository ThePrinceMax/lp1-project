package org.princelle.lp1project.Routes;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;
import javax.ws.rs.*;

import org.princelle.lp1project.Entities.Colocation;
import org.princelle.lp1project.Entities.Person;
import org.princelle.lp1project.Repositories.ColocationRepository;
import org.princelle.lp1project.Repositories.PersonRepository;
import org.princelle.lp1project.Exceptions.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Component
@Path("/api")
public class ColocationResource {

	@Autowired
	private ColocationRepository colocRepository;

	@Autowired
	private PersonRepository personRepository;

	@GET
	@Produces("application/json")
	@Path("/colocs")
	public List<Colocation> getAllColcations() {
		return colocRepository.findAll();
	}

	@GET
	@Produces("application/json")
	@Path("/colocs/{id}")
	public ResponseEntity<Colocation> getColocationById(@PathParam(value = "id") Long colocId) throws ResourceNotFoundException {
		Colocation coloc = colocRepository.findById(colocId)
				.orElseThrow(() -> new ResourceNotFoundException("Colocation not found :: " + colocId));
		return ResponseEntity.ok().body(coloc);
	}

	@POST
	@Produces("application/json")
	@Consumes("application/json")
	@Path("/colocs/{id}/members/{userID}")
	public List<Person> addUsertoColocation(@PathParam(value = "id") Long colocId, @PathParam(value = "userID") Long userID) throws ResourceNotFoundException {
		Colocation coloc = colocRepository.findById(colocId)
				.orElseThrow(() -> new ResourceNotFoundException("Colocation not found :: " + colocId));
		Person user = personRepository.findById(userID)
				.orElseThrow(() -> new ResourceNotFoundException("User not found :: " + userID));

		user.setColoc(coloc);
		personRepository.save(user);

		List<Person> users = personRepository.findByColocId(coloc.getId());
		return users;
	}

	@GET
	@Produces("application/json")
	@Path("/colocs/{id}/members")
	public List<Person> getMembersByColocationID(@PathParam(value = "id") Long colocId) throws ResourceNotFoundException {
		Colocation coloc = colocRepository.findById(colocId)
				.orElseThrow(() -> new ResourceNotFoundException("Colocation not found :: " + colocId));
		List<Person> users = personRepository.findByColocId(coloc.getId());
		return users;
	}

	@POST
	@Produces("application/json")
	@Consumes("application/json")
	@Path("/colocs")
	@PostMapping("/colocs")
	public Colocation createColocation(Colocation coloc) {
		return colocRepository.save(coloc);
	}

	@PUT
	@Produces("application/json")
	@Consumes("application/json")
	@Path("/colocs/{id}")
	public ResponseEntity<Colocation> updateColocation(@PathParam(value = "id") Long colocId,
										   @Valid @RequestBody Colocation colocDetails) throws ResourceNotFoundException {
		Colocation coloc = colocRepository.findById(colocId)
				.orElseThrow(() -> new ResourceNotFoundException("Colocation not found :: " + colocId));

		if (colocDetails.getName() != null){
			coloc.setName(colocDetails.getName());
		}

		final Colocation colocEdited = colocRepository.save(coloc);
		return ResponseEntity.ok(colocEdited);
	}

	@DELETE
	@Produces("application/json")
	@Path("/colocs/{id}")
	public Map<String, Boolean> deleteColocation(@PathParam(value = "id") Long colocId) throws ResourceNotFoundException {
		Colocation user = colocRepository.findById(colocId)
				.orElseThrow(() -> new ResourceNotFoundException("Colocation not found :: " + colocId));

		colocRepository.delete(user);
		Map<String, Boolean> response = new HashMap<>();
		response.put("deleted", Boolean.TRUE);
		return response;
	}

	@DELETE
	@Produces("application/json")
	@Consumes("application/json")
	@Path("/colocs/{id}/members/{userID}")
	public List<Person> deleteUserFromColocation(@PathParam(value = "id") Long colocId, @PathParam(value = "userID")  Long userId) throws ResourceNotFoundException {
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