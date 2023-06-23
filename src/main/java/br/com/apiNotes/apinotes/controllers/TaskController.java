package br.com.apiNotes.apinotes.controllers;

import br.com.apiNotes.apinotes.dataBase.DataBase;
import br.com.apiNotes.apinotes.dtos.*;
import br.com.apiNotes.apinotes.models.Task;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/tasks")
public class TaskController {
    @PostMapping("/{email}")
    public ResponseEntity addTasks(@PathVariable String email, @RequestBody @Valid AddTask newTask){
        var user = DataBase.getUserByEmail(email);

        if(user == null) {
            return ResponseEntity.badRequest().body(new ErrorData("Usuário não localizado."));
        }
        user.getTasks().add(new Task(newTask));
        return ResponseEntity.ok().body(newTask);
    }

    @GetMapping("/{email}")
    public ResponseEntity getTasks(@PathVariable String email, @RequestParam(required = false) String title, @RequestParam(required = false) boolean archived){
        var tasks = DataBase.getAllTasks(email);
        var esseTemReatribuicao = DataBase.getEmail(email).getTasks();

        if(tasks == null) {
            return ResponseEntity.badRequest().body(new ErrorData("Task não localizada."));
        }

        if(title != null) {
            esseTemReatribuicao = esseTemReatribuicao.stream().filter(t -> t.getTitle().contains((title))).toList();
            return ResponseEntity.ok().body(esseTemReatribuicao);
        }

        if(archived) {
            esseTemReatribuicao = esseTemReatribuicao.stream().filter(a -> a.getArchive().equals(true)).toList();
            return ResponseEntity.ok().body(esseTemReatribuicao);
        }
        return ResponseEntity.ok().body(tasks);
    }

    @DeleteMapping ("/{email}/{idTask}")
    public ResponseEntity deleteTasks(@PathVariable String email, @PathVariable UUID idTask){
        var user = DataBase.getUserByEmail(email);
        if(user == null) {
            return ResponseEntity.badRequest().body(new ErrorData("Usuário não localizado."));
        }
        var taskId = DataBase.getTaskByID(idTask, email);

        user.getTasks().remove(taskId);
        return ResponseEntity.ok().body(taskId);
    }

    @PutMapping ("/{email}/{idTask}")
    public ResponseEntity updateTask(@PathVariable String email, @PathVariable UUID idTask, @RequestBody UpdateTask taskUpdated ){
        var user = DataBase.getUserByEmail(email);

        var taskUpdateFilter = user.getTasks().stream().filter(t -> t.getId().equals(idTask)).findAny();

        if(taskUpdateFilter == null) {
            return ResponseEntity.badRequest().body(new ErrorData("Recado não encontrado."));
        }

        taskUpdateFilter.get().UpdateTask(taskUpdated);
        
        return ResponseEntity.ok().body(taskUpdated);
    }

    @PostMapping("/{email}/{idTask}/archive")
    public ResponseEntity archiveTask(@PathVariable String email, @PathVariable UUID idTask) {

        var task = DataBase.getTaskByID(idTask, email);
        var archived = task.getArchive();

        task.setArchive(!archived);
        return ResponseEntity.ok().body(task.getArchive());
    }
}
