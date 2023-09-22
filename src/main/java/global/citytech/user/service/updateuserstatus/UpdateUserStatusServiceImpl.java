package global.citytech.user.service.updateuserstatus;


import global.citytech.cashflow.repository.CashFlow;
import global.citytech.cashflow.service.CashFlowSevice;
import global.citytech.user.repository.User;
import global.citytech.user.repository.UserRepository;
import global.citytech.user.service.adaptor.ApiResponse;
import jakarta.inject.Inject;

import java.util.Optional;

public class UpdateUserStatusServiceImpl implements UpdateUserStatusService {
    @Inject
    private CashFlowSevice newVerifiedUserCashTable;
    @Inject
   private  UserRepository userRepository;
    @Override
    public ApiResponse<String> updateUserStatus(UserStatusDto userStatus) {
        User updateUserStatus = userRepository.findByUserName(userStatus.getUserName()).orElseThrow(
                () -> new IllegalArgumentException("The provided user doesnot exist")
        );
        updateUserStatus.setStatus(true);
        User veirfiedUser = userRepository.update(updateUserStatus);
        // setting the initial value of the new  user in the CashInformationTable
        newVerifiedUserCashTable.saveNewUserCashInformation(veirfiedUser);
        ApiResponse<String> userUserApiResponse = new ApiResponse<>(200, "UserStatus has been successfully active", "UserName: " + veirfiedUser.getUserName() + " is activated");
        return userUserApiResponse;

    }
}
