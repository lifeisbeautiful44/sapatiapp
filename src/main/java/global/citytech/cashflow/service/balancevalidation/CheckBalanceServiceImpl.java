package global.citytech.cashflow.service.balancevalidation;

import global.citytech.cashflow.repository.CashFlow;
import global.citytech.cashflow.repository.CashFlowRepository;
import global.citytech.transaction.repository.Transaction;
import global.citytech.user.repository.UserRepository;
import jakarta.inject.Inject;

import java.util.Optional;

public class CheckBalanceServiceImpl implements CheckBalanceService{

    @Inject
    private CashFlowRepository cashFlowRepository;
    @Inject
    private UserRepository userRepository;
    @Override
    public Boolean checkLenderBalance(Transaction transaction, double checkBalance) {

        Optional<CashFlow> userBalanceCheck = cashFlowRepository.findByLenderId(transaction.getLenderId());

        if (userBalanceCheck.get().getCashAmount() >= checkBalance) {
            return true;
        } else {
            throw new IllegalArgumentException("Not sufficient balance to make the request");
        }

    }
    @Override
    public Boolean checkBorrowerBalance(Transaction transaction, double checkBalance) {
        Optional<CashFlow> userBalanceCheck = cashFlowRepository.findByBorrowerId(transaction.getBorrowerId());
        if (userBalanceCheck.get().getCashAmount() >= checkBalance) {
            return true;
        } else {
            throw new IllegalArgumentException("Not sufficient balance to make the request");
        }
    }

    public void checkAmountPaid(Double amountToReturn, Double amountProvided) {
        System.out.println(amountToReturn + " " + amountProvided);
        if (amountProvided < amountToReturn) {
            throw new IllegalArgumentException("Return amount  is : " + amountToReturn + "  Provided amount: " + amountProvided);
        }

    }
}