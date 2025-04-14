package com.fmtalitech.movies;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Document(collection = "register")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Register {

    @Id
    private ObjectId id;

    private String imdbId;
    private String name;
    private String surname;
    private String email;
    private String username;
    private String password;

}