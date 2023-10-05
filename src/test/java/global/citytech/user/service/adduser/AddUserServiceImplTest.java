
package global.citytech.user.service.adduser;


import global.citytech.common.apiresponse.ApiResponse;
import global.citytech.common.exception.CustomResponseException;
import global.citytech.user.repository.User;
import global.citytech.user.repository.UserRepository;
import global.citytech.user.service.adaptor.dto.CreateUserDto;
import jakarta.inject.Inject;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import org.mockito.Mockito;


import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;


public class AddUserServiceImplTest {


    private  AddUserServiceImpl addUserService;
    private  UserRepository userRepository;


    @BeforeEach
    void setUp()
    {
        this.userRepository = Mockito.mock(UserRepository.class);
         this.addUserService = new AddUserServiceImpl(userRepository);
    }

    @Test
    void shouldCreateUserIfAllFieldArePresent()
    {
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

        User savedUser = new User(); // Mocked saved user
        Mockito.when(userRepository.save(any(User.class))).thenReturn(savedUser);

        // When
        ApiResponse<UserReponseInfo> response = addUserService.registerUser(createUserDto);

        // Then
        Assertions.assertNotNull(response);
        assertEquals(200, response.getCode());
    }


    @Test
    void shouldThrowExceptionWhenFieldsAreEmpty() {
        // Given: Create a CreateUserDto with empty fields
        CreateUserDto createUserDto = new CreateUserDto(
                1L,
                "",     // Empty first name
                "",     // Empty last name
                "",     // Empty username
                "",     // Empty email
                "",     // Empty password
                "",     // Empty confirmPassword
                "",     // Empty userType
                false,
                LocalDateTime.now().toString()
        );

        // When: Attempt to register a user with empty fields
        Executable executable = () -> addUserService.registerUser(createUserDto);

        // Then: Verify that a CustomResponseException is thrown with the expected details
        CustomResponseException exception = assertThrows(CustomResponseException.class, executable);

        assertEquals("All the field are mandatory.", exception.getData());
    }
}


