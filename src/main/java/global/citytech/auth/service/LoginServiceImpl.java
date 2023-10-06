package global.citytech.auth.service;

import global.citytech.auth.service.adaptor.dto.LoginDto;
import global.citytech.common.apiresponse.ApiResponse;
import global.citytech.common.exception.CustomResponseException;
import global.citytech.notification.service.CheckPaymentDeadLineService;
import global.citytech.user.repository.User;
import global.citytech.user.repository.UserRepository;
import jakarta.inject.Inject;

import java.util.Optional;

public class LoginServiceImpl implements LoginService {
    private UserRepository userRepository;

    private CheckPaymentDeadLineService checkPaymentDeadLineService;

    @Inject
    public LoginServiceImpl(UserRepository userRepository, CheckPaymentDeadLineService checkPaymentDeadLineService) {
        this.userRepository = userRepository;
        this.checkPaymentDeadLineService = checkPaymentDeadLineService;
    }

    @Override
    public ApiResponse<?> login(LoginDto loginRequest) {

        validateLoginRequest(loginRequest);
        Optional<User> userDetails = userRepository.findByUserName(loginRequest.getUserName());
        if (userDetails.isPresent()) {
            User userExists = userDetails.get();
            if (userExists.getPassword().equals(loginRequest.getPassword())) {
                checkPaymentDeadLineService.findThePaymentDeadline(loginRequest.getUserName());
                return new ApiResponse<>(200, userExists.getUserName() + " has been successfully login. ", "Account Type: " + userExists.getUserType());

            } else {
                throw new CustomResponseException(400, "bad request", "password doesn't match.");
            }
        } else {
            throw new CustomResponseException(400, "bad request", "User not found");
        }
    }


    private void validateLoginRequest(LoginDto loginRequest) {
        if (loginRequest.getUserName().isEmpty()) {
            throw new CustomResponseException(400, "bad request", "empty username field.");
        }
        if (loginRequest.getPassword().isEmpty()) {
            throw new CustomResponseException(400, "bad request", "empty password field.");
        }
        if (loginRequest.getPassword().contains(" ")) {
            throw new CustomResponseException(400, "bad request", "password contains white spaces.");
        }
    }
}
