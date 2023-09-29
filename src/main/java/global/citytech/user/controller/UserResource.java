package global.citytech.user.controller;


import global.citytech.cashflow.service.cashflow.LoadBalanceResponse;
import global.citytech.cashflow.service.dto.CashDto;
import global.citytech.cashflow.service.cashflow.CashFlowSevice;
import global.citytech.notification.service.CheckPaymentDeadLineService;
import global.citytech.transactionhistory.service.transactionhistorylist.TransactionHistoryDto;
import global.citytech.transactionhistory.service.transactionhistorylist.TransactionHistoryListService;
import global.citytech.common.apiresponse.ApiResponse;
import global.citytech.user.service.adaptor.dto.CreateUserDto;
import global.citytech.user.service.adduser.AddUserService;
import global.citytech.user.service.adduser.UserReponseInfo;
import global.citytech.user.service.updateblacklist.UpdateBlackListUserStatusService;
import global.citytech.user.service.updateuserstatus.UpdateUserStatusService;
import global.citytech.user.service.updateuserstatus.UserStatusDto;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.annotation.*;
import jakarta.inject.Inject;


@Controller("/api/v1/")
public class UserResource {
    @Inject
    private   AddUserService addUserService;
    @Inject
    private  UpdateUserStatusService updateUserStatusService;
    @Inject
    private CashFlowSevice cashFlowSevice;
    @Inject
    private UpdateBlackListUserStatusService updateBlackListUserStatusService;
    @Inject
   private  TransactionHistoryListService transactionHistoryList;
    @Inject
    private CheckPaymentDeadLineService checkService;



    @Inject
    public UserResource(AddUserService addUserService,
                        UpdateUserStatusService updateUserStatusService) {
        this.addUserService = addUserService;
        this.updateUserStatusService = updateUserStatusService;
    }
    @Post("/register")
    public HttpResponse<ApiResponse<UserReponseInfo>> registerUser(@Body CreateUserDto userDto) {
        ApiResponse<UserReponseInfo> registerUser = addUserService.registerUser(userDto);
        return HttpResponse.ok().body(registerUser);
    }

    @Put("/approve/user")
    public HttpResponse<ApiResponse<String>> activateUser(@Body UserStatusDto userStatus) {
        ApiResponse<String> userStatusUpdated = updateUserStatusService.updateUserStatus(userStatus);
        return HttpResponse.ok().body(userStatusUpdated);
    }
    @Post("/loadbalance")
    public HttpResponse<ApiResponse<LoadBalanceResponse>> loadBalance(@Body CashDto cashDto)
    {

       ApiResponse<LoadBalanceResponse> loadBalanceResponse =  cashFlowSevice.loadBalance(cashDto);

       return  HttpResponse.ok().body(loadBalanceResponse);

    }

    @Post("/transaction/list")
    public HttpResponse<ApiResponse> getList(@Body TransactionHistoryDto transactionDto)
    {
        ApiResponse  response =   transactionHistoryList.findAllTransactionHistory(transactionDto);
        return HttpResponse.ok().body(response);
    }

    @Post("/blacklist")
    public void updateBlackList(@Body UserStatusDto userStatusDto)
    {
        updateBlackListUserStatusService.updateBlackListUser(userStatusDto);
    }

}
