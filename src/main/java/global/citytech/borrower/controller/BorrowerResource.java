package global.citytech.borrower.controller;


import global.citytech.borrower.service.listlender.BorrowerService;
import global.citytech.borrower.service.listlender.LenderResponse;
import global.citytech.transaction.service.payment.TransactionPaymentService;
import global.citytech.transaction.service.request.TransactionService;
import global.citytech.transaction.service.adapter.TransactionDto;
import global.citytech.transaction.service.adapter.TransactionPaymentDto;
import global.citytech.transactionhistory.service.transactionhistorylist.TransactionHistoryListService;
import global.citytech.transactionhistory.service.transactionhistorylist.TransactionHistoryDto;
import global.citytech.transactionhistory.service.transactionhistorylist.TransactionHistoryResponse;
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
    @Inject
    TransactionPaymentService transactionPaymentService;

    @Get("/lender-list")
    public HttpResponse<ApiResponse<List<LenderResponse>>> getLenderList() {
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
    @Put("/return/money")
    public  void returnPayment(@Body  TransactionPaymentDto transactionPaymentDto)
    {
        transactionPaymentService.makePayment(transactionPaymentDto);
    }


}

