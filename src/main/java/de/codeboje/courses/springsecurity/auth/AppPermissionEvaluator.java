package de.codeboje.courses.springsecurity.auth;

import java.io.Serializable;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.PermissionEvaluator;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import de.codeboje.courses.springsecurity.TaskRepository;
import de.codeboje.courses.springsecurity.model.Task;

@Component
public class AppPermissionEvaluator implements PermissionEvaluator{
	
	@Autowired
	private TaskRepository taskRepo;

	@Override
	public boolean hasPermission(Authentication authentication, Object targetDomainObject, Object permission) {
		if("write".equals(permission) && targetDomainObject instanceof Long) {
			 Optional<Task> task = taskRepo.findById((Long) targetDomainObject);
			 if(task.isPresent() && task.get().getUser().getUsername().equals(authentication.getName())) {
				 return true;
			 }
		}
		return false;
	}

	@Override
	public boolean hasPermission(Authentication authentication, Serializable targetId, String targetType,
			Object permission) {
		if("taskId".equals(targetType) && "write".equals(permission) && targetId instanceof Long) {
			 Optional<Task> task = taskRepo.findById((Long) targetId);
			 if(task.isPresent() && task.get().getUser().getUsername().equals(authentication.getName())) {
				 return true;
			 }
		}
		return false;
	}

}
