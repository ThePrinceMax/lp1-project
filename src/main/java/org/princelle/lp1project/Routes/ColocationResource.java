package org.princelle.lp1project.Routes;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import org.princelle.lp1project.Entities.Colocation;
import org.princelle.lp1project.Repositories.ColocationRepository;
import org.princelle.lp1project.ResourceNotFoundException;
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
		Colocation user = colocRepository.findById(colocId)
				.orElseThrow(() -> new ResourceNotFoundException("Colocation not found :: " + colocId));
		return ResponseEntity.ok().body(user);
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
		Colocation user = colocRepository.findById(colocId)
				.orElseThrow(() -> new ResourceNotFoundException("Colocation not found :: " + colocId));

		if (colocDetails.getName() != null){
			user.setName(colocDetails.getName());
		}

		final Colocation colocEdited = colocRepository.save(user);
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
}