package com.fmtalitech.movies;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

// RegisterService.java
@Service
public class RegisterService {

    @Autowired
    private RegisterRepository registerRepository;

    @Autowired
    private MongoTemplate mongoTemplate;

    public Register createRegister(RegisterRequest request) {
        Register register = new Register();
        register.setImdbId(request.getImdbId());
        register.setName(request.getName());
        register.setSurname(request.getSurname());
        register.setEmail(request.getEmail());
        register.setUsername(request.getUsername());
        register.setPassword(request.getPassword());

        register = registerRepository.insert(register);

        // Optional: link this user to a movie
        mongoTemplate.update(Movie.class)
                .matching(Criteria.where("imdbId").is(request.getImdbId()))
                .apply(new Update().push("registeredUsers").value(register))
                .first();

        return register;
    }
}
