package edu.espe.proyectou1.Repository;

import edu.espe.proyectou1.Model.Project;
import edu.espe.proyectou1.Model.Task;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class ProjectRepository {

    List<Project> projects = new ArrayList<>();

    public List<Project> findAll() {
        return projects;
    }

    public Project save(Project project) {
        projects.add(project);
        return project;
    }
    public void deleteById(String id) {
        for (Project person : projects) {
            if (person.getId().equals(id)) {
                projects.remove(person);
                break;
            }
        }
    }
    public Project findById(String id) {
        for (Project project : projects) {
            if (project.getId().equals(id)) {
                return project;
            }
        }
        return null;
    }
    public void updateById(String id, Project project) {
        for (Project project1 : projects) {
            if (project1.getId().equals(id)) {
                project1.setName(project.getName());
                project1.setDescription(project.getDescription());
                project1.setStartDate(project.getStartDate());
                project1.setEndDate(project.getEndDate());
                project1.setState(project.getState());
                project1.setIdLeader(project.getIdLeader());
            }
        }
    }

    public List<Project> findByUserId(String id){
        List<Project> projectsByUser = new ArrayList<>();
        for(Project project : projects){
            if(project.getIdLeader().equals(id)){
                projectsByUser.add(project);
            }
        }
        return projectsByUser;
    }

}
