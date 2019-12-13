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

import org.princelle.lp1project.Entities.User;
import org.princelle.lp1project.Repositories.UserRepository;
import org.princelle.lp1project.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Component
@Path("/api")
public class UserResource {

	@Autowired
	private UserRepository userRepository;

	@GET
	@Produces("application/json")
	@Path("/users")
	public List<User> getAllUsers() {
		return userRepository.findAll();
	}

	@GET
	@Produces("application/json")
	@Path("/users/{id}")
	public ResponseEntity<User> getUserById(@PathParam(value = "id") Long userId) throws ResourceNotFoundException {
		User user = userRepository.findById(userId)
				.orElseThrow(() -> new ResourceNotFoundException("User not found :: " + userId));
		return ResponseEntity.ok().body(user);
	}

	@POST
	@Produces("application/json")
	@Consumes("application/json")
	@Path("/users")
	@PostMapping("/users")
	public User createUser(User user) {
		return userRepository.save(user);
	}

	@PUT
	@Produces("application/json")
	@Consumes("application/json")
	@Path("/users/{id}")
	public ResponseEntity<User> updateUser(@PathParam(value = "id") Long userId,
										   @Valid @RequestBody User userDetails) throws ResourceNotFoundException {
		User user = userRepository.findById(userId)
				.orElseThrow(() -> new ResourceNotFoundException("User not found :: " + userId));

		if (userDetails.getEmailId() != null){
			user.setEmailId(userDetails.getEmailId());
		}

		if (userDetails.getFirstName() != null){
			user.setFirstName(userDetails.getFirstName());
		}

		if (userDetails.getLastName() != null){
			user.setLastName(userDetails.getLastName());
		}

		final User userEdited = userRepository.save(user);
		return ResponseEntity.ok(userEdited);
	}

	@DELETE
	@Produces("application/json")
	@Path("/users/{id}")
	public Map<String, Boolean> deleteUser(@PathParam(value = "id") Long userId) throws ResourceNotFoundException {
		User user = userRepository.findById(userId)
				.orElseThrow(() -> new ResourceNotFoundException("User not found :: " + userId));

		userRepository.delete(user);
		Map<String, Boolean> response = new HashMap<>();
		response.put("deleted", Boolean.TRUE);
		return response;
	}
}