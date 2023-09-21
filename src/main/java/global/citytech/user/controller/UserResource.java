package global.citytech.user.controller;


import global.citytech.cashflow.service.CashDto;
import global.citytech.cashflow.service.CashFlowSevice;
import global.citytech.user.service.adaptor.ApiResponse;
import global.citytech.user.service.adaptor.dto.CreateUserDto;
import global.citytech.user.service.adduser.AddUserService;
import global.citytech.user.service.adduser.UserReponseInfo;
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
    public UserResource(AddUserService addUserService,
                        UpdateUserStatusService updateUserStatusService) {
        this.addUserService = addUserService;
        this.updateUserStatusService = updateUserStatusService;
    }
    @Post("/register")
    public HttpResponse<ApiResponse<UserReponseInfo>> registerUser(@Body CreateUserDto userDto) {
        ApiResponse registerUser = addUserService.registerUser(userDto);
        return HttpResponse.ok().body(registerUser);
    }

    @Put("/status")
    public HttpResponse<ApiResponse<String>> activateUser(@Body UserStatusDto userStatus) {
        ApiResponse<String> userStatusUpdated = updateUserStatusService.updateUserStatus(userStatus);
        return HttpResponse.ok().body(userStatusUpdated);
    }
    @Post("/loadBalance")
    public String loadBalance(@Body CashDto cashDto)
    {
       String message =  cashFlowSevice.loadBalance(cashDto);
       return  message;
    }
}
