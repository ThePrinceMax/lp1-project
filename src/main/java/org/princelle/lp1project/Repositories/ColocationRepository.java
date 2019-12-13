package org.princelle.lp1project.Repositories;

import org.princelle.lp1project.Entities.Colocation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ColocationRepository extends JpaRepository<Colocation, Long>{

}