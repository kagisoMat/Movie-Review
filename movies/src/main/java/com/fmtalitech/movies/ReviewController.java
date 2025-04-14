package com.fmtalitech.movies;

import com.fmtalitech.movies.Review;
import com.fmtalitech.movies.ReviewRequest;
import com.fmtalitech.movies.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

// ReviewController.java
@RestController
@RequestMapping("/api/v1/reviews")
@CrossOrigin(origins = "*")
public class ReviewController {

    @Autowired
    private ReviewService reviewService;

    @PostMapping
    public ResponseEntity<Review> createReview(@RequestBody ReviewRequest request) {
        Review createdReview = reviewService.createReview(request.getReviewBody(), request.getImdbId());
        return new ResponseEntity<>(createdReview, HttpStatus.CREATED);
    }
}
