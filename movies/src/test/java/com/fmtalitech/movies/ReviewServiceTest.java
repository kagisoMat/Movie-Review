package com.fmtalitech.movies;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.data.mongodb.core.ExecutableUpdateOperation;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

class ReviewServiceTest {

    @Mock
    private ReviewRepository reviewRepository;

    @Mock
    private MongoTemplate mongoTemplate;

    @InjectMocks
    private ReviewService reviewService;

    private ReviewRequest reviewRequest;
    private Movie movie;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        reviewRequest = new ReviewRequest();
        reviewRequest.setReviewBody("Awesome movie!");
        reviewRequest.setImdbId("tt1234567");

        movie = new Movie();
        movie.setImdbId("tt1234567");
    }

    @Test
    void shouldCreateReviewAndUpdateMovie() {
        Review review = new Review("Awesome movie!", "tt1234567");

        when(reviewRepository.save(any(Review.class))).thenReturn(review);
        when(mongoTemplate.findOne(any(Query.class), eq(Movie.class))).thenReturn(movie);

        // Mocking MongoTemplate fluent update chain
        ExecutableUpdateOperation.ExecutableUpdate<Movie> executableUpdate = mock(ExecutableUpdateOperation.ExecutableUpdate.class);
        ExecutableUpdateOperation.UpdateWithUpdate<Movie> updateWithUpdate = mock(ExecutableUpdateOperation.UpdateWithUpdate.class);
        ExecutableUpdateOperation.TerminatingUpdate<Movie> terminatingUpdate = mock(ExecutableUpdateOperation.TerminatingUpdate.class);

        when(mongoTemplate.update(Movie.class)).thenReturn(executableUpdate);
        when(executableUpdate.matching(any(Criteria.class))).thenReturn(updateWithUpdate);
        when(updateWithUpdate.apply(any(Update.class))).thenReturn(terminatingUpdate);
        when(terminatingUpdate.first()).thenReturn(null);

        Review savedReview = reviewService.createReview(reviewRequest);

        assertThat(savedReview).isNotNull();
        assertThat(savedReview.getBody()).isEqualTo("Awesome movie!");
        assertThat(savedReview.getImdbId()).isEqualTo("tt1234567");

        verify(reviewRepository).save(any(Review.class));
        verify(mongoTemplate).update(Movie.class);
    }

    @Test
    void shouldThrowWhenMovieNotFound() {
        when(reviewRepository.save(any(Review.class))).thenReturn(new Review("Test", "tt0000000"));
        when(mongoTemplate.findOne(any(Query.class), eq(Movie.class))).thenReturn(null);

        try {
            reviewService.createReview(reviewRequest);
        } catch (RuntimeException ex) {
            assertThat(ex.getMessage()).contains("Movie not found with imdbId");
        }

        verify(mongoTemplate, never()).update(eq(Movie.class));
    }

    @Test
    void shouldReturnReviewsByImdbId() {
        when(reviewRepository.findByImdbId("tt1234567")).thenReturn(List.of(
                new Review("Great!", "tt1234567"),
                new Review("Nice!", "tt1234567")
        ));

        List<Review> reviews = reviewService.getReviewsByMovie("tt1234567");

        assertThat(reviews).hasSize(2);
        verify(reviewRepository).findByImdbId("tt1234567");
    }
}
