package global.citytech.auth.service;

import global.citytech.auth.service.adaptor.dto.LoginDto;
import global.citytech.user.service.adaptor.ApiResponse;

public interface LoginService {

    ApiResponse<?> login(LoginDto loginRequest);
}
