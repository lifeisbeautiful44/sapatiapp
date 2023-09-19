package global.citytech.user.controller;


import global.citytech.user.service.adaptor.ApiResponse;
import global.citytech.user.service.adaptor.dto.CreateUserDto;
import global.citytech.user.service.adduser.AddUserService;
import global.citytech.user.service.adduser.UserReponseInfo;
import global.citytech.user.service.updateuserstatus.UpdateUserStatusService;
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

    @Put("/status/{userId}")
    public HttpResponse<ApiResponse<String>> activateUser(@PathVariable long userId) {
        ApiResponse<String> userStatusUpdated = updateUserStatusService.updateUserStatus(userId);

        return HttpResponse.ok().body(userStatusUpdated);

    }


}
