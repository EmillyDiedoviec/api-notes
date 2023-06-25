package br.com.apiNotes.apinotes.controllers;

import br.com.apiNotes.apinotes.dataBase.DataBase;
import br.com.apiNotes.apinotes.dtos.CreateUser;
import br.com.apiNotes.apinotes.dtos.ErrorData;
import br.com.apiNotes.apinotes.dtos.RequestLogin;
import br.com.apiNotes.apinotes.dtos.UserDetail;
import br.com.apiNotes.apinotes.models.User;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/users")
public class UserController {

    @GetMapping
    public ResponseEntity getUsers(){
        var userList = DataBase.getAllUsers();
        return ResponseEntity.ok().body(userList);
    }

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

        return ResponseEntity.ok().body("Conta criada com sucesso!");
    }


    @GetMapping("/{email}")
    public  ResponseEntity getUser(@PathVariable String email){
        var userValid = DataBase.userExitsByEmail(email);

        if(userValid == false){
            return ResponseEntity.badRequest().body(new ErrorData("User não localizado"));
        }

        var user = DataBase.getUserByEmail(email);


        return  ResponseEntity.ok().body(user);
    }


    @GetMapping("/login/{email}/{password}")
    public ResponseEntity login(@PathVariable @Valid String email, @PathVariable @Valid String password){
        try {
            var user = DataBase.getEmail(email);
            if(user.getEmail().equals(email) && user.getPassword().equals(password)){
                return ResponseEntity.ok().body(user);
            }
            return ResponseEntity.badRequest().body(new ErrorData("E-mail ou senha inválidos."));

        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new ErrorData("Login inválido."));
        }
    }

}
