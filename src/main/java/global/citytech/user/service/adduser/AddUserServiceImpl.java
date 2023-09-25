package global.citytech.user.service.adduser;

import global.citytech.user.repository.User;
import global.citytech.user.repository.UserRepository;
import global.citytech.user.service.adaptor.ApiResponse;
import global.citytech.user.service.adaptor.dto.CreateUserDto;
import global.citytech.user.service.adaptor.mapper.Mapper;
import jakarta.inject.Inject;

public class AddUserServiceImpl implements AddUserService {
    @Inject
    private UserRepository userRepository;

    public AddUserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public ApiResponse<?> registerUser(CreateUserDto userDto) {
        User user = Mapper.mapUserDtoToUserEntity(userDto);
        try {
            validateUserRequest(user);
            User registeredUser = userRepository.save(user);

            UserReponseInfo userRequestInfo = Mapper.userReponseInfo(registeredUser);
            ApiResponse<UserReponseInfo> createUserApiResponse = new ApiResponse<>(200, "user has been successfully created", userRequestInfo);
            return createUserApiResponse;

        } catch (IllegalArgumentException e) {
            return new ApiResponse<>(400, "Bad Request", e.getMessage());
        }
    }

    private void validateUserRequest(User userRequest) {

        if (userRepository.existsByUserName(userRequest.getUserName())) {
            throw new IllegalArgumentException(userRequest.getUserName() + " already exist !! ");
        }
        if (!userRequest.getPassword().equals(userRequest.getConfirmPassword())) {
            throw new IllegalArgumentException("Password mis-match.");
        }
    }
}


