package global.citytech.borrower.service;

import global.citytech.cashflow.service.CashFlowSevice;
import global.citytech.transactionhistory.repository.TransactionHistory;
import global.citytech.transactionrequest.repository.Transaction;
import jakarta.inject.Inject;

public class DoPayment {

    @Inject
    CashFlowSevice cashFlowSevice;

    @Inject
    TransactionHistory transactionHistory;


    public void makePayment(Transaction transaction)
    {

    }

}
