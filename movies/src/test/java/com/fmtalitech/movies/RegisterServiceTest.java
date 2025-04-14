package com.fmtalitech.movies;

import com.fmtalitech.movies.register.Register;
import com.fmtalitech.movies.register.RegisterRepository;
import com.fmtalitech.movies.register.RegisterService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

class RegisterServiceTest {

    @Mock
    private RegisterRepository registerRepository;

    @Mock
    private MongoTemplate mongoTemplate;

    @InjectMocks
    private RegisterService registerService;

    private Register register;
    private Movie movie;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        register = new Register();
        register.setImdbId("tt1234567");
        register.setTitle("New Title");
        register.setName("John");
        register.setSurname("Doe");
        register.setEmail("john@example.com");
        register.setUsername("johndoe");
        register.setPassword("securepass");

        movie = new Movie();
        movie.setImdbId("tt1234567");
        movie.setTitle("Old Title");
    }

    @Test
    void shouldUpdateMovieAndSaveRegister() {
        // Mock MongoTemplate behavior
        when(mongoTemplate.findOne(any(Query.class), eq(Movie.class))).thenReturn(movie);
        when(registerRepository.save(register)).thenReturn(register);

        Register result = registerService.registerMovie(register);

        assertThat(result).isNotNull();
        assertThat(result.getName()).isEqualTo("John");

        // Verify interactions
        verify(mongoTemplate).findOne(any(Query.class), eq(Movie.class));
        verify(mongoTemplate).save(movie);
        verify(registerRepository).save(register);
    }

    @Test
    void shouldSaveRegisterWhenMovieNotFound() {
        // Mock movie not found
        when(mongoTemplate.findOne(any(Query.class), eq(Movie.class))).thenReturn(null);
        when(registerRepository.save(register)).thenReturn(register);

        Register result = registerService.registerMovie(register);

        assertThat(result).isNotNull();
        verify(mongoTemplate, never()).save(any(Movie.class));
        verify(registerRepository).save(register);
    }
}
