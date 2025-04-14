package com.fmtalitech.movies;

import org.jetbrains.annotations.NotNull;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RegisterRepository extends MongoRepository<Register, String>{
    @NotNull Register insert(@NotNull Register register);
}
