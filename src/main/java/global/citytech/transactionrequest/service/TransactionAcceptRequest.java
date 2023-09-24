package global.citytech.transactionrequest.service;

import global.citytech.transactionrequest.service.adapter.TransactionAcceptDto;
import global.citytech.user.service.adaptor.ApiResponse;

public interface TransactionAcceptRequest {
    ApiResponse<?> acceptTransactionRequest(TransactionAcceptDto acceptTransaction);
}
