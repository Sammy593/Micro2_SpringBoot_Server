package edu.espe.proyectou1.Repository;

import edu.espe.proyectou1.Model.Task;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class TaskRepository {
    List<Task> tasks = new ArrayList<>();

    public List<Task> findAll() {
        return tasks;
    }
    public List<Task> findAllByProject(String id) {
        List<Task> projectTask = new ArrayList<>();
        for(Task task : tasks){
            if(task.getProjectId().equals(id)){
                projectTask.add(task);
            }
        }
        return projectTask;
    }
    public Task save(Task task){
        tasks.add(task);
        return task;
    }
    public void deleteById(String id) {
        for (Task task : tasks) {
            if (task.getId().equals(id)) {
                tasks.remove(task);
                break;
            }
        }
    }
    public Task findById(String id) {
        for (Task task : tasks) {
            if (task.getId().equals(id)) {
                return task;
            }
        }
        return null;
    }

    public void updateById(String id, Task task) {
        for (Task task1 : tasks) {
            if (task1.getId().equals(id)) {
                task1.setName(task.getName());
                task1.setDescription(task.getDescription());
                task1.setStartDate(task.getStartDate());
                task1.setEndDate(task.getEndDate());
                task1.setProjectId(task.getProjectId());
                task1.setResponsible(task.getResponsible());
                task1.setState(task.getState());
                break;
            }
        }
    }

    public List<Task> findByUserId(String id){
        List<Task> tasksByUser = new ArrayList<>();
        for(Task task : tasks){
            if(task.getResponsible().equals(id)){
                tasksByUser.add(task);
            }
        }
        return tasksByUser;
    }
}
