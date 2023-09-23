package global.citytech.transactionrequest.service;

import global.citytech.transactionrequest.service.adapter.TransactionPaymentDto;

public interface TransactionPaymentService {
    void makePayment(TransactionPaymentDto transactionPaymentDto);
}
