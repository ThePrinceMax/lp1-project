package org.princelle.lp1project;

import javax.ws.rs.ApplicationPath;

import org.glassfish.jersey.server.ResourceConfig;
import org.princelle.lp1project.Routes.*;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.ClassPathScanningCandidateComponentProvider;
import org.springframework.core.type.filter.RegexPatternTypeFilter;
import org.springframework.stereotype.Component;

import java.util.Set;
import java.util.regex.Pattern;

@Component
@ApplicationPath("/")
public class JerseyConfiguration extends ResourceConfig {
	public JerseyConfiguration() throws ClassNotFoundException {
		// Creating Scanner and disabling default filters
		final ClassPathScanningCandidateComponentProvider provider = new ClassPathScanningCandidateComponentProvider(false);

		// Add filter that matches all the classes that we want to import.
		provider.addIncludeFilter(new RegexPatternTypeFilter(Pattern.compile(".*")));

		// Get all the classes defined in specific package
		final Set<BeanDefinition> classes = provider.findCandidateComponents("org.princelle.lp1project.Routes");

		// Loading each classes from BeanDefinition instance
		for (BeanDefinition bean: classes) {
			Class<?> the_class = Class.forName(bean.getBeanClassName());
			register(the_class); // Register the Resource class for JerseyConfiguration
		}
	}
}