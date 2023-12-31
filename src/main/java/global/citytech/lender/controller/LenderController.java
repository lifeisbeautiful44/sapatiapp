package global.citytech.lender.controller;


import global.citytech.transaction.service.acceptrequest.TransactionAcceptRequest;
import global.citytech.transaction.service.acceptrequest.TransactionAcceptResponse;
import global.citytech.transaction.service.adapter.TransactionAcceptDto;
import global.citytech.transactionhistory.service.transactionhistorylist.TransactionHistoryListService;
import global.citytech.common.apiresponse.ApiResponse;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.annotation.Body;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Put;
import jakarta.inject.Inject;

@Controller("/api/v1/")
public class LenderController {
    TransactionAcceptRequest transactionAcceptRequest;
    TransactionHistoryListService transactionHistoryList;
    @Inject
    public LenderController(TransactionAcceptRequest transactionAcceptRequest, TransactionHistoryListService transactionHistoryList) {
        this.transactionAcceptRequest = transactionAcceptRequest;
        this.transactionHistoryList = transactionHistoryList;
    }

    @Put("/accept-money-request")
    public HttpResponse<ApiResponse<TransactionAcceptResponse>> acceptTransactionRequest(@Body  TransactionAcceptDto acceptDto) {
            ApiResponse acceptedTransactionRequest = transactionAcceptRequest.acceptTransactionRequest(acceptDto);
            return HttpResponse.ok().body(acceptedTransactionRequest);
    }

//    @Post("/list")
//    public HttpResponse<ApiResponse> getList(@Body TransactionHistoryDto transactionDto)
//    {
//        ApiResponse  response =   transactionHistoryList.findAllTransactionHistory(transactionDto);
//        return HttpResponse.ok().body(response);
//    }

}
