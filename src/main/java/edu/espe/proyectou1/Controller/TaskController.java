package edu.espe.proyectou1.Controller;

import edu.espe.proyectou1.Model.Task;
import edu.espe.proyectou1.Service.ProjectService;
import edu.espe.proyectou1.Service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/task")
public class TaskController {
    @Autowired
    private TaskService taskService;

    @Autowired
    private ProjectService projectService;

    @PostMapping(value = "/save")
    public ResponseEntity<?> save(@RequestBody Task task) {
        return taskService.save(task);
    }

    @GetMapping(value = "/listTask")
    public List<Task> listTask() {
        return taskService.findAll();
    }

    @GetMapping(value = "/listTasksByProject/{id}")
    public List<Task> listTasksByProject(@PathVariable String id) {
        return taskService.findAllByProject(id);
    }

    @GetMapping(value = "/findById/{id}")
    public ResponseEntity<?> findById(@PathVariable String id) {
        return taskService.findById(id);
    }

    @DeleteMapping(value = "/deleteById/{id}")
    public ResponseEntity<?> deleteById(@PathVariable String id) {
        return taskService.deleteById(id);
    }

    @PutMapping(value = "/updateById/{id}")
    public ResponseEntity<?> updateById(@PathVariable String id, @RequestBody Task task) {
        if (projectService.updateProjectProgress(task.getProjectId())){
            return taskService.updateById(id, task);
        } else {
            return new ResponseEntity<>("Project no found", HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping(value = "/countTasksByUserId/{userId}")
    public ResponseEntity<?> countTasksByUserId(@PathVariable String userId) {
        return taskService.countTasksByUserId(userId);
    }
}
