package global.citytech.user.service.adduser;

import global.citytech.common.apiresponse.ApiResponse;
import global.citytech.user.service.adaptor.dto.CreateUserDto;

public interface AddUserService {
    ApiResponse<UserReponseInfo> registerUser(CreateUserDto userDto);
}
