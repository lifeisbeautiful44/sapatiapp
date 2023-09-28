package global.citytech.user.service.updateuserstatus;

import global.citytech.common.apiresponse.ApiResponse;

public interface UpdateUserStatusService {
    ApiResponse<String> updateUserStatus(UserStatusDto userStatus);
}
