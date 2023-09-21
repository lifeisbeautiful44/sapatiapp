package global.citytech.cashflow.service;

import global.citytech.transactionrequest.repository.Transaction;
import global.citytech.user.repository.User;

public interface CashFlowSevice {
    void updateCashTransactionAccepted(Transaction transaction);
    String loadBalance(CashDto cashDto);
    // every time a new user is created, saves the user , with inirtial values .
    void saveNewUserCashInformation(User verifiedUser);
     Boolean isSufficientBalance(Long userId, double checkBalance);
}
