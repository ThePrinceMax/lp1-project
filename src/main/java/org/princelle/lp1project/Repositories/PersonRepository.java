package org.princelle.lp1project.Repositories;

import org.princelle.lp1project.Entities.Person;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PersonRepository extends JpaRepository<Person, Long>{

	Person findByEmailId(String email);
	List<Person> findByColocId(Long colocId);

	List<Person> findByColocIsNull();
}