package ie.ali.taskmanager.task;


import ie.ali.taskmanager.user.UserRepository;
import ie.ali.taskmanager.user.UserService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.client.HttpClientErrorException;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment= SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class TaskControllerTests {
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
    @WithMockUser(roles="USER", username = "user1")
    public void taskInsertionTest(){
//        TaskRequestResponse taskRequest = new TaskRequestResponse();
//        taskRequest.setChecked(Boolean.FALSE);
//        taskRequest.setDescription("test");
//        taskRequest.setOwnerUsername("user1");
//        TaskRequestResponse tempTaskRequest = taskController.createTask(taskRequest);
//
//        taskController.deleteTask(tempTaskRequest.getId());
//
//
//        try {
//            TaskRequestResponse taskResponse = taskController.getTask(tempTaskRequest.getId());
//        }
//        catch (Exception e){
//
//        }
//
//        TaskRequestResponse tempTaskResponse = taskController.createTask(taskRequest);
//
//        TaskRequestResponse tempTaskResponse2 = taskController.getTask(tempTaskResponse.getId());
//
//        Assert.assertEquals(tempTaskRequest.getId(), tempTaskResponse2.getId());
//        Assert.assertEquals(tempTaskRequest.getChecked(), tempTaskResponse2.getChecked());
//        Assert.assertEquals(tempTaskRequest.getDescription(), tempTaskResponse2.getDescription());
//        Assert.assertEquals(tempTaskRequest.getOwnerUsername(), tempTaskResponse2.getOwnerUsername());

    }
}
