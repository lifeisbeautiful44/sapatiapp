package global.citytech.transactionrequest.service;

import global.citytech.cashflow.service.CashFlowSevice;
import global.citytech.transactionhistory.repository.TransactionHistory;
import global.citytech.transactionhistory.repository.TransactionHistoryRepository;
import global.citytech.transactionhistory.service.TransactionHistoryServiceImpl;
import global.citytech.transactionrequest.repository.TransacitonRepository;
import global.citytech.transactionrequest.repository.Transaction;
import global.citytech.transactionrequest.service.adapter.TransactionDto;
import global.citytech.transactionrequest.service.adapter.TransactionPaymentDto;
import global.citytech.user.repository.User;
import global.citytech.user.repository.UserRepository;
import jakarta.inject.Inject;

public class TransactionPaymentServiceImpl implements TransactionPaymentService {

    @Inject
    private CashFlowSevice cashFlowSevice;

    @Inject
    private UserRepository userRepository;


    @Inject
    private TransactionHistoryServiceImpl transactionHistoryService;




    @Inject
    private TransacitonRepository transacitonRepository;
    public void makePayment(TransactionPaymentDto transactionPaymentDto)
    {

        User validBorrower =  validateBorrower(transactionPaymentDto);
        User validLender = validateLender(transactionPaymentDto);
        System.out.println(validBorrower);
        System.out.println(validLender);
        Transaction transaction = transacitonRepository.findByLenderIdAndBorrowerIdWhereStatus(validLender.getId(), validBorrower.getId(),"ACCEPTED").get();
        System.out.println(transaction);
        System.out.println("hello");
        System.out.println(transactionPaymentDto.getAmount());
        //check If the borrower has been amount to paid .
        cashFlowSevice.isSufficientBalance(validBorrower.getId(),transactionPaymentDto.getAmount());


        //updatecashflow or make payment

        cashFlowSevice.updatePaymentSuccessfull(validBorrower.getId(), validLender.getId(), transactionPaymentDto.getAmount());
        System.out.println("Payment is successfully made");


        //update transaction history from Unpaid to paid
        transactionHistoryService.updateTransactionPayment(transaction);

        System.out.println("Transaction has been succefully updated from paid to unpaid");




    }

    private User validateLender(TransactionPaymentDto transactionPaymentDto) {

        String lenderUserName = transactionPaymentDto.getLenderUserName();
        User userExist =   userRepository.findByUserName(lenderUserName).orElseThrow(
                () -> {throw new IllegalArgumentException("No user found");}
        );
        if( userExist.getStatus() == false)
        {
            throw new IllegalArgumentException(userExist.getFirstName() + "  -> type [LENDER] " + " is not verified too make the money request");
        }

        return     userRepository.findByUserNameAndUserType(lenderUserName,"LENDER").orElseThrow(
                () -> { throw new IllegalArgumentException(lenderUserName +" doesnot exist as  LENDER");}
        );
    }

    private User validateBorrower(TransactionPaymentDto transactionPaymentDto) {
        String borrowerUserName =  transactionPaymentDto.getBorrowerUserName();

        User userExist =   userRepository.findByUserName(borrowerUserName).orElseThrow(
                () -> {throw new IllegalArgumentException("No user found");}
        );

        if( userExist.getStatus() == false)
        {
            throw new IllegalArgumentException(userExist.getFirstName() +  " ->  type [BORROWER] " + " is not verified too make the money request");
        }
        return   userRepository.findByUserNameAndUserType(borrowerUserName,"BORROWER").orElseThrow(
                () -> { throw new IllegalArgumentException(borrowerUserName +" doesnot exist as BORROWER" );}
        );

    }
}
