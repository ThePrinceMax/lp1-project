package org.princelle.lp1project.Repositories;

import org.princelle.lp1project.Entities.Service;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ServiceRepository extends JpaRepository<Service, Long>{

	@Query("SELECT s FROM Service s WHERE s.achieved = :a_id")
	List<Service> findByAchieved_Id(@Param("a_id") Long a_id);

}