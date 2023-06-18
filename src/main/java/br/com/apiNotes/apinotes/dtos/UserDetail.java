package br.com.apiNotes.apinotes.dtos;

import br.com.apiNotes.apinotes.models.Task;
import br.com.apiNotes.apinotes.models.User;

import java.util.List;

public record UserDetail(
        String email,
        String password,
        List<Task> task
) {
    public UserDetail(User U) {
        this(
                U.getEmail(),
                U.getPassword(),
                U.getTasks()
        );

    }

}
