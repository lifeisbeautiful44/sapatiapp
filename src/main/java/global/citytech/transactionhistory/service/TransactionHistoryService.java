package global.citytech.transactionhistory.service;

import global.citytech.transaction.repository.Transaction;

public interface TransactionHistoryService {
    void create(Transaction transaction);
    void updateTransactionAccepted(Transaction transaction);
    void updateTransactionPayment(Transaction transaction);
}
