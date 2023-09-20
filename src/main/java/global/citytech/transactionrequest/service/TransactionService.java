package global.citytech.transactionrequest.service;

import global.citytech.transactionrequest.service.adapter.TransactionDto;
import global.citytech.user.service.adaptor.ApiResponse;

public interface TransactionService {


    ApiResponse<?> requestMoney( TransactionDto transactionDto);
}
