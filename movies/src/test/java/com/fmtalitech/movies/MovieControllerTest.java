package com.fmtalitech.movies;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = MovieController.class)
class MovieControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private MovieService movieService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void shouldGetAllMovies() throws Exception {
        Movie movie = new Movie();
        movie.setImdbId("tt1234567");
        movie.setTitle("Test Movie");

        when(movieService.allMovies()).thenReturn(List.of(movie));

        mockMvc.perform(get("/api/v1/movies"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].imdbId").value("tt1234567"))
                .andExpect(jsonPath("$[0].title").value("Test Movie"));
    }

    @Test
    void shouldGetSingleMovieByImdbId() throws Exception {
        Movie movie = new Movie();
        movie.setImdbId("tt1234567");
        movie.setTitle("Test Movie");

        when(movieService.singleMovie("tt1234567")).thenReturn(Optional.of(movie));

        mockMvc.perform(get("/api/v1/movies/tt1234567"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("Test Movie"));
    }
}
