package com.fmtalitech.movies.register;

import com.fmtalitech.movies.Movie;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

@Service
public class RegisterService {

    @Autowired
    private RegisterRepository registerRepository;

    @Autowired
    private MongoTemplate mongoTemplate;

    public Register registerMovie(Register register) {
        // Create a Query object with Criteria
        Query query = new Query(Criteria.where("imdbId").is(register.getImdbId()));

        // Find the movie by imdbId
        Movie movie = mongoTemplate.findOne(query, Movie.class);

        // If a movie is found, update the movie details
        if (movie != null) {
            movie.setTitle(register.getTitle()); // Update movie title with the one from register
            mongoTemplate.save(movie);  // Save the updated movie back to the database
        }

        // Save the registration info
        return registerRepository.save(register);
    }
}
