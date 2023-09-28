package global.citytech.transactionhistory.service.transactionhistorylist;

import global.citytech.common.apiresponse.ApiResponse;

import java.util.List;

public interface TransactionHistoryListService {

    ApiResponse<List<TransactionHistoryResponse>> findAllTransactionHistory(TransactionHistoryDto transactionHistory);

}
