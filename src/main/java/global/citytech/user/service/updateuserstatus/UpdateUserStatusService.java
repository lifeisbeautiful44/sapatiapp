package global.citytech.user.service.updateuserstatus;

import global.citytech.user.service.adaptor.ApiResponse;

public interface UpdateUserStatusService {

    ApiResponse<String> updateUserStatus(Long userId);

}
