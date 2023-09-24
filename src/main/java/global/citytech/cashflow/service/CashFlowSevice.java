package global.citytech.cashflow.service;

import global.citytech.transactionrequest.repository.Transaction;
import global.citytech.user.repository.User;

public interface CashFlowSevice {

    void updateCashTransactionAccepted(Transaction transaction);
    String loadBalance(CashDto cashDto);
    // every time a new user is created, saves the user , with inirtial values .
    void saveNewUserCashInformation(User verifiedUser);
    Boolean isSufficientBalance(Transaction transaction, double checkBalance);

    Boolean checkLenderBalance(Transaction transaction, double checkBalance);

    Boolean checkBorrowerBalance(Transaction transaction, double checkBalance);
    void updatePaymentSuccessfull(Long borrowerId, Long lenerdID, Double intrestAmount);

    void checkAmountPaid(Double amountToReturn , Double amountProivded);
}
