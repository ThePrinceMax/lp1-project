package org.princelle.lp1project.Routes;

import org.princelle.lp1project.Entities.Service;
import org.princelle.lp1project.Repositories.ServiceRepository;
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
public class ServiceResource {

	@Autowired
	private ServiceRepository serviceRepository;

	@GET
	@Produces("application/json")
	@Path("/services")
	public List<Service> getAllServices() {
		return serviceRepository.findAll();
	}

	@GET
	@Produces("application/json")
	@Path("/services/{id}")
	public ResponseEntity<Service> getServiceById(@PathParam(value = "id") Long serviceId) throws ResourceNotFoundException {
		Service service = serviceRepository.findById(serviceId)
				.orElseThrow(() -> new ResourceNotFoundException("Service not found :: " + serviceId));
		return ResponseEntity.ok().body(service);
	}

	@POST
	@Produces("application/json")
	@Consumes("application/json")
	@Path("/services")
	@PostMapping("/services")
	public Service createService(Service service) {
		return serviceRepository.save(service);
	}

	@PUT
	@Produces("application/json")
	@Consumes("application/json")
	@Path("/services/{id}")
	public ResponseEntity<Service> updateService(@PathParam(value = "id") Long serviceId,
										   @Valid @RequestBody Service ServiceDetails) throws ResourceNotFoundException {
		Service service = serviceRepository.findById(serviceId)
				.orElseThrow(() -> new ResourceNotFoundException("Service not found :: " + serviceId));

		if (service.isAchieved()) { // If Service is achieved, do not edit !
			return ResponseEntity.ok(service);
		} else {
			if (ServiceDetails.getTitle() != null){
				service.setTitle(ServiceDetails.getTitle());
			}

			if (ServiceDetails.getDescription() != null){
				service.setDescription(ServiceDetails.getDescription());
			}

			if (ServiceDetails.getCost() != null){
				service.setCost(ServiceDetails.getCost());
			}

			if (ServiceDetails.getProposed()) {
				service.setProposed(ServiceDetails.getProposed());
			}
		}

		final Service ServiceEdited = serviceRepository.save(service);
		return ResponseEntity.ok(ServiceEdited);
	}

	@DELETE
	@Produces("application/json")
	@Path("/services/{id}")
	public Map<String, Boolean> deleteService(@PathParam(value = "id") Long serviceId) throws ResourceNotFoundException {
		Service service = serviceRepository.findById(serviceId)
				.orElseThrow(() -> new ResourceNotFoundException("Service not found :: " + serviceId));

		serviceRepository.delete(service);
		Map<String, Boolean> response = new HashMap<>();
		response.put("deleted", Boolean.TRUE);
		return response;
	}
}