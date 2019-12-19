package org.princelle.lp1project.Repositories;

import org.princelle.lp1project.Entities.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PersonRepository extends JpaRepository<Person, Long>{

}