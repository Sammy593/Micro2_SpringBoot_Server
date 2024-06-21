package edu.espe.proyectou1.Service;

import edu.espe.proyectou1.Model.Project;
import edu.espe.proyectou1.Model.Task;
import edu.espe.proyectou1.Payload.response.ProjectWithLeader;
import edu.espe.proyectou1.Repository.ProjectRepository;
import edu.espe.proyectou1.Repository.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Optional;

@Service
public class ProjectService {

    @Autowired
    private ProjectRepository projectRepository;
    @Autowired
    private TaskRepository taskRepository;

    RestTemplate restTemplate = new RestTemplate();

    public List<Project> findAll() {
        return projectRepository.findAll();
    }

    public ResponseEntity<?> save(Project project) {
        // Verificar que el usuario existe
        String url = "http://localhost:8081/user/findById/" + project.getIdLeader();
        ResponseEntity<?> response = restTemplate.getForEntity(url, Object.class);

        if (response.getStatusCode().is2xxSuccessful()) {
            return new ResponseEntity<>(projectRepository.save(project), HttpStatus.CREATED);
        }else{
            return new ResponseEntity<>("Leader user not found", HttpStatus.NOT_FOUND);
        }
    }

    public ResponseEntity<?> deleteById(String id) {
        Optional<Project> projectOptional = Optional.ofNullable(projectRepository.findById(id));
        if (projectOptional.isPresent()) {
            projectRepository.deleteById(id);
            return new ResponseEntity<>(projectOptional.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Project not found", HttpStatus.NOT_FOUND);
        }
    }

    public ResponseEntity<?> findById(String id) {
        Optional<Project> projectOptional = Optional.ofNullable(projectRepository.findById(id));
        if (projectOptional.isPresent()) {
            String leaderId = projectOptional.get().getIdLeader();
            String url = "http://localhost:8081/user/findById/" + leaderId;
            ResponseEntity<?> response = restTemplate.getForEntity(url, Object.class);

            if (response.getStatusCode().is2xxSuccessful()) {
                Object userLeader = response.getBody();
                ProjectWithLeader projectResponse = new ProjectWithLeader();
                projectResponse.setProject(projectOptional.get());
                projectResponse.setUserLeader(userLeader);

                return new ResponseEntity<>(projectResponse, HttpStatus.OK);
            }else{
                return new ResponseEntity<>("Leader user not found", HttpStatus.NOT_FOUND);
            }

        } else {
            return new ResponseEntity<>("Project not found", HttpStatus.NOT_FOUND);
        }
    }

    public ResponseEntity<?> updateById(String id, Project project) {
        Optional<Project> projectOptional = Optional.ofNullable(projectRepository.findById(id));
        if (projectOptional.isPresent()) {
            projectRepository.updateById(id, project);
            return new ResponseEntity<>("Project updated", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Project not found", HttpStatus.NOT_FOUND);
        }
    }


    public Boolean updateProjectProgress(String projectId) {
        boolean res = false;
        Optional<Project> projectOptional = Optional.ofNullable(projectRepository.findById(projectId));

        if (projectOptional.isPresent()) {
            res = true;
            List<Task> tasks = taskRepository.findAllByProject(projectId);

            long completedTasks = tasks.stream().filter(task -> "Listo".equals(task.getState())).count();
            double progress = (double) completedTasks / tasks.size() * 100;

            projectOptional.get().setProgress(progress);
            projectRepository.updateById(projectOptional.get().getId(), projectOptional.get());
        }
        return res;
    }

    public ResponseEntity<?> countProjectsByUserId(String userId) {
        List<Project> projects = projectRepository.findByUserId(userId);
        return new ResponseEntity<>(projects.size(), HttpStatus.OK);
    }
}
