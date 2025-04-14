package com.fmtalitech.movies;

import com.mongodb.client.result.UpdateResult;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.data.mongodb.core.ExecutableUpdateOperation;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Update;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class ReviewServiceTest {

    @InjectMocks
    private ReviewService reviewService;

    @Mock
    private ReviewRepository reviewRepository;

    @Mock
    private MongoTemplate mongoTemplate;

    @Mock
    private ExecutableUpdateOperation.ExecutableUpdate<Movie> executableUpdate;

    @Mock
    private ExecutableUpdateOperation.TerminatingUpdate<Movie> terminatingUpdate;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateReview_success() {
        // Given
        String reviewBody = "This was a great movie!";
        String imdbId = "tt9876543";
        Review savedReview = new Review("1", reviewBody);

        when(reviewRepository.save(any(Review.class))).thenReturn(savedReview);

        when(mongoTemplate.update(Movie.class)).thenReturn(executableUpdate);
        when(executableUpdate.matching(any(Criteria.class))).thenReturn(executableUpdate);
        when(executableUpdate.apply(any(Update.class))).thenReturn(terminatingUpdate);
        when(terminatingUpdate.first()).thenReturn(mock(UpdateResult.class));

        // When
        Review result = reviewService.createReview(reviewBody, imdbId);

        // Then
        assertNotNull(result);
        assertEquals("1", result.getId());
        assertEquals(reviewBody, result.getBody());

        verify(reviewRepository, times(1)).save(any(Review.class));
        verify(mongoTemplate, times(1)).update(Movie.class);
        verify(executableUpdate, times(1)).matching(any(Criteria.class));
        verify(executableUpdate, times(1)).apply(any(Update.class));
        verify(terminatingUpdate, times(1)).first();
    }
}
