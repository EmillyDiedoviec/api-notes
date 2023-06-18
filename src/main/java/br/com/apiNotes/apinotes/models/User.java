package br.com.apiNotes.apinotes.models;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class User {

    private String email;
    private String password;
    private ArrayList<Task> tasks;

    public User(String email, String password) {
        this.email = email;
        this.password = password;
        tasks = new ArrayList<Task>();
    }
}

