package global.citytech.transactionrequest.service;

import global.citytech.cashflow.service.CashFlowSevice;
import global.citytech.transactionrequest.repository.TransacitonRepository;
import global.citytech.transactionrequest.repository.Transaction;
import global.citytech.transactionrequest.service.adapter.TransactionAcceptDto;
import global.citytech.user.repository.User;
import global.citytech.user.repository.UserRepository;
import global.citytech.user.service.adaptor.ApiResponse;
import jakarta.inject.Inject;

import javax.swing.*;

public class TransactionAcceptRequestImpl implements TransactionAcceptRequest {

    @Inject
    private UserRepository userRepository;

    @Inject
    private TransacitonRepository transacitonRepository;

    @Inject
    private CashFlowSevice cashFlowSevice;


    @Override
    public ApiResponse acceptTransactionRequest(TransactionAcceptDto acceptTransaction) {

        String lenderUserName = acceptTransaction.getLenderUserName();
        String borrowerUserName = acceptTransaction.getBorroweUserName();

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
            cashFlowSevice.isSufficientBalance(pendingTransaction.getBorrowerId(),pendingTransaction.getAmount());
            pendingTransaction.setStatus("ACCEPTED");


            pendingTransaction.setInterestRate(simpleIntresetRate(pendingTransaction) + pendingTransaction.getAmount());
           Transaction acceptedTransaction =  transacitonRepository.update(pendingTransaction);
           //redirecting to the CashInfo service
            cashFlowSevice.updateCashTransactionAccepted(acceptedTransaction);

            return new ApiResponse<>(200, "Money request Accepeted",pendingTransaction);

        }else {
            throw new IllegalArgumentException("No Request has made to  "+ validateLender.getUserName() + "by "+ validateBorrower.getUserName());
        }

    }

    private User validateLender(TransactionAcceptDto transactionAcceptDto) {
        String lenderUserName = transactionAcceptDto.getLenderUserName();

        User userExist = userRepository.findByUserName(lenderUserName).orElseThrow(
                () -> {throw new IllegalArgumentException("No user found");}
        );

        System.out.println(userExist);
        System.out.println(lenderUserName);
        System.out.println(userExist.getId());
        System.out.println(userExist.getStatus());
        if( userExist.getStatus() == false)
        {
            throw new IllegalArgumentException(userExist.getFirstName() + " is not verified too make the money request");
        }
        return userRepository.findByUserNameAndUserType(lenderUserName, "LENDER").orElseThrow(
                () -> {
                    throw new IllegalArgumentException(lenderUserName + " doesnot exist of type: LENDER");
                }
        );
    }

    private User validateBorrower(TransactionAcceptDto transactionAcceptDto) {
        String borrowerUserName = transactionAcceptDto.getBorroweUserName();

        User userExist = userRepository.findByUserName(borrowerUserName).orElseThrow(
                () -> {throw new IllegalArgumentException("No user found");}
        );

        if( userExist.getStatus() == false)
        {
            throw new IllegalArgumentException(userExist.getFirstName() + " is not verified too make the money request");
        }

        return userRepository.findByUserNameAndUserType(borrowerUserName, "BORROWER").orElseThrow(
                () -> {
                    throw new IllegalArgumentException(borrowerUserName + " doesnot exist of type: BORROWER");
                }
        );

    }

    private double simpleIntresetRate(Transaction transaction)
    {
      double amount =   transaction.getAmount();
       Double time  =    (double)transaction.getWillPay()/365;
        if(amount <= 5000)
      {
          return  (amount * time * 5) /100;
      } else if (amount <= 25000) {
          return  (amount * time * 10) /100;
      }else if (amount <= 50000){
          return (amount * time * 12) /100;
      }else {
          return  amount;
      }
    }
}
