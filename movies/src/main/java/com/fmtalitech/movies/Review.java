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

    // Constructor
    public Review(String body) {

        this.body = body;
    }
}
