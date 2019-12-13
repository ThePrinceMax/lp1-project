package org.princelle.lp1project;

import javax.ws.rs.ApplicationPath;

import org.glassfish.jersey.server.ResourceConfig;
import org.princelle.lp1project.Routes.ColocationResource;
import org.princelle.lp1project.Routes.UserResource;
import org.springframework.stereotype.Component;

@Component
@ApplicationPath("/")
public class JerseyConfiguration extends ResourceConfig {
	public JerseyConfiguration() {
		register(UserResource.class);
		register(ColocationResource.class);
	}
}