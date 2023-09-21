package global.citytech.borrower.controller;


import global.citytech.borrower.service.listlender.BorrowerService;
import global.citytech.transactionrequest.service.TransactionService;
import global.citytech.transactionrequest.service.adapter.TransactionDto;
import global.citytech.user.repository.User;
import global.citytech.user.service.adaptor.ApiResponse;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.annotation.Body;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.Post;
import jakarta.inject.Inject;

import java.util.List;

@Controller("/api/v1")
public class BorrowerResource {
    @Inject
    BorrowerService borrowerService;
    @Inject
    TransactionService transactionService;
    @Get("/lender-list")
    public HttpResponse<ApiResponse<List<User>>> getLenderList() {
        ApiResponse lenderList = borrowerService.getLenderList();
        return HttpResponse.ok().body(lenderList);
    }

    @Post("/request-money")
    public HttpResponse<ApiResponse<?>> requestMoney(@Body TransactionDto transactionDto) {
        try {
            ApiResponse requestMade = transactionService.requestMoney(transactionDto);
            return HttpResponse.ok().body(requestMade);
        } catch (IllegalArgumentException e) {
            return HttpResponse.badRequest().body(new ApiResponse<>(400, "Failed", e.getMessage()));
        }
    }
}

