package com.fmtalitech.movies;

import lombok.Data;

@Data
public class ReviewRequest {
    private String reviewBody;
    private String imdbId; // So we can attach this review to a movie
}
