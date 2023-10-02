package global.citytech.transaction.service.request;

import global.citytech.user.service.blacklist.BlackListService;
import global.citytech.common.exception.CustomResponseException;
import global.citytech.transactionhistory.repository.TransactionHistory;
import global.citytech.transactionhistory.repository.TransactionHistoryRepository;
import global.citytech.transactionhistory.service.TransactionHistoryService;
import global.citytech.transaction.repository.TransacitionRepository;
import global.citytech.transaction.repository.Transaction;
import global.citytech.transaction.service.adapter.TransactionRequestDto;
import global.citytech.transaction.service.adapter.mapper.Mapper;
import global.citytech.user.repository.User;
import global.citytech.user.repository.UserRepository;
import global.citytech.common.apiresponse.ApiResponse;
import jakarta.inject.Inject;

import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

public class TransactionServiceImpl implements TransactionService {

    @Inject
    private TransacitionRepository transacitonRepository;
    @Inject
    private UserRepository userRepository;
    @Inject
    private TransactionHistoryService transactionHistoryService;

    @Inject
    private TransactionHistoryRepository transactionHistoryRepository;

    @Inject
    private BlackListService blackListService;

    @Override
    public ApiResponse<TransactionResponse> requestMoney(TransactionRequestDto transactionDto) {

        checkIfUserIsBlackListed(transactionDto);
        validateTransactionDtoRequest(transactionDto);
        User validBorrower = validateBorrower(transactionDto);
        User validLender = validateLender(transactionDto);
        checkPreviousTransactionExists(validLender.getId(), validBorrower.getId());
        Transaction mappedTransaction = Mapper.MapTransactionDtoToEntity(transactionDto, validLender.getId(), validBorrower.getId());
        Transaction requestMade = transacitonRepository.save(mappedTransaction);

        TransactionResponse transactionResponse = new TransactionResponse();
        transactionResponse.setBorrowerName(validBorrower.getUserName());
        transactionResponse.setLenderName(validLender.getUserName());
        transactionResponse.setAmount(requestMade.getAmount());
        transactionResponse.setStatus(requestMade.getStatus());
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        OffsetDateTime offsetDateTime = OffsetDateTime.parse(requestMade.getRequestDate());
        transactionResponse.setRequestDate(offsetDateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm a")));

        // created Transaction History
        System.out.println("Transaction History Created with REQUEST_On_HOLD");
        transactionHistoryService.create(requestMade);
        return new ApiResponse<>(200, "Money request has made successfully", transactionResponse);
    }

    private void checkIfUserIsBlackListed(TransactionRequestDto transactionDto) {
       String isUserBlacklisted =  transactionDto.getBorrowerUserName();
       Optional<User> user =  userRepository.findByUserName(isUserBlacklisted);
       if(user.isPresent())
       {
           blackListService.isUserBlacklisted(user.get().getId());
       }
    }

    private void validateTransactionDtoRequest(TransactionRequestDto transactionDto) {

        if(transactionDto.getBorrowerUserName().isEmpty() || transactionDto.getLenderUserName().isEmpty()   )
        {
            throw new CustomResponseException(400, "bad request", "All the fields are mandatory.");
        }
        if(transactionDto.getAmount() < 10)
        {
            throw new CustomResponseException(400, "bad request", "Amount should be greater than Rs 10..");

        }
        if (transactionDto.getEstimatedReturnTime() < 1 || transactionDto.getEstimatedReturnTime() >= 30) {
            throw new CustomResponseException(400, "bad request", "Invalid estimated day.");
        }
    }


    private User validateLender(TransactionRequestDto transactionDto) {

        String lenderUserName = transactionDto.getLenderUserName();
        User userExist = userRepository.findByUserName(lenderUserName).orElseThrow(
                () -> {
                    throw new CustomResponseException(400, "bad request", "No user found.");

                }
        );
        if (userExist.getStatus() == false) {
            throw new IllegalArgumentException(userExist.getFirstName() + "  -> type [LENDER] " + " is not verified too make the money request");
        }

        return userRepository.findByUserNameAndUserType(lenderUserName, "LENDER").orElseThrow(
                () -> {
                    throw new CustomResponseException(400, "bad request", lenderUserName +  "+ doesnot exist as  LENDER.");
                }
        );
    }

    private User validateBorrower(TransactionRequestDto transactionDto) {
        String borrowerUserName = transactionDto.getBorrowerUserName();

        User userExist = userRepository.findByUserName(borrowerUserName).orElseThrow(
                () -> {
                    throw new CustomResponseException(400, "bad request", "No user found.");
                }
        );

        if (userExist.getStatus() == false) {
            throw new CustomResponseException(400, "bad request", " ->  type [BORROWER]  is not verified too make the money request");

        }
        return userRepository.findByUserNameAndUserType(borrowerUserName, "BORROWER").orElseThrow(
                () -> {
                    throw new CustomResponseException(400, "bad request", "doesnot exist as BORROWER.");

                }
        );

    }

    private void checkPreviousTransactionExists(Long lenderId, Long borrowerId) {
        //The same user can have only one pending request with the lender
        Optional<Transaction> user = transacitonRepository.findByLenderIdAndBorrowerIdAndStatus(lenderId, borrowerId, "PENDING");
        System.out.println(user);

        if (user.isPresent()) {
            throw new CustomResponseException(400, "bad request", "You already have pending transaction with the same user, transaction failed...");
        }
        //if the previous transaction is there then you should, if the borrower payment status is unpaid , then it cannot do request
        Optional <TransactionHistory> previousUnPaidTransaction = transactionHistoryRepository.findByBorrowerIdAndPaymentStatus(borrowerId,"UNPAID");
        if(previousUnPaidTransaction.isPresent())
        {
            throw new CustomResponseException(400, "bad request", "You have previous UNPAID transaction..");

        }
    }


}

