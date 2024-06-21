package edu.espe.proyectou1.Payload.response;

import edu.espe.proyectou1.Model.Project;
import lombok.Data;

@Data
public class ProjectWithLeader {
    private Project project;
    private Object userLeader;
}
