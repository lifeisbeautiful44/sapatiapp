package global.citytech.auth.service;

import global.citytech.auth.service.adaptor.dto.LoginDto;
import global.citytech.common.apiresponse.ApiResponse;

public interface LoginService {

    ApiResponse<?> login(LoginDto loginRequest);
}
