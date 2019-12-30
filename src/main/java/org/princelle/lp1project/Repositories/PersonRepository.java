package org.princelle.lp1project.Repositories;

import org.princelle.lp1project.Entities.Person;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

@Repository
public interface PersonRepository extends JpaRepository<Person, Long>{

	// Find Person by Email ID
	@Query("SELECT u FROM Person u WHERE u.emailId = ?1")
	Person findPersonByEmailId(String email);
}