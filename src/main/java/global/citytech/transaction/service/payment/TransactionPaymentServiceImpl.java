package global.citytech.transaction.service.payment;

import global.citytech.cashflow.service.cashflow.CashFlowSevice;
import global.citytech.cashflow.service.balancevalidation.CheckBalanceService;
import global.citytech.transactionhistory.repository.TransactionHistory;
import global.citytech.transactionhistory.repository.TransactionHistoryRepository;
import global.citytech.transactionhistory.service.TransactionHistoryService;
import global.citytech.transaction.repository.TransacitonRepository;
import global.citytech.transaction.repository.Transaction;
import global.citytech.transaction.service.adapter.TransactionPaymentDto;
import global.citytech.user.repository.User;
import global.citytech.user.repository.UserRepository;
import jakarta.inject.Inject;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

public class TransactionPaymentServiceImpl implements TransactionPaymentService {

    @Inject
    private CashFlowSevice cashFlowSevice;

    @Inject
    private CheckBalanceService checkBalanceService;
    @Inject
    private UserRepository userRepository;
    @Inject
    private TransactionHistoryRepository transactionHistoryRepository;
    @Inject
    private TransactionHistoryService transactionHistoryService;

    @Inject
    private TransacitonRepository transacitionRepository;
    public void makePayment(TransactionPaymentDto transactionPaymentDto)
    {

        User validBorrower =  validateBorrower(transactionPaymentDto);
        User validLender = validateLender(transactionPaymentDto);

        TransactionHistory transactionHistory = transactionHistoryRepository.findByLenderIdAndBorrowerIdWherePaymentStatus(validLender.getId(), validBorrower.getId(), "UNPAID").orElseThrow(
                () -> {throw  new IllegalArgumentException("No previous UNPAID transaction ");}
        );
        Transaction transaction = transacitionRepository.findById(transactionHistory.getTransactionId()).get();

        /*check If the borrower has been amount to paid .*/
        checkBalanceService.checkBorrowerBalance(transaction,transactionPaymentDto.getAmount());

        //check the balance to be paid ,and balance given
        checkBalanceService.checkAmountPaid(interestAmount(transaction),transactionPaymentDto.getAmount());

        //update cashflow or make payment
        cashFlowSevice.updatePaymentSuccessfull(validBorrower.getId(), validLender.getId(), interestAmount(transaction));

        //update transaction history from Unpaid to paid
        transactionHistoryService.updateTransactionPayment(transaction);

        //update the transaction with amount that has been paid.
        transaction.setAmountWithInterest(interestAmount(transaction));
        transacitionRepository.update(transaction);


    }

    private User validateLender(TransactionPaymentDto transactionPaymentDto) {

        String lenderUserName = transactionPaymentDto.getLenderUserName();
        User userExist =   userRepository.findByUserName(lenderUserName).orElseThrow(
                () -> {throw new IllegalArgumentException("No user found");}
        );
        if(!userExist.getStatus())
        {
            throw new IllegalArgumentException(userExist.getFirstName() + "  -> type [LENDER] " + " is not verified too make the money request");
        }

        return     userRepository.findByUserNameAndUserType(lenderUserName,"LENDER").orElseThrow(
                () -> { throw new IllegalArgumentException(lenderUserName +" doesn't exist as  LENDER");}
        );
    }

    private User validateBorrower(TransactionPaymentDto transactionPaymentDto) {
        String borrowerUserName =  transactionPaymentDto.getBorrowerUserName();

        User userExist =   userRepository.findByUserName(borrowerUserName).orElseThrow(
                () -> {throw new IllegalArgumentException("No user found");}
        );


        if(!userExist.getStatus())
        {
            throw new IllegalArgumentException(userExist.getFirstName() +  " ->  type [BORROWER] " + " is not verified too make the money request");
        }
        return   userRepository.findByUserNameAndUserType(borrowerUserName,"BORROWER").orElseThrow(
                () -> { throw new IllegalArgumentException(borrowerUserName +" doesn't exist as BORROWER" );}
        );

    }

    private Double interestAmount(Transaction transaction)
    {
        LocalDateTime paymentDate = transaction.getPaymentAcceptedDate();
        LocalDateTime todayDate = LocalDateTime.now();

        long daysPassed = ChronoUnit.DAYS.between(paymentDate, todayDate);

        double time = (daysPassed == 0) ? 1.0 / 365.0 : daysPassed / 365.0;

        double principal = transaction.getAmount();
        double rate = transaction.getInterestRate() / 100.0;

        double amountWithInterest = (principal * rate * time);

        return amountWithInterest + principal;

}

}