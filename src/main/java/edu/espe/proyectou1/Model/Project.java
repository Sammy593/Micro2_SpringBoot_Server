package edu.espe.proyectou1.Model;

import lombok.Data;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Data
public class Project {
    private String id;
    private String name;
    private String description;
    private Date startDate;
    private Date endDate;
    private Double progress;
    private String state;
    private String idLeader;

    public Project(String name, String description, Date startDate,
                   Date endDate, String state, String idLeader,
                   Double progress) {
        this.id = UUID.randomUUID().toString();
        this.name = name;
        this.description = description;
        this.startDate = startDate;
        this.endDate = endDate;
        this.state = state;
        this.idLeader = idLeader;
        this.progress = 0.0;
    }
}
