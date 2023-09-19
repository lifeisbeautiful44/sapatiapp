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

        Optional<User> userDetails = userRepository.findByUserName(loginRequest.getUserName());

        try {
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
        } catch (IllegalArgumentException e) {
            return new ApiResponse<>(400, "Bad Request", e.getMessage());

        }
    }
}
