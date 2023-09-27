package global.citytech.transaction.service.payment;

import global.citytech.transaction.service.adapter.TransactionPaymentDto;
import global.citytech.user.service.adaptor.ApiResponse;

public interface TransactionPaymentService {
    ApiResponse<TransactionPaymentBackResponse> makePayment(TransactionPaymentDto transactionPaymentDto);
}
