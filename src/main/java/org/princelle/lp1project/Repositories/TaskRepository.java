package org.princelle.lp1project.Repositories;

import org.princelle.lp1project.Entities.Colocation;
import org.princelle.lp1project.Entities.Person;
import org.princelle.lp1project.Entities.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long>{
	List<Task> findAllByColoc(Colocation colocation);
	List<Task> findTasksByFinishDateIsNull();
	List<Task> findTasksByColocAndFinishDateIsNull(Colocation colocation);
	List<Task> findTasksByProposedIsTrueAndColoc(Colocation colocation);
	List<Task> findTasksByProposedIsTrue();
	List<Task> findAllByToPerson(Person person);
	List<Task> findTasksByProposedIsTrueAndToPerson(Person person);
	List<Task> findTasksByToPersonAndFinishDateIsNull(Person person);
}