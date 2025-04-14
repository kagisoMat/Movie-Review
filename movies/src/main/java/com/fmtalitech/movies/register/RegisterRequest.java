package com.fmtalitech.movies.register;

import lombok.Data;

@Data
public class RegisterRequest {
    private String imdbId;
    private String title;
    private String name;
    private String surname;
    private String email;
    private String username;
    private String password;

    public RegisterRequest() {} // ✅ Add this!

    public RegisterRequest(String username, String email, String password) {
        this.username = username;
        this.email = email;
        this.password = password;
    }
}
