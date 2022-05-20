package ie.ali.taskmanager;


import ie.ali.taskmanager.user.UserRepository;
import ie.ali.taskmanager.user.UserService;
import ie.ali.taskmanager.task.TaskController;
import ie.ali.taskmanager.task.TaskRepository;
import ie.ali.taskmanager.task.TaskService;
import org.assertj.core.api.Assertions;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.json.JacksonJsonParser;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;

import org.springframework.security.oauth2.common.exceptions.InvalidGrantException;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestBuilders.formLogin;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.authenticated;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment= SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class TaskManagerApplicationTests {

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private TestRestTemplate template;

	@Autowired
	private TaskService taskService;

	@Autowired
	private UserService userService;

	@Autowired
	private TaskRepository taskRepository;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private TaskController taskController;


	@Test
	public void contextLoads() {
		Assertions.assertThat(taskService).isNotNull();
		Assertions.assertThat(taskController).isNotNull();
		Assertions.assertThat(taskRepository).isNotNull();
		Assertions.assertThat(userRepository).isNotNull();
		Assertions.assertThat(userService).isNotNull();

	}

	@Test
	public void testAuthenticationSuccess() throws Exception {
		Assert.assertNotNull(obtainAccessToken("user1","123"));

	}

	@Test
	public void testAuthenticationFailure() throws Exception{

		MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
		params.add("grant_type", "password");
		params.add("client_id", "taskmanager-client");
		params.add("username", "user1");
		params.add("password", "1");


		MvcResult mvcResult = mockMvc.perform(post("/oauth/token")
						.params(params)
						.with(httpBasic("taskmanager-client", "taskmanager-secret"))
						.accept("application/json;charset=UTF-8"))
				.andReturn();
		Assert.assertEquals(400, mvcResult.getResponse().getStatus());
	}

	private String obtainAccessToken(String username, String password) throws Exception {

		MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
		params.add("grant_type", "password");
		params.add("client_id", "taskmanager-client");
		params.add("username", username);
		params.add("password", password);

		ResultActions result
				= mockMvc.perform(post("/oauth/token")
						.params(params)
						.with(httpBasic("taskmanager-client","taskmanager-secret"))
						.accept("application/json;charset=UTF-8"))
				.andExpect(status().isOk())
				.andExpect(content().contentType("application/json;charset=UTF-8"));

		String resultString = result.andReturn().getResponse().getContentAsString();

		JacksonJsonParser jsonParser = new JacksonJsonParser();
		return jsonParser.parseMap(resultString).get("access_token").toString();
	}


}
