package edu.espe.proyectou1.Service;

import edu.espe.proyectou1.Model.Task;
import edu.espe.proyectou1.Payload.response.TaskWithResponsible;
import edu.espe.proyectou1.Repository.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Optional;

@Service
public class TaskService {

    @Autowired
    private TaskRepository taskRepository;

    RestTemplate restTemplate = new RestTemplate();


    public List<Task> findAll() {
        return taskRepository.findAll();
    }

    public List<Task> findAllByProject(String id) {
        return taskRepository.findAllByProject(id);
    }

    public ResponseEntity<?> save(Task task) {
        // Verificar que usuario existe
        String url = "http://localhost:8081/user/findById/" + task.getResponsible();
        ResponseEntity<?> response = restTemplate.getForEntity(url, Object.class);

        if (response.getStatusCode().is2xxSuccessful()) {
            return new ResponseEntity<>(taskRepository.save(task), HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>("Responsible user not found", HttpStatus.NOT_FOUND);
        }
    }

    public ResponseEntity<?> deleteById(String id) {
        Optional<Task> taskOptional = Optional.ofNullable(taskRepository.findById(id));
        if (taskOptional.isPresent()) {
            taskRepository.deleteById(id);
            return new ResponseEntity<>(taskOptional.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Task not found", HttpStatus.NOT_FOUND);
        }
    }

    public ResponseEntity<?> findById(String id) {
        Optional<Task> taskOptional = Optional.ofNullable(taskRepository.findById(id));
        if (taskOptional.isPresent()) {
            String resposibleId = taskOptional.get().getResponsible();
            String url = "http://localhost:8081/user/findById/" + resposibleId;
            ResponseEntity<?> response = restTemplate.getForEntity(url, Object.class);

            if (response.getStatusCode().is2xxSuccessful()) {
                Object userResposible = response.getBody();
                TaskWithResponsible taskWithResponsible = new TaskWithResponsible();
                taskWithResponsible.setTask(taskOptional.get());
                taskWithResponsible.setResponsibleObject(userResposible);

                return new ResponseEntity<>(taskWithResponsible, HttpStatus.OK);
            }else{
                return new ResponseEntity<>("Responsible user not found", HttpStatus.NOT_FOUND);
            }

        } else {
            return new ResponseEntity<>("Task not found", HttpStatus.NOT_FOUND);
        }
    }

    public ResponseEntity<?> updateById(String id, Task task) {
        Optional<Task> taskOptional = Optional.ofNullable(taskRepository.findById(id));
        if (taskOptional.isPresent()) {
            taskRepository.updateById(id, task);
            return new ResponseEntity<>("Task updated", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Task not found", HttpStatus.NOT_FOUND);
        }
    }

    public ResponseEntity<?> countTasksByUserId(String userId) {
        List<Task> tasks = taskRepository.findByUserId(userId);
        return new ResponseEntity<>(tasks.size(), HttpStatus.OK);
    }
}
