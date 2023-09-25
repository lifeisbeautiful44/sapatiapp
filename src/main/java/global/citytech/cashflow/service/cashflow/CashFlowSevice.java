package global.citytech.cashflow.service.cashflow;

import global.citytech.cashflow.service.CashDto;
import global.citytech.transaction.repository.Transaction;
import global.citytech.user.repository.User;

public interface CashFlowSevice {

    void updateCashTransactionAccepted(Transaction transaction);
    String loadBalance(CashDto cashDto);
    // every time a new user is created, saves the user , with inirtial values .
    void saveNewUserCashInformation(User verifiedUser);
    void updatePaymentSuccessfull(Long borrowerId, Long lenderId, Double intrestAmount);
  //  Boolean isSufficientBalance(Transaction transaction, double checkBalance);

//    Boolean checkLenderBalance(Transaction transaction, double checkBalance);
//    Boolean checkBorrowerBalance(Transaction transaction, double checkBalance);
//
//    void checkAmountPaid(Double amountToReturn , Double amountProivded);
}
