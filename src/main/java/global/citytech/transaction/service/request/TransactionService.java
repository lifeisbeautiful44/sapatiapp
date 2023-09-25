package global.citytech.transaction.service.request;

import global.citytech.transaction.service.adapter.TransactionDto;
import global.citytech.user.service.adaptor.ApiResponse;

public interface TransactionService {


    ApiResponse<?> requestMoney( TransactionDto transactionDto);

}
