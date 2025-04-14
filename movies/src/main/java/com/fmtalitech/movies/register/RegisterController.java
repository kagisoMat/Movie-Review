package com.fmtalitech.movies.register;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/register")
@CrossOrigin(origins = "*")
public class RegisterController {

    @Autowired
    private RegisterService registerService;

    @PostMapping
    public ResponseEntity<Register> registerMovie(@RequestBody RegisterRequest registerRequest) {
        Register register = new Register();
        // Map request data to Register entity
        register.setImdbId(registerRequest.getImdbId());
        register.setName(registerRequest.getName());
        register.setSurname(registerRequest.getSurname());
        register.setEmail(registerRequest.getEmail());
        register.setUsername(registerRequest.getUsername());
        register.setPassword(registerRequest.getPassword());

        // Pass register entity to the service for further processing
        Register createdRegister = registerService.registerMovie(register);

        // Return the saved register information
        return new ResponseEntity<>(createdRegister, HttpStatus.CREATED);
    }
}
