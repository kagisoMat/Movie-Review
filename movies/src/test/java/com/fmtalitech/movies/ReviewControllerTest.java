package com.fmtalitech.movies;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ReviewController.class)
class ReviewControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ReviewService reviewService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void shouldCreateReview() throws Exception {
        ReviewRequest request = new ReviewRequest();
        request.setReviewBody("Amazing movie!");
        request.setImdbId("tt1234567");

        Review review = new Review("1", "Amazing movie!", "tt1234567");

        when(reviewService.createReview(any(ReviewRequest.class))).thenReturn(review);

        mockMvc.perform(post("/api/v1/reviews")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.body").value("Amazing movie!"))
                .andExpect(jsonPath("$.imdbId").value("tt1234567"));
    }

    @Test
    void shouldReturnReviewsByImdbId() throws Exception {
        List<Review> reviews = List.of(
                new Review("1", "Great!", "tt1234567"),
                new Review("2", "Loved it!", "tt1234567")
        );

        when(reviewService.getReviewsByMovie("tt1234567")).thenReturn(reviews);

        mockMvc.perform(get("/api/v1/reviews/movie/tt1234567"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].body").value("Great!"));
    }
}
