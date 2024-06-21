package edu.espe.proyectou1.Controller;

import edu.espe.proyectou1.Model.Project;
import edu.espe.proyectou1.Service.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/project")
public class ProjectController {
    @Autowired
    private ProjectService projectService;

    @PostMapping(value = "/save")
    public ResponseEntity<?> save(@RequestBody Project project) {
        return projectService.save(project);
    }

    @GetMapping(value = "/listProjects")
    public List<Project> listProjects() {
        return projectService.findAll();
    }

    @GetMapping(value = "/findById/{id}")
    public ResponseEntity<?> findById(@PathVariable String id) {
        return projectService.findById(id);
    }

    @DeleteMapping(value = "/deleteById/{id}")
    public ResponseEntity<?> deleteById(@PathVariable String id) {
        return projectService.deleteById(id);
    }

    @PutMapping(value = "/updateById/{id}")
    public ResponseEntity<?> updateById(@PathVariable String id, @RequestBody Project project) {
        return projectService.updateById(id, project);
    }

    @GetMapping(value = "/countProjectsByUserId/{userId}")
    public ResponseEntity<?> countProjectsByUserId(@PathVariable String userId) {
        return projectService.countProjectsByUserId(userId);
    }

}
