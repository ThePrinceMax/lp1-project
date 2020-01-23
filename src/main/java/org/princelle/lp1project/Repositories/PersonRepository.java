package org.princelle.lp1project.Repositories;

import org.princelle.lp1project.Entities.Colocation;
import org.princelle.lp1project.Entities.Person;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PersonRepository extends JpaRepository<Person, Long>{

	List<Person> findByColocId(Long colocId);

	List<Person> findAllByColoc(Colocation coloc);

	List<Person> findByColocIsNull();

	Person findPersonByEmailId(String emailId);

	Person findPersonByPseudo(String pseudo);

	boolean existsPersonByEmailId(String emailId);
	boolean existsPersonByPseudo(String pseudo);
}