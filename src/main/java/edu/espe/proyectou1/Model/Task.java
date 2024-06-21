package edu.espe.proyectou1.Model;

import lombok.Data;

import java.util.ArrayList;
import java.util.Date;
import java.util.UUID;

@Data
public class Task {
    private String id;
    private String name;
    private String description;
    private Date startDate;
    private Date endDate;
    private String projectId;
    private String responsible;
    private String state;

    public Task(String id, String name, String description, Date startDate, Date endDate, String projectId, String responsible, String state) {
        this.id = UUID.randomUUID().toString();
        this.name = name;
        this.description = description;
        this.startDate = startDate;
        this.endDate = endDate;
        this.projectId = projectId;
        this.responsible = responsible;
        this.state = state;
    }
}
