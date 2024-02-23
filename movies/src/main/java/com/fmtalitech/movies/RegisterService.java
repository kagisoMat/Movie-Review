package com.fmtalitech.movies;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

@Service
public class RegisterService {
    @Autowired
    private RegisterRepository registerRepository;

    @Autowired
    private MongoTemplate mongoTemplate;

    public Register createRegister(String registerBody, String imdbId) {
        Register register = registerRepository.insert(new Register());

        mongoTemplate.update(Movie.class)
                .matching(Criteria.where("imdbId").
                        is(imdbId))
                .apply(new Update().push("register").
                        value(register))
                .first();

        return register;
    }
}
