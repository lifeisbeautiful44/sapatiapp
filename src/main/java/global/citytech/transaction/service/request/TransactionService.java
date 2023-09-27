package global.citytech.transaction.service.request;

import global.citytech.transaction.service.adapter.TransactionRequestDto;
import global.citytech.user.service.adaptor.ApiResponse;

public interface TransactionService {


    ApiResponse<TransactionResponse> requestMoney( TransactionRequestDto transactionDto);

}
