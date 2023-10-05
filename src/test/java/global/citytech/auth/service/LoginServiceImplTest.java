package global.citytech.auth.service;

import global.citytech.auth.service.adaptor.dto.LoginDto;
import global.citytech.common.apiresponse.ApiResponse;
import global.citytech.common.exception.CustomResponseException;
import global.citytech.notification.service.CheckPaymentDeadLineService;
import global.citytech.user.repository.User;
import global.citytech.user.repository.UserRepository;
import io.micronaut.test.extensions.junit5.annotation.MicronautTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@MicronautTest
public class LoginServiceImplTest {

    private UserRepository userRepository;

    private CheckPaymentDeadLineService checkPaymentDeadLineService;

    private LoginServiceImpl loginService;

    @BeforeEach
    public void setUp()
    {
        this.userRepository = Mockito.mock(UserRepository.class);
        this.checkPaymentDeadLineService = Mockito.mock(CheckPaymentDeadLineService.class);
        this.loginService = new LoginServiceImpl(userRepository,checkPaymentDeadLineService);
    }

    @Test
    public void testSuccessfulLogin() {
        // Given
        LoginDto loginRequest = new LoginDto("username", "password");
        User userExists = new User();
        userExists.setUserName("username");
        userExists.setPassword("password");
        userExists.setUserType("userType");
        when(userRepository.findByUserName("username")).thenReturn(Optional.of(userExists));
        // When
        ApiResponse<?> response = loginService.login(loginRequest);

        // Then
        verify(checkPaymentDeadLineService, times(1)).findThePaymentDeadline("username");
        assertEquals(200, response.getCode());
        assertEquals("username has been successfully login. ", response.getMessage());
    }

    @Test
    public void testLoginWithIncorrectPassword() {
        // Given
        LoginDto loginRequest = new LoginDto("username", "wrongpassword");
        User userExists = new User();
        userExists.setUserName("username");
        userExists.setPassword("password");

        when(userRepository.findByUserName("username")).thenReturn(Optional.of(userExists));

        // When
        assertThrows(CustomResponseException.class, () -> loginService.login(loginRequest));

        // Then
        verify(checkPaymentDeadLineService, never()).findThePaymentDeadline(any());
    }

    @Test
    public void testLoginWithUserNotFound() {
        // Given
        LoginDto loginRequest = new LoginDto("nonexistentuser", "password");

        when(userRepository.findByUserName("nonexistentuser")).thenReturn(Optional.empty());

        // When
        assertThrows(CustomResponseException.class, () -> loginService.login(loginRequest));

        // Then
        verify(checkPaymentDeadLineService, never()).findThePaymentDeadline(any());
    }
}
