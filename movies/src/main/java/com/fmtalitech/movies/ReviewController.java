package com.fmtalitech.movies;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/reviews")
@CrossOrigin(origins = "*")
public class ReviewController {

    @Autowired
    private ReviewService reviewService;

    @PostMapping
    public ResponseEntity<Review> createReview(@RequestBody ReviewRequest reviewRequest) {
        // Create the review by passing the ReviewRequest to the ReviewService
        Review createdReview = reviewService.createReview(reviewRequest);

        // Return the created review with a 201 CREATED status
        return new ResponseEntity<>(createdReview, HttpStatus.CREATED);
    }

    @GetMapping("/movie/{imdbId}")
    public ResponseEntity<List<Review>> getReviewsByMovie(@PathVariable String imdbId) {
        // Fetch all reviews for the movie
        List<Review> reviews = reviewService.getReviewsByMovie(imdbId);

        return new ResponseEntity<>(reviews, HttpStatus.OK);
    }

    // Additional methods for other CRUD operations (PUT, DELETE) can be added here
}

