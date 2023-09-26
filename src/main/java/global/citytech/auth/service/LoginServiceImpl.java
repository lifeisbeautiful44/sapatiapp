package global.citytech.auth.service;

import global.citytech.auth.service.adaptor.dto.LoginDto;
import global.citytech.user.repository.User;
import global.citytech.user.repository.UserRepository;
import global.citytech.user.service.adaptor.ApiResponse;
import jakarta.inject.Inject;

import java.util.Optional;

public class LoginServiceImpl implements LoginService {
    @Inject
    UserRepository userRepository;
    public LoginServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
    @Override
    public ApiResponse<?> login(LoginDto loginRequest) {

        validateLoginRequest(loginRequest);
        Optional<User> userDetails = userRepository.findByUserName(loginRequest.getUserName());
         if (userDetails.isPresent()) {
                User userExists = userDetails.get();
                if (userExists.getPassword().equals(loginRequest.getPassword())) {
                    return new ApiResponse<>(200, userExists.getUserName() + " has been successfully login. ", "Account Type: " + userExists.getUserType());

                } else {
                    throw new IllegalArgumentException("password doesnot match.");
                }
            } else {
                throw new IllegalArgumentException("User not found");
            }
        }
   private void  validateLoginRequest(LoginDto loginRequest)
    {
        if(loginRequest.getUserName().isEmpty())
        {
            throw new IllegalArgumentException("empty username field.");
        }
        if(loginRequest.getPassword().isEmpty())
        {
            throw new IllegalArgumentException("empty password field.");
        }
        if(loginRequest.getPassword().contains(" "))
        {
            throw new IllegalArgumentException("password contains white spaces");
        }
    }
}
