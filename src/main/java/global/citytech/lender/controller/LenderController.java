package global.citytech.lender.controller;


import global.citytech.transactionrequest.service.TransactionAcceptRequest;
import global.citytech.transactionrequest.service.adapter.TransactionAcceptDto;
import global.citytech.user.service.adaptor.ApiResponse;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.annotation.Body;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Put;
import jakarta.inject.Inject;

@Controller("/api/v1/")
public class LenderController {
    @Inject
    TransactionAcceptRequest transactionAcceptRequest;
    @Put("/accept-money-request")
    public HttpResponse<ApiResponse> acceptTransactionRequest(@Body  TransactionAcceptDto acceptDto) {
        try {
            ApiResponse apiResponse = transactionAcceptRequest.acceptTransactionRequest(acceptDto);
            return HttpResponse.ok().body(apiResponse);
        } catch (IllegalArgumentException e) {
            return HttpResponse.badRequest().body(new ApiResponse<>(400, "failed", e.getMessage()));
        }

    }
}
