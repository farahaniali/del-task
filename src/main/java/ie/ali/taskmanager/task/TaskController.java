package ie.ali.taskmanager.task;

import ie.ali.taskmanager.auth.ApplicationUser;
import ie.ali.taskmanager.user.User;
import ie.ali.taskmanager.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/task")
public class TaskController {

    private final TaskService taskService;


    private final UserService userService;

    @Autowired
    public TaskController(TaskService taskService, UserService userService) {
        this.taskService = taskService;
        this.userService = userService;
    }


    @PostMapping
    public TaskRequestResponse createTask(@RequestBody TaskRequestResponse taskRequest) {
        ApplicationUser applicationUser = getUser();
        User user = userService.selectUserByUsername(applicationUser.getUsername());

        taskRequest.setOwnerUsername(user.getUsername());
        TaskRequestResponse registeredTaskResponse = taskService.registerTask(taskRequest);

        return registeredTaskResponse;

    }

    @PutMapping("/{id}")
    public TaskRequestResponse updateTask(@RequestBody TaskRequestResponse taskRequest, @PathVariable("id") Long id) {
        ApplicationUser applicationUser = getUser();
        User user = userService.selectUserByUsername(applicationUser.getUsername());
        TaskRequestResponse task = taskService.getUserTask(user, id);

        //update the fields that are changed
        if (taskRequest.getChecked() != null) {
            task.setChecked(taskRequest.getChecked());
        }
        if (taskRequest.getDescription() != null) {
            task.setDescription(taskRequest.getDescription());
        }

        return taskService.updateTask(task);
    }

    public String getTasks(Model model) {
        model.addAttribute("tasks", taskService.getUserTasks(getUser().getUsername()));
        return "task/all";
    }


    @DeleteMapping("/{id}")
    public void deleteTask(@PathVariable("id") Long id) {
        ApplicationUser applicationUser = getUser();
        User user = userService.selectUserByUsername(applicationUser.getUsername());
        taskService.getUserTask(user, id);

        taskService.delete(id);
    }

    @GetMapping("{id}")
    public TaskRequestResponse getTask(@PathVariable("id") Long id) {
        ApplicationUser applicationUser = getUser();
        User user = userService.selectUserByUsername(applicationUser.getUsername());
        TaskRequestResponse task = taskService.getUserTask(user, id);
        return task;

    }

    @GetMapping("")
    public List<TaskRequestResponse> getTasksByUser() {
        return taskService.getUserTasks(getUser().getUsername());
    }



    private ApplicationUser getUser() {

        ApplicationUser applicationUser = (ApplicationUser) SecurityContextHolder
                .getContext().getAuthentication().getPrincipal();
        return applicationUser;
    }

}
