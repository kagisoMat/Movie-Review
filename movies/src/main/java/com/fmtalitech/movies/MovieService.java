package com.fmtalitech.movies;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MovieService {

    private final MovieRepository movieRepository;  // Define the repository

    // Constructor injection is the recommended way to inject dependencies
    @Autowired
    public MovieService(MovieRepository movieRepository) {
        this.movieRepository = movieRepository;
    }

    // Method to retrieve all movies
    public List<Movie> allMovies() {
        return movieRepository.findAll();
    }

    // Method to retrieve a single movie by imdbId
    public Optional<Movie> singleMovie(String imdbId) {
        return movieRepository.findMovieByImdbId(imdbId);
    }
}
