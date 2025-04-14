package com.fmtalitech.movies;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.Map;


// RegisterController.java
@RestController
@RequestMapping("/api/v1/register")
@CrossOrigin(origins = "*")
public class RegisterController {

    @Autowired
    private RegisterService registerService;

    @PostMapping
    public ResponseEntity<Register> registerUser(@RequestBody RegisterRequest request) {
        Register newUser = registerService.createRegister(request);
        return new ResponseEntity<>(newUser, HttpStatus.CREATED);
    }
}

