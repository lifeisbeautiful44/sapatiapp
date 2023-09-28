package global.citytech.transaction.service.acceptrequest;

import global.citytech.transaction.service.adapter.TransactionAcceptDto;
import global.citytech.common.apiresponse.ApiResponse;

public interface TransactionAcceptRequest {
    ApiResponse<TransactionAcceptResponse> acceptTransactionRequest(TransactionAcceptDto acceptTransaction);
}
