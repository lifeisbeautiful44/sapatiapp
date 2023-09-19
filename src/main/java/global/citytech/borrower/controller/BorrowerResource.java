package global.citytech.borrower.controller;


import global.citytech.borrower.service.listlender.BorrowerService;
import global.citytech.transactionrequest.entity.Transaction;
import global.citytech.transactionrequest.service.TransactionService;
import global.citytech.transactionrequest.service.adapter.TransactionDto;
import global.citytech.user.repository.User;
import global.citytech.user.service.adaptor.ApiResponse;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.annotation.*;
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

    @Post("/request-money/{lenderId}/{borrowerId}")
    public HttpResponse<ApiResponse<?>> requestMoney(@PathVariable Long lenderId, @PathVariable  Long borrowerId, @Body TransactionDto transactionDto) {


        ApiResponse requestMade = transactionService.requestMoney(lenderId, borrowerId, transactionDto);

        return HttpResponse.ok().body(requestMade);


    }
}
