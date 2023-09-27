package global.citytech.borrower.controller;


import global.citytech.borrower.service.listlender.BorrowerService;
import global.citytech.borrower.service.listlender.LenderResponse;
import global.citytech.transaction.service.payment.TransactionPaymentBackResponse;
import global.citytech.transaction.service.payment.TransactionPaymentService;
import global.citytech.transaction.service.request.TransactionResponse;
import global.citytech.transaction.service.request.TransactionService;
import global.citytech.transaction.service.adapter.TransactionRequestDto;
import global.citytech.transaction.service.adapter.TransactionPaymentDto;
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
    public HttpResponse<ApiResponse<TransactionResponse>> requestMoney(@Body TransactionRequestDto transactionDto) {
            ApiResponse<TransactionResponse> requestMade = transactionService.requestMoney(transactionDto);
            return HttpResponse.ok().body(requestMade);
    }
    @Put("/return/money")
    public  HttpResponse<ApiResponse<TransactionPaymentBackResponse>> returnPayment(@Body  TransactionPaymentDto transactionPaymentDto)
    {
      ApiResponse<TransactionPaymentBackResponse> paymentBackResponse =  transactionPaymentService.makePayment(transactionPaymentDto);
      return HttpResponse.ok().body(paymentBackResponse);
    }


}

