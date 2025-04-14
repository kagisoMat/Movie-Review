package com.fmtalitech.movies;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fmtalitech.movies.register.Register;
import com.fmtalitech.movies.register.RegisterController;
import com.fmtalitech.movies.register.RegisterRequest;
import com.fmtalitech.movies.register.RegisterService;
import org.bson.types.ObjectId;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(RegisterController.class)
class RegisterControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private RegisterService registerService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void shouldRegisterMovieAndReturnCreatedStatus() throws Exception {
        // Arrange
        RegisterRequest request = new RegisterRequest();
        request.setImdbId("tt1234567");
        request.setTitle("Updated Movie Title");
        request.setName("Alice");
        request.setSurname("Smith");
        request.setEmail("alice@example.com");
        request.setUsername("alicesmith");
        request.setPassword("securepassword");

        Register mockResponse = new Register(
                new ObjectId(),
                request.getImdbId(),
                request.getName(),
                request.getSurname(),
                request.getEmail(),
                request.getUsername(),
                request.getPassword(),
                request.getTitle()
        );

        when(registerService.registerMovie(any(Register.class)))
                .thenReturn(mockResponse);

        // Act & Assert
        mockMvc.perform(post("/api/v1/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.username").value("alicesmith"))
                .andExpect(jsonPath("$.email").value("alice@example.com"))
                .andExpect(jsonPath("$.imdbId").value("tt1234567"))
                .andExpect(jsonPath("$.title").value("Updated Movie Title"));
    }
}
