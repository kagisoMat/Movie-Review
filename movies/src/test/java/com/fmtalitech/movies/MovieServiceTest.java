package com.fmtalitech.movies;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

class MovieServiceTest {

    @Mock
    private MovieRepository movieRepository;

    @InjectMocks
    private MovieService movieService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void shouldReturnAllMovies() {
        Movie movie = new Movie(); // Create your own movie object and set fields
        movie.setImdbId("tt0111161");
        movie.setTitle("The Shawshank Redemption");

        when(movieRepository.findAll()).thenReturn(List.of(movie));

        List<Movie> movies = movieService.allMovies();
        assertThat(movies).hasSize(1);
        assertThat(movies.get(0).getImdbId()).isEqualTo("tt0111161");
    }

    @Test
    void shouldReturnMovieByImdbId() {
        Movie movie = new Movie();
        movie.setImdbId("tt0111161");
        movie.setTitle("The Shawshank Redemption");

        when(movieRepository.findMovieByImdbId("tt0111161")).thenReturn(Optional.of(movie));

        Optional<Movie> result = movieService.singleMovie("tt0111161");
        assertThat(result).isPresent();
        assertThat(result.get().getTitle()).isEqualTo("The Shawshank Redemption");
    }
}
