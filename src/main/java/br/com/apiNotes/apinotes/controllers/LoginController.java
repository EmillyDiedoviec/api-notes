package br.com.apiNotes.apinotes.controllers;

import br.com.apiNotes.apinotes.dataBase.DataBase;
import br.com.apiNotes.apinotes.dtos.ErrorData;
import br.com.apiNotes.apinotes.dtos.RequestLogin;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/login")
public class LoginController {
    @PostMapping
    public ResponseEntity loginUser(@RequestBody @Valid RequestLogin login){
        try {
            var user = DataBase.getEmail(login.email());
            if(user.getPassword().equals(login.password())){
                return ResponseEntity.ok().body(login);
            }
            return ResponseEntity.badRequest().body(new ErrorData("E-mail ou senha inválidos."));

        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new ErrorData("Login inválido."));
        }
    }
}
