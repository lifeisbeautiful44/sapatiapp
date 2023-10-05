
package global.citytech.user.service.adduser;


import global.citytech.common.apiresponse.ApiResponse;
import global.citytech.common.exception.CustomResponseException;
import global.citytech.user.repository.User;
import global.citytech.user.repository.UserRepository;
import global.citytech.user.service.adaptor.dto.CreateUserDto;
import io.micronaut.test.extensions.junit5.annotation.MicronautTest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import org.mockito.Mockito;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;

@MicronautTest
public class AddUserServiceImplTest {


    private AddUserServiceImpl addUserService;
    private UserRepository userRepository;


    @BeforeEach
    void setUp() {
        this.userRepository = Mockito.mock(UserRepository.class);
        this.addUserService = new AddUserServiceImpl(userRepository);
    }

    @Test
    void shouldCreateUserIfAllFieldArePresent() {
//given
        CreateUserDto createUserDto = new CreateUserDto(
                1l,
                "srijansil",
                "bohara",
                "srijansil123",
                "srijan@gmail.com",
                "test123",
                "test123",
                "Borrower",
                false,
                LocalDateTime.now().toString()
        );

        User savedUser = new User();
        Mockito.when(userRepository.save(any(User.class))).thenReturn(savedUser);
        // When
        ApiResponse<UserReponseInfo> response = addUserService.registerUser(createUserDto);
        // Then
        Assertions.assertNotNull(response);
        assertEquals(200, response.getCode());
    }


    @Test
    void shouldThrowExceptionWhenFieldsAreEmpty() {
        // Given
        CreateUserDto createUserDto = new CreateUserDto(
                1L,
                "",
                "",
                "",
                "",
                "",
                "",
                "",
                false,
                LocalDateTime.now().toString()
        );

        // When
        Executable executable = () -> addUserService.registerUser(createUserDto);

        // Then
        CustomResponseException exception = assertThrows(CustomResponseException.class, executable);

        assertEquals("All the field are mandatory.", exception.getData());
    }
}


