package org.princelle.lp1project.Security;

import org.princelle.lp1project.Entities.Person;
import org.princelle.lp1project.Repositories.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import static java.util.Collections.emptyList;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

	@Autowired
	private PersonRepository applicationUserRepository;

	public UserDetailsServiceImpl(PersonRepository applicationUserRepository) {
		this.applicationUserRepository = applicationUserRepository;
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Person applicationUser = applicationUserRepository.findPersonByPseudo(username);
		if (applicationUser == null) {
			throw new UsernameNotFoundException(username);
		}
		return new User(applicationUser.getPseudo(), applicationUser.getPassword(), emptyList());
	}
}