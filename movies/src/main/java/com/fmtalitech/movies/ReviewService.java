package com.fmtalitech.movies;

import org.springframework.data.mongodb.core.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Update;

import java.util.List;

@Service
public class ReviewService {

    @Autowired
    private ReviewRepository reviewRepository;

    @Autowired
    private MongoTemplate mongoTemplate;

    public Review createReview(ReviewRequest reviewRequest) {
        // Create a review from the request
        Review review = new Review(reviewRequest.getReviewBody(), reviewRequest.getImdbId());

        // Save the review to the database
        Review savedReview = reviewRepository.save(review);

        // Create a Query object with Criteria
        Query query = new Query(Criteria.where("imdbId").is(reviewRequest.getImdbId()));

        // Find the movie using the imdbId from the reviewRequest
        Movie movie = mongoTemplate.findOne(query, Movie.class);  // Correct method usage with Query object

        if (movie != null) {
            // Update the movie by adding the new review to its list of reviews
            mongoTemplate.update(Movie.class)
                    .matching(Criteria.where("imdbId").is(reviewRequest.getImdbId()))
                    .apply(new Update().push("reviews", savedReview)) // Add the review to the movie's reviews list
                    .first();
        } else {
            throw new RuntimeException("Movie not found with imdbId: " + reviewRequest.getImdbId());
        }

        return savedReview; // Return the saved review
    }

    public List<Review> getReviewsByMovie(String imdbId) {
        // Fetch all reviews for the movie using imdbId
        return reviewRepository.findByImdbId(imdbId); // You may want to add this custom method in ReviewRepository
    }
}

