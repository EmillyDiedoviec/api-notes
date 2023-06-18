package br.com.apiNotes.apinotes.models;

import br.com.apiNotes.apinotes.dtos.AddTask;
import br.com.apiNotes.apinotes.dtos.UpdateTask;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class Task {

    private UUID id;
    private String title;
    private String description;
    private Boolean archive;

    public Task(AddTask newTask) {
        id = UUID.randomUUID();
        title = newTask.title();
        description = newTask.description();
        archive = false;
    }

    public void UpdateTask(UpdateTask taskUpdated) {
            if(taskUpdated.title() != null) {
                title = taskUpdated.title();
            }
            if(taskUpdated.description() != null) {
                description = taskUpdated.description();
            }
    }
}

