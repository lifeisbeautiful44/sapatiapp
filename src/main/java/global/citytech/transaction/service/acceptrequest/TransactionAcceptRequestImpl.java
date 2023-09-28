package global.citytech.transaction.service.acceptrequest;

import global.citytech.cashflow.service.cashflow.CashFlowSevice;
import global.citytech.cashflow.service.balancevalidation.CheckBalanceService;
import global.citytech.common.BlackList;
import global.citytech.common.exception.CustomResponseException;
import global.citytech.transactionhistory.service.TransactionHistoryService;
import global.citytech.transaction.repository.TransacitionRepository;
import global.citytech.transaction.repository.Transaction;
import global.citytech.transaction.service.adapter.TransactionAcceptDto;
import global.citytech.user.repository.User;
import global.citytech.user.repository.UserRepository;
import global.citytech.common.apiresponse.ApiResponse;
import jakarta.inject.Inject;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

public class TransactionAcceptRequestImpl implements TransactionAcceptRequest {
    @Inject
    private UserRepository userRepository;
    @Inject
    private TransacitionRepository transacitonRepository;
    @Inject
    private CashFlowSevice cashFlowSevice;

    @Inject
    private CheckBalanceService checkBalanceService;
    @Inject
    private TransactionHistoryService transactionHistoryService;

    @Inject
    private BlackList blackList;


    @Override
    public ApiResponse<TransactionAcceptResponse> acceptTransactionRequest(TransactionAcceptDto acceptTransaction) {

        checkIfUserIsBlackListed(acceptTransaction);

        validateAcceptTransactionRequest(acceptTransaction);
        //for lender
        User validateLender = validateLender(acceptTransaction);
        //for borrower
        User validateBorrower = validateBorrower(acceptTransaction);

        //check the request of borrower for the particular lender and the status must be pending
        if (transacitonRepository.findByLenderIdAndBorrowerId(validateLender.getId(), validateBorrower.getId()).isPresent()) {
            Transaction pendingTransaction = transacitonRepository.findByLenderIdAndBorrowerIdAndStatus(validateLender.getId(), validateBorrower.getId(), "PENDING")
                    .orElseThrow(
                            () -> {
                                throw new CustomResponseException(400, "bad request", "No Pending transaction to  accept the request.");
                            }
                    );
            //if the lender has sufficient amount or not
            checkBalanceService.checkLenderBalance(pendingTransaction, pendingTransaction.getAmount());
            pendingTransaction.setStatus(acceptTransaction.getStatus());
            pendingTransaction.setInterestRate(acceptTransaction.getInterestRate());
            pendingTransaction.setPaymentAcceptedDate(LocalDateTime.now());

            Transaction acceptedTransaction = transacitonRepository.update(pendingTransaction);

            //redirecting to the CashInfo service
            cashFlowSevice.updateCashTransactionAccepted(acceptedTransaction);
            // Updating the transaction history
            transactionHistoryService.updateTransactionAccepted(acceptedTransaction);

            TransactionAcceptResponse transactionAcceptResponse = new TransactionAcceptResponse();
            transactionAcceptResponse.setBorrowerUserName(validateBorrower.getUserName());
            transactionAcceptResponse.setLenderUserName(validateLender.getUserName());
            transactionAcceptResponse.setStatus(pendingTransaction.getStatus());
            OffsetDateTime offSetRequestDate = OffsetDateTime.parse(pendingTransaction.getRequestDate());
            transactionAcceptResponse.setRequestedDate(offSetRequestDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm a")));
            transactionAcceptResponse.setInterestRate(pendingTransaction.getInterestRate());
            OffsetDateTime offSetAccepetedPaymentDate = OffsetDateTime.parse(pendingTransaction.getRequestDate());
            transactionAcceptResponse.setPaymentAcceptedDate(offSetAccepetedPaymentDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm a")));
            return new ApiResponse<>(200, "Money request Accepted", transactionAcceptResponse);

        } else {
            throw new CustomResponseException(400, "bad request", "No Request has made to  accept the transaction.");

        }
    }

    private void checkIfUserIsBlackListed(TransactionAcceptDto acceptTransaction) {
        String isUserBlacklisted =  acceptTransaction.getBorrowerUserName();
        Optional<User> user =  userRepository.findByUserName(isUserBlacklisted);
        if(user.isPresent())
        {
            blackList.isUserBlacklisted(user.get().getId());
        }
    }

    private void validateAcceptTransactionRequest(TransactionAcceptDto acceptTransactionRequest) {
        System.out.println("acceptTransactionRequest.getInterestRate() = " + acceptTransactionRequest.getInterestRate());

    if(acceptTransactionRequest.getLenderUserName().isEmpty() || acceptTransactionRequest.getBorrowerUserName().isEmpty() || acceptTransactionRequest.getInterestRate().isNaN())
    {
        throw new CustomResponseException(400, "bad request", "All the fields are mandatory.");
    }
    if(acceptTransactionRequest.getInterestRate() < 0)
    {
        throw new CustomResponseException(400, "bad request", "Interest Rate should be greater than Zero.");
    }
    if( !(acceptTransactionRequest.getStatus().equals("ACCEPTED") ) && !(acceptTransactionRequest.getStatus().equals("REJECTED")))
    {
        throw new CustomResponseException(400, "bad request", "Provide two option accept or reject");
    }
    }
    private User validateLender(TransactionAcceptDto transactionAcceptDto) {
        String lenderUserName = transactionAcceptDto.getLenderUserName();

        User userExist = userRepository.findByUserName(lenderUserName).orElseThrow(
                () -> {
                    throw new CustomResponseException(400, "bad request", "No user found.");

                }
        );
        if (userExist.getStatus() == false) {
            throw new CustomResponseException(400, "bad request", userExist.getFirstName() + " is not verified too make the money request");
        }
        return userRepository.findByUserNameAndUserType(lenderUserName, "LENDER").orElseThrow(
                () -> {
                    throw new CustomResponseException(400, "bad request", lenderUserName + " doesn't exist of type: LENDER");
                }
        );
    }
    private User validateBorrower(TransactionAcceptDto transactionAcceptDto) {
        String borrowerUserName = transactionAcceptDto.getBorrowerUserName();

        User userExist = userRepository.findByUserName(borrowerUserName).orElseThrow(
                () -> {
                    throw new CustomResponseException(400, "bad request", "No user found");
                }
        );

        if (userExist.getStatus() ==false ) {
            throw new CustomResponseException(400, "bad request", userExist.getFirstName() + " is not verified too make the money request");
        }

        return userRepository.findByUserNameAndUserType(borrowerUserName, "BORROWER").orElseThrow(
                () -> {
                    throw new CustomResponseException(400, "bad request", borrowerUserName + " doesn't exist of type: BORROWER");
                }
        );

    }

}

