package global.citytech.cashflow.service.balancevalidation;

import global.citytech.transaction.repository.Transaction;

public interface CheckBalanceService {


    Boolean checkLenderBalance(Transaction transaction, double checkBalance);
    Boolean checkBorrowerBalance(Transaction transaction, double checkBalance);

    void checkAmountPaid(Double amountToReturn , Double amountProivded);
}
