package com.fmtalitech.movies;

import lombok.Data;

@Data
public class RegisterRequest {
    private String imdbId;
    private String title;  // Add title field here
    private String name;
    private String surname;
    private String email;
    private String username;
    private String password;
}
