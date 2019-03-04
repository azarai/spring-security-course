package de.codeboje.courses.springsecurity.usermgm;

import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User,Long>{

	User findByUsername(String username);
}
