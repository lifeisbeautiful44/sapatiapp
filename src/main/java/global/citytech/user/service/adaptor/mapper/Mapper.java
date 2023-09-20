package global.citytech.user.service.adaptor.mapper;

import global.citytech.user.repository.User;
import global.citytech.user.service.adaptor.dto.CreateUserDto;
import global.citytech.user.service.adduser.UserReponseInfo;


public class Mapper {
    public static CreateUserDto mapUserEntityToDto(User savedUser) {

        CreateUserDto userDto = new CreateUserDto();
        userDto.setId(savedUser.getId());
        userDto.setFirstName(savedUser.getFirstName());
        userDto.setLastName(savedUser.getLastName());
        userDto.setUserName(savedUser.getUserName());
        userDto.setEmail(savedUser.getEmail());
        userDto.setPassword(savedUser.getPassword());
        userDto.setConfirmPassword(savedUser.getConfirmPassword());
        userDto.setUserType(savedUser.getUserType());
        userDto.setStatus(savedUser.getStatus());
        userDto.setCreatedAt(savedUser.getCreatedAt());

        return userDto;

    }

    public static User mapUserDtoToUserEntity(CreateUserDto userDto) {

        User user = new User();
        user.setFirstName(userDto.getFirstName());
        user.setLastName(userDto.getLastName());
        user.setUserName(userDto.getUserName());
        user.setEmail(userDto.getEmail());
        user.setPassword(userDto.getPassword());
        user.setConfirmPassword(userDto.getConfirmPassword());
        user.setUserType(userDto.getUserType());
        user.setStatus(userDto.getStatus());
        user.setCreatedAt(userDto.getCreatedAt());

        return user;
    }

    public static UserReponseInfo userReponseInfo(User user) {
        UserReponseInfo addUserRequestInfo = new UserReponseInfo();
        addUserRequestInfo.setFirstName(user.getFirstName());
        addUserRequestInfo.setLastName(user.getLastName());
        addUserRequestInfo.setUserType(user.getUserType());
        addUserRequestInfo.setUserType(user.getUserName());

        return addUserRequestInfo;
    }

}