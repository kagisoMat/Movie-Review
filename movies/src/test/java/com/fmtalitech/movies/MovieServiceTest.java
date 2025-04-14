package com.fmtalitech.movies;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class MovieServiceTest {

    private MovieRepository movieRepository;
    private MovieService movieService;

    @BeforeEach
    void setUp() {
        movieRepository = Mockito.mock(MovieRepository.class);
        movieService = new MovieService(movieRepository);
    }

    @Test
    void testAllMovies() {
        // Arrange
        Movie movie1 = new Movie();
        movie1.setImdbId("tt1234567");
        movie1.setTitle("Test Movie 1");

        Movie movie2 = new Movie();
        movie2.setImdbId("tt7654321");
        movie2.setTitle("Test Movie 2");

        List<Movie> movies = Arrays.asList(movie1, movie2);
        when(movieRepository.findAll()).thenReturn(movies);

        // Act
        List<Movie> result = movieService.allMovies();

        // Assert
        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals("Test Movie 1", result.get(0).getTitle());
        verify(movieRepository, times(1)).findAll();
    }

    @Test
    void testSingleMovieFound() {
        // Arrange
        String imdbId = "tt1234567";
        Movie movie = new Movie();
        movie.setImdbId(imdbId);
        Optional<Movie> movieOptional = Optional.of(movie);

        when(movieRepository.findMovieByImdbId(imdbId)).thenReturn(movieOptional);

        // Act
        Optional<Movie> result = movieService.singleMovie(imdbId);

        // Assert
        assertTrue(result.isPresent());
        assertEquals(imdbId, result.get().getImdbId());
        verify(movieRepository, times(1)).findMovieByImdbId(imdbId);
    }

    @Test
    void testSingleMovieNotFound() {
        // Arrange
        String imdbId = "tt0000000";
        when(movieRepository.findMovieByImdbId(imdbId)).thenReturn(Optional.empty());

        // Act
        Optional<Movie> result = movieService.singleMovie(imdbId);

        // Assert
        assertFalse(result.isPresent());
        verify(movieRepository, times(1)).findMovieByImdbId(imdbId);
    }
}
