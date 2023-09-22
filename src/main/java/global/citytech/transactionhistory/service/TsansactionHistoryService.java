package global.citytech.transactionhistory.service;

import global.citytech.transactionrequest.repository.Transaction;

public interface TsansactionHistoryService {

    void create(Transaction transaction);

    void updateTransactionAccepted(Transaction transaction);
}
