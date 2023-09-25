package global.citytech.transaction.service.acceptrequest;

import global.citytech.transaction.service.adapter.TransactionAcceptDto;
import global.citytech.user.service.adaptor.ApiResponse;

public interface TransactionAcceptRequest {
    ApiResponse<?> acceptTransactionRequest(TransactionAcceptDto acceptTransaction);
}
