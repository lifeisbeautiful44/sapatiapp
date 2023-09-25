package global.citytech.transaction.service.acceptrequest;

import global.citytech.cashflow.service.cashflow.CashFlowSevice;
import global.citytech.cashflow.service.balancevalidation.CheckBalanceService;
import global.citytech.transactionhistory.service.TransactionHistoryService;
import global.citytech.transaction.repository.TransacitonRepository;
import global.citytech.transaction.repository.Transaction;
import global.citytech.transaction.service.adapter.TransactionAcceptDto;
import global.citytech.user.repository.User;
import global.citytech.user.repository.UserRepository;
import global.citytech.user.service.adaptor.ApiResponse;
import jakarta.inject.Inject;

import java.time.LocalDateTime;

public class TransactionAcceptRequestImpl implements TransactionAcceptRequest {
    @Inject
    private UserRepository userRepository;
    @Inject
    private TransacitonRepository transacitonRepository;
    @Inject
    private CashFlowSevice cashFlowSevice;

    @Inject
    private CheckBalanceService checkBalanceService;
    @Inject
    private TransactionHistoryService transactionHistoryService;


    @Override
    public ApiResponse acceptTransactionRequest(TransactionAcceptDto acceptTransaction) {

        //for lender
        User validateLender = validateLender(acceptTransaction);
        //for borrower
        User validateBorrower = validateBorrower(acceptTransaction);

        //check the request of borrower for the particular lender and the status must be pending
        if (transacitonRepository.findByLenderIdAndBorrowerId(validateLender.getId(), validateBorrower.getId()).isPresent()) {
            Transaction pendingTransaction = transacitonRepository.findByLenderIdAndBorrowerIdAndStatus(validateLender.getId(), validateBorrower.getId(), "PENDING")
                    .orElseThrow(
                            () -> {
                                throw new IllegalArgumentException("No Pending transaction to  accept the request");
                            }
                    );
            //if the lender has sufficient amount or not
            checkBalanceService.checkLenderBalance(pendingTransaction, pendingTransaction.getAmount());
            pendingTransaction.setStatus("ACCEPTED");
            pendingTransaction.setInterestRate(acceptTransaction.getInterestRate());
            pendingTransaction.setPaymentAcceptedDate(LocalDateTime.now());

            Transaction acceptedTransaction = transacitonRepository.update(pendingTransaction);

            //redirecting to the CashInfo service
            cashFlowSevice.updateCashTransactionAccepted(acceptedTransaction);
            // Updating the transaction history
            transactionHistoryService.updateTransactionAccepted(acceptedTransaction);

            return new ApiResponse<>(200, "Money request Accepted", pendingTransaction);

        } else {
            throw new IllegalArgumentException("No Request has made to  accept the transaction" );
        }
    }

    private User validateLender(TransactionAcceptDto transactionAcceptDto) {
        String lenderUserName = transactionAcceptDto.getLenderUserName();

        User userExist = userRepository.findByUserName(lenderUserName).orElseThrow(
                () -> {
                    throw new IllegalArgumentException("No user found");
                }
        );

        if (userExist.getStatus() == false) {
            throw new IllegalArgumentException(userExist.getFirstName() + " is not verified too make the money request");
        }
        return userRepository.findByUserNameAndUserType(lenderUserName, "LENDER").orElseThrow(
                () -> {
                    throw new IllegalArgumentException(lenderUserName + " doesn't exist of type: LENDER");
                }
        );
    }

    private User validateBorrower(TransactionAcceptDto transactionAcceptDto) {
        String borrowerUserName = transactionAcceptDto.getBorrowerUserName();

        User userExist = userRepository.findByUserName(borrowerUserName).orElseThrow(
                () -> {
                    throw new IllegalArgumentException("No user found");
                }
        );

        if (userExist.getStatus() ==false ) {
            throw new IllegalArgumentException(userExist.getFirstName() + " is not verified too make the money request");
        }

        return userRepository.findByUserNameAndUserType(borrowerUserName, "BORROWER").orElseThrow(
                () -> {
                    throw new IllegalArgumentException(borrowerUserName + " doesn't exist of type: BORROWER");
                }
        );

    }

}

