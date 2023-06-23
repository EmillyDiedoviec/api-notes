package br.com.apiNotes.apinotes.controllers;

import br.com.apiNotes.apinotes.dataBase.DataBase;
import br.com.apiNotes.apinotes.dtos.CreateUser;
import br.com.apiNotes.apinotes.dtos.ErrorData;
import br.com.apiNotes.apinotes.dtos.UserDetail;
import br.com.apiNotes.apinotes.models.User;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/users")
public class UserController {
    @PostMapping
    public ResponseEntity createUser(@RequestBody CreateUser data){
        if (DataBase.userExitsByEmail(data.email())){
            return ResponseEntity.badRequest().body(new ErrorData("Email já cadastrado"));
        }

        if (!DataBase.passwordUser(data.password(), data.repassword())){
            return ResponseEntity.badRequest().body(new ErrorData("As senhas devem ser iguais."));
        }

        var user = new User(
                data.email(),
                data.password()
        );

        DataBase.addUser(user);

        return ResponseEntity.ok().body(user);
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
