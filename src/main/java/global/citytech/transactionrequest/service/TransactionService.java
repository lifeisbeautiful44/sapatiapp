package global.citytech.transactionrequest.service;

import global.citytech.transactionrequest.entity.Transaction;
import global.citytech.transactionrequest.service.adapter.TransactionDto;
import global.citytech.user.service.adaptor.ApiResponse;

public interface TransactionService {


    ApiResponse<?> requestMoney(Long lenderId, Long borrowerId, TransactionDto transactionDto);
}
