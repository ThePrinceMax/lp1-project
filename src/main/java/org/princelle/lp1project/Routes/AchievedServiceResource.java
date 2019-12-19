package org.princelle.lp1project.Routes;

import org.princelle.lp1project.Entities.AchievedService;
import org.princelle.lp1project.Repositories.AchievedServiceRepository;
import org.princelle.lp1project.Repositories.AchievedServiceRepository;
import org.princelle.lp1project.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.Valid;
import javax.ws.rs.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
@Path("/api")
public class AchievedServiceResource {

	@Autowired
	private AchievedServiceRepository AchievedServiceRepository;

	@GET
	@Produces("application/json")
	@Path("/services/achieved")
	public List<AchievedService> getAllAchievedService() {
		return AchievedServiceRepository.findAll();
	}

	@GET
	@Produces("application/json")
	@Path("/services/achieved/{id}")
	public ResponseEntity<AchievedService> getAchievedServiceById(@PathParam(value = "id") Long a_service_id) throws ResourceNotFoundException {
		AchievedService achievedService = AchievedServiceRepository.findById(a_service_id)
				.orElseThrow(() -> new ResourceNotFoundException("AchievedService not found :: " + a_service_id));
		return ResponseEntity.ok().body(achievedService);
	}

	@POST
	@Produces("application/json")
	@Consumes("application/json")
	@Path("/services/achieved")
	@PostMapping("/services/achieved")
	public AchievedService createAchievedService(AchievedService achievedService) {
		return AchievedServiceRepository.save(achievedService);
	}

	@PUT
	@Produces("application/json")
	@Consumes("application/json")
	@Path("/services/achieved/{id}")
	public ResponseEntity<AchievedService> updateAchievedService(@PathParam(value = "id") Long a_service_id,
										   @Valid @RequestBody AchievedService achievedServiceDetails) throws ResourceNotFoundException {
		AchievedService achievedService = AchievedServiceRepository.findById(a_service_id)
				.orElseThrow(() -> new ResourceNotFoundException("AchievedService not found :: " + a_service_id));

		if (achievedServiceDetails.getDate() != null){
			achievedService.setDate(achievedServiceDetails.getDate());
		}

		if (achievedServiceDetails.getValidFrom()) {
			achievedService.setValidFrom(achievedServiceDetails.getValidFrom());
		}

		if (achievedServiceDetails.isPicture()) {
			achievedService.setPicture(achievedServiceDetails.getPicture());
		}

		if (achievedServiceDetails.getValidTo()) {
			achievedService.setValidTo(achievedServiceDetails.getValidTo());
		}

		final AchievedService achievedServiceEdited = AchievedServiceRepository.save(achievedService);
		return ResponseEntity.ok(achievedServiceEdited);
	}

	@DELETE
	@Produces("application/json")
	@Path("/services/achieved/{id}")
	public Map<String, Boolean> deleteAchievedService(@PathParam(value = "id") Long a_service_id) throws ResourceNotFoundException {
		AchievedService achievedService = AchievedServiceRepository.findById(a_service_id)
				.orElseThrow(() -> new ResourceNotFoundException("AchievedService not found :: " + a_service_id));

		AchievedServiceRepository.delete(achievedService);
		Map<String, Boolean> response = new HashMap<>();
		response.put("deleted", Boolean.TRUE);
		return response;
	}
}