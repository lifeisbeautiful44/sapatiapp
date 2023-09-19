package global.citytech.user.service.adduser;

import global.citytech.user.service.adaptor.ApiResponse;
import global.citytech.user.service.adaptor.dto.CreateUserDto;

public interface AddUserService {

    ApiResponse<?> registerUser(CreateUserDto userDto);
}
