package global.citytech.auth.controller;


import global.citytech.auth.service.LoginService;
import global.citytech.auth.service.adaptor.dto.LoginDto;
import global.citytech.common.apiresponse.ApiResponse;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.annotation.Body;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Post;
import jakarta.inject.Inject;

@Controller("/api/v1")
public class LoginResource {

    @Inject
    private final LoginService loginService;

    public LoginResource(LoginService loginService) {
        this.loginService = loginService;
    }
    @Post("/login")
    public HttpResponse<ApiResponse> login(@Body LoginDto loginRequest) {
        ApiResponse apiResponse = loginService.login(loginRequest);
        return HttpResponse.ok().body(apiResponse);
    }
}
