package ie.ali.taskmanager.task;

import ie.ali.taskmanager.user.User;
import ie.ali.taskmanager.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class TaskService {

    private final TaskRepository taskRepository;
    private final UserRepository userRepository;

    @Autowired
    public TaskService(TaskRepository taskRepository, UserRepository userRepository) {
        this.taskRepository = taskRepository;
        this.userRepository = userRepository;
    }

    public List<TaskRequestResponse> getUserTasks(String username) {
        User user = userRepository.findByUsername(username).orElseThrow(
                () -> new UsernameNotFoundException(String.format("User %s not found", username))
        );
        List<Task> tasks = taskRepository.findByOwnerId(user.getId());

        List<TaskRequestResponse> taskRequestResponses = tasks.stream().map(this::toTaskRequestResponse)
                .collect(Collectors.toList());

        return taskRequestResponses;

    }

    public TaskRequestResponse registerTask(TaskRequestResponse taskRequest) {
        Task task = fromTaskRequestResponse(taskRequest);
        task.setUpdateDate(new Timestamp(System.currentTimeMillis()));
        Task registeredTask = taskRepository.save(task);

        return toTaskRequestResponse(registeredTask);
    }


    public TaskRequestResponse getUserTask(User user, Long id) {

        Optional<Task> task = taskRepository.findById(id);
        if (!task.isPresent()) {
            throw new HttpClientErrorException(HttpStatus.NOT_FOUND, String.format("Task %s not found", id));
        }
        if (task.get().getOwner() != user) {
            throw new AccessDeniedException(
                    String.format("The user %s does not have access to Task %s", user.getUsername(), task.get().getId()));
        }
        return toTaskRequestResponse(task.get());
    }

    public TaskRequestResponse updateTask(TaskRequestResponse taskRequest) {

        Task task = fromTaskRequestResponse(taskRequest);
        task.setUpdateDate(new Timestamp(System.currentTimeMillis()));
        Task updatedTask = taskRepository.save(task);
        return toTaskRequestResponse(updatedTask);

    }

    public Task fromTaskRequestResponse(TaskRequestResponse taskRequest) {
        Task resultTask = new Task();
        User user = userRepository.findByUsername(taskRequest.getOwnerUsername()).get();
        resultTask.setOwner(user);
        resultTask.setId(taskRequest.getId());
        resultTask.setDescription(taskRequest.getDescription());
        resultTask.setChecked(taskRequest.getChecked());

        return resultTask;
    }

    public TaskRequestResponse toTaskRequestResponse(Task task) {
        TaskRequestResponse taskResponse = new TaskRequestResponse();

        taskResponse.setChecked(task.getChecked());
        taskResponse.setDescription(task.getDescription());
        taskResponse.setId(task.getId());
        taskResponse.setOwnerUsername(task.getOwner().getUsername());

        return taskResponse;
    }

    public void delete(Long id) {

        taskRepository.deleteById(id);
    }
}
