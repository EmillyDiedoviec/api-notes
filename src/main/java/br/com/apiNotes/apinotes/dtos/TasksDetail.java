package br.com.apiNotes.apinotes.dtos;

import br.com.apiNotes.apinotes.models.Task;
import lombok.Getter;

import java.util.Collection;
import java.util.UUID;

public record TasksDetail(
        UUID id,
        String title,
        String description,
        Boolean archived
) {
    public TasksDetail(Task task) {
        this(task.getId(), task.getTitle(), task.getDescription(), task.getArchived());
    }

}
