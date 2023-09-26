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
        validateUserRequest(userDto);
        User user = Mapper.mapUserDtoToUserEntity(userDto);
        User registeredUser = userRepository.save(user);
        UserReponseInfo userRequestInfo = Mapper.userReponseInfo(registeredUser);
        ApiResponse<UserReponseInfo> createUserApiResponse = new ApiResponse<>(200, "user has been successfully created", userRequestInfo);
        return createUserApiResponse;
    }

    private void validateUserRequest(CreateUserDto userDto) {
        if (userDto.getFirstName().isEmpty() || userDto.getLastName().isEmpty() || userDto.getUserType().isEmpty() || userDto.getPassword().isEmpty() || userDto.getConfirmPassword().isEmpty() || userDto.getEmail().isEmpty()) {
            throw new IllegalArgumentException("All the field are mandatory ");
        }
        if (userDto.getFirstName().contains(" ") || userDto.getLastName().contains(" ") || userDto.getUserType().contains(" ") || userDto.getEmail().contains(" ")) {
            throw new IllegalArgumentException("Fields should not be empty");
        }
        if (!userDto.getUserType().equalsIgnoreCase("BORROWER") && !userDto.getUserType().equalsIgnoreCase("LENDER")) {
            throw new IllegalArgumentException("Only BORROWER or LENDER accounts can be created");
        }
        if (userRepository.existsByUserName(userDto.getUserName())) {
            throw new IllegalArgumentException(userDto.getUserName() + " already exist !! ");
        }
        if (userDto.getPassword().contains(" ")) {
            throw new IllegalArgumentException("Password should not contains white space");
        }
        if (!userDto.getPassword().equals(userDto.getConfirmPassword())) {
            throw new IllegalArgumentException("Password mis-match.");
        }
        if (userRepository.existsByEmail(userDto.getEmail())) {
            throw new IllegalArgumentException("Email already exist.");
        }
    }
}


