package global.citytech.transactionrequest.service;

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
                                throw new IllegalArgumentException("No Pending transaction to to accept the request");
                            }
                    );
            pendingTransaction.setStatus("ACCEPTED");
            pendingTransaction.setInterestRate(simpleIntresetRate(pendingTransaction) + pendingTransaction.getAmount());
            transacitonRepository.update(pendingTransaction);
            return new ApiResponse<>(200, "Money request Accepeted",pendingTransaction);

        }else {
            throw new IllegalArgumentException("No Request has made by "+ validateLender.getUserName());
        }

    }

    private User validateLender(TransactionAcceptDto transactionAcceptDto) {
        String lenderUserName = transactionAcceptDto.getLenderUserName();

        return userRepository.findByUserNameAndUserType(lenderUserName, "LENDER").orElseThrow(
                () -> {
                    throw new IllegalArgumentException(lenderUserName + " doesnot exist of type: LENDER");
                }
        );
    }

    private User validateBorrower(TransactionAcceptDto transactionAcceptDto) {
        String borrowerUserName = transactionAcceptDto.getBorroweUserName();

        return userRepository.findByUserNameAndUserType(borrowerUserName, "BORROWER").orElseThrow(
                () -> {
                    throw new IllegalArgumentException(borrowerUserName + " doesnot exist of type: BORROWER");
                }
        );

    }

    private double simpleIntresetRate(Transaction transaction)
    {
      double amount =   transaction.getAmount();
        System.out.println(amount);
        System.out.println(transaction.getWillPay());
       Double time  =    (double)transaction.getWillPay()/365;
        System.out.println(time);


        if(amount <= 5000)
      {
          return  (amount * time * 5) /100;

      } else if (amount <= 25000) {
          System.out.println((amount * time * 0.05) /100);
          return  (amount * time * 10) /100;


      }else if (amount <= 50000){
          return (amount * time * 12) /100;
      }else {
          return  amount;
      }
    }


}
