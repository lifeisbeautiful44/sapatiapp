package global.citytech.transaction.service.request;

import global.citytech.transactionhistory.repository.TransactionHistory;
import global.citytech.transactionhistory.repository.TransactionHistoryRepository;
import global.citytech.transactionhistory.service.TransactionHistoryService;
import global.citytech.transaction.repository.TransacitonRepository;
import global.citytech.transaction.repository.Transaction;
import global.citytech.transaction.service.adapter.TransactionDto;
import global.citytech.transaction.service.adapter.mapper.Mapper;
import global.citytech.user.repository.User;
import global.citytech.user.repository.UserRepository;
import global.citytech.user.service.adaptor.ApiResponse;
import jakarta.inject.Inject;

import java.util.List;
import java.util.Optional;

public class TransactionServiceImpl implements TransactionService {

    @Inject
    private TransacitonRepository transacitonRepository;
    @Inject
    private UserRepository userRepository;
    @Inject
    private TransactionHistoryService transactionHistoryService;

    @Inject
    private TransactionHistoryRepository transactionHistoryRepository;

    @Override
    public ApiResponse<?> requestMoney(TransactionDto transactionDto) {

        validateTransactionDtoRequest(transactionDto);
        User validBorrower = validateBorrower(transactionDto);
        User validLender = validateLender(transactionDto);
        checkPreviousTransactionExists(validLender.getId(), validBorrower.getId());
        Transaction mappedTransaction = Mapper.MapTransactionDtoToEntity(transactionDto, validLender.getId(), validBorrower.getId());
        Transaction requestMade = transacitonRepository.save(mappedTransaction);
        // created Transaction History
        System.out.println("Transacition History Created with REQUEST_On_HOLD");
        transactionHistoryService.create(requestMade);
        return new ApiResponse<>(200, "Money request has made successfully", requestMade);
    }

    private void validateTransactionDtoRequest(TransactionDto transactionDto) {

        if(transactionDto.getBorrowerUserName().isEmpty() || transactionDto.getLenderUserName().isEmpty()   )
        {
            throw new IllegalArgumentException("All the fields are mandatory");
        }
        if(transactionDto.getAmount() < 10)
        {
            throw new IllegalArgumentException("Amount should be greater than Rs 10..");
        }
        if (transactionDto.getEstimatedReturnTime() < 1 || transactionDto.getEstimatedReturnTime() >= 30) {
            throw new IllegalArgumentException("Invalid estimated day");
        }
    }


    private User validateLender(TransactionDto transactionDto) {

        String lenderUserName = transactionDto.getLenderUserName();
        User userExist = userRepository.findByUserName(lenderUserName).orElseThrow(
                () -> {
                    throw new IllegalArgumentException("No user found");
                }
        );
        if (userExist.getStatus() == false) {
            throw new IllegalArgumentException(userExist.getFirstName() + "  -> type [LENDER] " + " is not verified too make the money request");
        }

        return userRepository.findByUserNameAndUserType(lenderUserName, "LENDER").orElseThrow(
                () -> {
                    throw new IllegalArgumentException(lenderUserName + " doesnot exist as  LENDER");
                }
        );
    }

    private User validateBorrower(TransactionDto transactionDto) {
        String borrowerUserName = transactionDto.getBorrowerUserName();

        User userExist = userRepository.findByUserName(borrowerUserName).orElseThrow(
                () -> {
                    throw new IllegalArgumentException("No user found");
                }
        );

        if (userExist.getStatus() == false) {
            throw new IllegalArgumentException(userExist.getFirstName() + " ->  type [BORROWER] " + " is not verified too make the money request");
        }
        return userRepository.findByUserNameAndUserType(borrowerUserName, "BORROWER").orElseThrow(
                () -> {
                    throw new IllegalArgumentException(borrowerUserName + " doesnot exist as BORROWER");
                }
        );

    }

    private void checkPreviousTransactionExists(Long lenderId, Long borrowerId) {

        //The same user can have only one pending request with the lender
        Optional<Transaction> user = transacitonRepository.findByLenderIdAndBorrowerIdAndStatus(lenderId, borrowerId, "PENDING");
        System.out.println(user);

        if (user.isPresent()) {
            throw new IllegalArgumentException("You already have pending transaction, transaction failed..");
        }

        //if the previous transaction is there then you should
        List<TransactionHistory> previousUnPaidTransaction = transactionHistoryRepository.findByBorrowerIdAndPaymentStatus(borrowerId,"UNPAID");

        if(!previousUnPaidTransaction.isEmpty())
        {
            throw new IllegalArgumentException("You have previous UNPAID transaction.");
        }
    }


}

