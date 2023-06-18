package br.com.apiNotes.apinotes.controllers;

import br.com.apiNotes.apinotes.dataBase.DataBase;
import br.com.apiNotes.apinotes.dtos.CreateUser;
import br.com.apiNotes.apinotes.dtos.ErrorData;
import br.com.apiNotes.apinotes.dtos.UserDetail;
import br.com.apiNotes.apinotes.models.User;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class UserController {
    @PostMapping
    public ResponseEntity createUser(@RequestBody CreateUser data){
        if (DataBase.userExitsByEmail(data.email())){
            return ResponseEntity.badRequest().body(new ErrorData("Email já cadastrado"));
        }
        var user = new User(
                data.email(),
                data.password()
        );

        DataBase.addUser(user);

        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{email}")
    public  ResponseEntity getUser(@PathVariable String email){
        var user = DataBase.getUserByEmail(email);

        if(user == null){
            return ResponseEntity.badRequest().body(new ErrorData("User não localizado"));
        }
        var userDetails = new UserDetail(user);

        return  ResponseEntity.ok(userDetails);
    }
}
