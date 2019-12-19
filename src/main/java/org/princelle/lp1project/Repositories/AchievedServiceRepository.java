package org.princelle.lp1project.Repositories;

import org.princelle.lp1project.Entities.AchievedService;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AchievedServiceRepository extends JpaRepository<AchievedService, Long>{

}