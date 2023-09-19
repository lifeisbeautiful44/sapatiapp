package global.citytech.user.service.updateuserstatus;


import global.citytech.user.repository.User;
import global.citytech.user.repository.UserRepository;
import global.citytech.user.service.adaptor.ApiResponse;
import jakarta.inject.Inject;

public class UpdateUserStatusServiceImpl implements UpdateUserStatusService {


    @Inject
    UserRepository userRepository;

    @Override
    public ApiResponse<String> updateUserStatus(Long userId) {



        User updateUserStatus = userRepository.findById(userId).orElseThrow(
                () -> new IllegalArgumentException("User with userId " + userId + " is not found")
        );
        updateUserStatus.setStatus(true);
        User veirfiedUser = userRepository.update(updateUserStatus);

        ApiResponse<String> userUserApiResponse = new ApiResponse<>(200, "UserStatus has been successfully active", "UserName: " + veirfiedUser.getUserName() + " is activated");

        return userUserApiResponse;
    }
}
