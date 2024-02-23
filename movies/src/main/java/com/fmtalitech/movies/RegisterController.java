package com.fmtalitech.movies;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.Map;


@RestController
@RequestMapping("/api/v1/register")
@CrossOrigin(origins = "*")
public class RegisterController {

    @Autowired
    private RegisterService registerService;

    @PostMapping("/register")
    public ResponseEntity<Register> registerUser(@RequestBody Map<String, String> user) {
        String email = user.get("email");
        String password = user.get("password");
        return new ResponseEntity<>(registerService.createRegister(user.get("register"), user.get("imdbId")), HttpStatus.CREATED);
    }
}
