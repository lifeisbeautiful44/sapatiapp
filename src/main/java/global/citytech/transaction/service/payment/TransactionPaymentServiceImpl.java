package global.citytech.transaction.service.payment;

import global.citytech.cashflow.service.balancevalidation.CheckBalanceService;
import global.citytech.cashflow.service.cashflow.CashFlowSevice;
import global.citytech.common.exception.CustomResponseException;
import global.citytech.transaction.repository.TransacitionRepository;
import global.citytech.transaction.repository.Transaction;
import global.citytech.transaction.service.adapter.TransactionPaymentDto;
import global.citytech.transactionhistory.repository.TransactionHistory;
import global.citytech.transactionhistory.repository.TransactionHistoryRepository;
import global.citytech.transactionhistory.service.TransactionHistoryService;
import global.citytech.user.repository.User;
import global.citytech.user.repository.UserRepository;
import global.citytech.common.apiresponse.ApiResponse;
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
    private TransacitionRepository transacitionRepository;

    public ApiResponse<TransactionPaymentBackResponse> makePayment(TransactionPaymentDto transactionPaymentDto) {

        validateTransactionPaymentRequest(transactionPaymentDto);

        User validBorrower = validateBorrower(transactionPaymentDto);
        User validLender = validateLender(transactionPaymentDto);

        TransactionHistory transactionHistory = transactionHistoryRepository.findByLenderIdAndBorrowerIdWherePaymentStatus(validLender.getId(), validBorrower.getId(), "UNPAID").orElseThrow(
                () -> {

                    throw new CustomResponseException(400, "bad request", "No previous UNPAID transaction.");
                }
        );
        Transaction transaction = transacitionRepository.findById(transactionHistory.getTransactionId()).get();

        /*check If the borrower has been amount to paid .*/
        checkBalanceService.checkBorrowerBalance(transaction, transactionPaymentDto.getAmount());

        //check the balance to be paid ,and balance given
        checkBalanceService.checkAmountPaid(interestAmount(transaction), transactionPaymentDto.getAmount());

        //update cashflow or make payment
        cashFlowSevice.updatePaymentSuccessfull(validBorrower.getId(), validLender.getId(), interestAmount(transaction));

        //update transaction history from Unpaid to paid
        transactionHistoryService.updateTransactionPayment(transaction);

        //update the transaction with amount that has been paid.
        transaction.setAmountWithInterest(interestAmount(transaction));
        Transaction successfulTransaction = transacitionRepository.update(transaction);

        // making response
        TransactionPaymentBackResponse paymentBackResponse = new TransactionPaymentBackResponse();
        paymentBackResponse.setBorrowerName(validBorrower.getUserName());
        paymentBackResponse.setLenderName(validLender.getUserName());
        paymentBackResponse.setAmount(successfulTransaction.getAmount());
        paymentBackResponse.setPaidBackAmmount(successfulTransaction.getAmountWithInterest());

        return new ApiResponse<>(200, "success", paymentBackResponse);

    }

    private void validateTransactionPaymentRequest(TransactionPaymentDto transactionPaymentDto) {

        if (transactionPaymentDto.getBorrowerUserName().isEmpty() && transactionPaymentDto.getLenderUserName().isEmpty()) {

            throw new CustomResponseException(400, "bad request", "UserName should not be empty.");
        }
        if (transactionPaymentDto.getAmount() < 10) {
            throw new CustomResponseException(400, "bad request", "Amount should be greater than Rs 10.");
        }
    }

    private User validateLender(TransactionPaymentDto transactionPaymentDto) {

        String lenderUserName = transactionPaymentDto.getLenderUserName();
        User userExist = userRepository.findByUserName(lenderUserName).orElseThrow(
                () -> {
                    throw new CustomResponseException(400, "bad request", "No user found");
                }
        );
        if (!userExist.getStatus()) {
            throw new CustomResponseException(400, "bad request", userExist.getFirstName() + " -> type [LENDER]  is not verified too make the money request");
        }

        return userRepository.findByUserNameAndUserType(lenderUserName, "LENDER").orElseThrow(
                () -> {
                    throw new CustomResponseException(400, "bad request", lenderUserName + " doesn't exist as  LENDER");
                }
        );
    }

    private User validateBorrower(TransactionPaymentDto transactionPaymentDto) {
        String borrowerUserName = transactionPaymentDto.getBorrowerUserName();

        User userExist = userRepository.findByUserName(borrowerUserName).orElseThrow(
                () -> {
                    throw new CustomResponseException(400, "bad request", "No user found.");
                }
        );


        if (!userExist.getStatus()) {
            throw new IllegalArgumentException(userExist.getFirstName() + " ->  type [BORROWER] " + " is not verified too make the money request");
        }
        return userRepository.findByUserNameAndUserType(borrowerUserName, "BORROWER").orElseThrow(
                () -> {
                    throw new CustomResponseException(400, "bad request", borrowerUserName + " doesn't exist as BORROWER");
                }


        );

    }
    private Double interestAmount(Transaction transaction) {
        LocalDateTime paymentDate = transaction.getPaymentAcceptedDate();
        LocalDateTime todayDate = LocalDateTime.now();

        long daysPassed = ChronoUnit.DAYS.between(paymentDate, todayDate);

        double time = (daysPassed == 0) ? 1.0 / 365.0 : daysPassed / 365.0;

        double principal = transaction.getAmount();
        double rate = transaction.getInterestRate() / 100.0;

        double amountWithInterest = (principal * rate * time) / 100;

        return amountWithInterest + principal;

    }

}