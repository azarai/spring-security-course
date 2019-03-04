package de.codeboje.courses.springsecurity;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import de.codeboje.courses.springsecurity.model.Task;

public interface TaskRepository extends CrudRepository<Task, Long>{

	@Query("select t from Task t where t.user.username = :username")
	List<Task> findAllTasksByUsername(@Param("username") String username);
}
