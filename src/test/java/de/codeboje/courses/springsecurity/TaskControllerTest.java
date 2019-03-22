package de.codeboje.courses.springsecurity;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertEquals;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;

import de.codeboje.courses.springsecurity.auth.SecurityUser;
import de.codeboje.courses.springsecurity.model.Task;
import de.codeboje.courses.springsecurity.usermgm.User;
import de.codeboje.courses.springsecurity.usermgm.UserRepository;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc

public class TaskControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private TaskRepository taskRepo;

	@Autowired
	private ObjectMapper objectMapper;
	
	@Autowired
	private UserRepository userRepo;
	
	@Before
	public void before() {
		taskRepo.deleteAll();
	}
	
	@Test
	@WithUserDetails("tester")
	public void testCreate() throws Exception {

		Task task = new Task();
		task.setText("TaskText");

		// @formatter:off
		mockMvc.perform(
				post("/tasks")
					.contentType(MediaType.APPLICATION_JSON)
					.content(objectMapper.writeValueAsBytes(task))
					.with(csrf())
					
		).andExpect(
				status().is(201)
		).andExpect(
				jsonPath(
						"text", 
						is(task.getText())
				)
		);
		// @formatter:on

		assertEquals(1, taskRepo.count());
	}
	
	@Test
	public void testGet() throws Exception {

		Task task = new Task();
		task.setUser(userRepo.findByUsername("tester"));
		task.setText("TaskText");
		task = taskRepo.save(task);

		// @formatter:off
		mockMvc.perform(
				get("/task/{0}", task.getId())
				.with(user("tester").authorities(new SimpleGrantedAuthority("user")))
		).andExpect(
				status().is(200)
		).andExpect(
				jsonPath(
						"text", 
						is(task.getText())
				)
		);
		// @formatter:on
	}
	
	@Test
	public void testGetWithMockDetails() throws Exception {

		//user for request
		SecurityUser testUser = new SecurityUser("tester2", "");
		
		//same user in DB
		User testUserDB = new User();
		testUserDB.setUsername("tester2");
		testUserDB.setPassword("doesntmatter");
		testUserDB = userRepo.save(testUserDB);
		
		Task task = new Task();
		task.setUser(testUserDB);
		task.setText("TaskText");
		task = taskRepo.save(task);

		// @formatter:off
		mockMvc.perform(
				get("/task/{0}", task.getId())
				.with(user(testUser))
		).andExpect(
				status().is(200)
		).andExpect(
				jsonPath(
						"text", 
						is(task.getText())
				)
		);
		// @formatter:on
	}
}
