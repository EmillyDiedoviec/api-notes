package br.com.apiNotes.apinotes.controllers;

import br.com.apiNotes.apinotes.dataBase.DataBase;
import br.com.apiNotes.apinotes.dtos.*;
import br.com.apiNotes.apinotes.models.Task;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/tasks")
public class TaskController {
    @PostMapping("/{email}/tasksUser")
    public ResponseEntity addTasks(@PathVariable String email, @RequestBody @Valid AddTask newTask){
        var user = DataBase.getUserByEmail(email);

        if(user == null) {
            return ResponseEntity.badRequest().body(new ErrorData("Usuário não localizado."));
        }
        user.getTasks().add(new Task(newTask));
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{email}/tasksUser")
    public ResponseEntity getTasks(@PathVariable String email){
        var tasks = DataBase.getAllTasks(email);

        if(tasks == null) {
            return ResponseEntity.badRequest().body(new ErrorData("Task não localizada."));
        }

        return ResponseEntity.ok().body(tasks);
    }

    @DeleteMapping ("/{email}/tasksUser/{idTask}")
    public ResponseEntity deleteTasks(@PathVariable String email, @PathVariable UUID idTask){
        var user = DataBase.getUserByEmail(email);
        if(user == null) {
            return ResponseEntity.badRequest().body(new ErrorData("Usuário não localizado."));
        }
        var taskId = DataBase.getTaskByID(idTask, email);

        user.getTasks().remove(taskId);
        return ResponseEntity.noContent().build();
    }

    @PutMapping ("/{email}/tasksUser/{idTask}")
    public ResponseEntity updateTask(@PathVariable String email, @PathVariable UUID idTask, @RequestBody UpdateTask taskUpdated ){
        var user = DataBase.getUserByEmail(email);

        if(user == null) {
            return ResponseEntity.badRequest().body(new ErrorData("Usuário não localizado."));
        }

        var taskUpdateFilter = user.getTasks().stream().filter(t -> t.getId().equals(idTask)).findAny();

        if(taskUpdateFilter == null) {
            return ResponseEntity.badRequest().body(new ErrorData("Recado não encontrado."));
        }

        taskUpdateFilter.get().UpdateTask(taskUpdated);
        
        return ResponseEntity.noContent().build();
    }
}
