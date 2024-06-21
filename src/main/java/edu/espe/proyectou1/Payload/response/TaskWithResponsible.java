package edu.espe.proyectou1.Payload.response;

import edu.espe.proyectou1.Model.Task;
import lombok.Data;

@Data
public class TaskWithResponsible {
    private Task task;
    private Object responsibleObject;
}
