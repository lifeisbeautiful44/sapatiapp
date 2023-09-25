package global.citytech.transaction.service.payment;

import global.citytech.transaction.service.adapter.TransactionPaymentDto;

public interface TransactionPaymentService {
    void makePayment(TransactionPaymentDto transactionPaymentDto);
}
