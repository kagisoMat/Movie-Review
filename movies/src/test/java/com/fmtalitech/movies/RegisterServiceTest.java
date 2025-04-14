package com.fmtalitech.movies;

import com.mongodb.client.result.UpdateResult;
import org.bson.types.ObjectId;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.data.mongodb.core.ExecutableUpdateOperation;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Update;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class RegisterServiceTest {

    @InjectMocks
    private RegisterService registerService;

    @Mock
    private RegisterRepository registerRepository;

    @Mock
    private MongoTemplate mongoTemplate;

    // 🔥 Correct generic type for Movie
    @Mock
    private ExecutableUpdateOperation.ExecutableUpdate<Movie> executableUpdate;

    @Mock
    private ExecutableUpdateOperation.TerminatingUpdate<Movie> terminatingUpdate;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateRegister_successful() {
        // Given
        RegisterRequest request = new RegisterRequest();
        request.setImdbId("tt123456");
        request.setName("John");
        request.setSurname("Doe");
        request.setEmail("john.doe@example.com");
        request.setUsername("johndoe");
        request.setPassword("secure123");

        Register mockRegister = new Register(
                new ObjectId(),
                request.getImdbId(),
                request.getName(),
                request.getSurname(),
                request.getEmail(),
                request.getUsername(),
                request.getPassword()
        );

        when(registerRepository.insert(any(Register.class))).thenReturn(mockRegister);

        // ✅ Correct mocking chain with Movie type
        when(mongoTemplate.update(Movie.class)).thenReturn(executableUpdate);
        when(executableUpdate.matching(any(Criteria.class))).thenReturn(executableUpdate);
        when(executableUpdate.apply(any(Update.class))).thenReturn(terminatingUpdate);
        when(terminatingUpdate.first()).thenReturn(mock(UpdateResult.class));

        // When
        Register result = registerService.createRegister(request);

        // Then
        assertNotNull(result);
        assertEquals("John", result.getName());
        assertEquals("Doe", result.getSurname());

        verify(registerRepository).insert(any(Register.class));
        verify(mongoTemplate).update(Movie.class);
    }
}