package global.citytech.user.service.updateuserstatus;


import global.citytech.cashflow.service.cashflow.CashFlowSevice;
import global.citytech.common.exception.CustomResponseException;
import global.citytech.user.repository.User;
import global.citytech.user.repository.UserRepository;
import global.citytech.common.apiresponse.ApiResponse;
import jakarta.inject.Inject;

public class UpdateUserStatusServiceImpl implements UpdateUserStatusService {
    @Inject
    private CashFlowSevice newVerifiedUserCashTable;
    @Inject
    private UserRepository userRepository;

    @Override
    public ApiResponse<String> updateUserStatus(UserStatusDto userStatusDto) {
        User updateUserStatus = validateUserStatus(userStatusDto);
        updateUserStatus.setStatus(true);
        User updatedUserStatus = userRepository.update(updateUserStatus);
        // setting the initial value of the new  user in the CashInformationTable
        newVerifiedUserCashTable.saveNewUserCashInformation(updatedUserStatus);
        ApiResponse<String> userUserApiResponse = new ApiResponse<>(200, "UserStatus has been successfully active", "UserName: " + updatedUserStatus.getUserName() + " is activated");
        return userUserApiResponse;
    }

    private User validateUserStatus(UserStatusDto userStatus) {

        if (userStatus.getUserName().isEmpty()) {
            throw new CustomResponseException(400, "bad request", "UserName is not provided.");
        }
        User user = userRepository.findByUserName(userStatus.getUserName()).orElseThrow(
                () ->
                {throw new CustomResponseException(400, "bad request", "The provided user doesn't exist.");}
        );
        if (user.getStatus() == true) {
            throw new CustomResponseException(400, "bad request", "The user status is already verified.");
        }
        return user;
    }
}
