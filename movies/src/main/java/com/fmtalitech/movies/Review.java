package com.fmtalitech.movies;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "reviews")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Review {

    @Id
    private String id; // Changed from ObjectId to String

    private String body;
    private String imdbId; // Add imdbId to associate the review with a movie

    // Constructor to initialize body and imdbId
    public Review(String body, String imdbId) {
        this.body = body;
        this.imdbId = imdbId;
    }
}
