package global.citytech.transactionrequest.service;

import global.citytech.transactionrequest.repository.Transaction;
import global.citytech.transactionrequest.repository.TransacitonRepository;
import global.citytech.transactionrequest.service.adapter.TransactionDto;
import global.citytech.transactionrequest.service.adapter.mapper.Mapper;
import global.citytech.user.repository.User;
import global.citytech.user.repository.UserRepository;
import global.citytech.user.service.adaptor.ApiResponse;
import jakarta.inject.Inject;

public class TransactionServiceImpl implements TransactionService {

    @Inject
    TransacitonRepository transacitonRepository;
    @Inject
    UserRepository userRepository;

    @Override
    public ApiResponse<?> requestMoney(TransactionDto transactionDto) {


        User validLender = validateLender(transactionDto);
        User validBorrower =  validateBorrower(transactionDto);

        if ((validLender.getStatus() && validBorrower.getStatus())) {
            checkPreviousTransactionExists(validLender.getId(),validBorrower.getId());
            Transaction mappedTransaction = Mapper.MapTransactionDtoToEntity(transactionDto, validLender.getId(), validBorrower.getId());
            Transaction requestMade = transacitonRepository.save(mappedTransaction);
            ApiResponse apiResponse = new ApiResponse<>(200, "Money request has made successfully", requestMade);
            return apiResponse;

        } else {
   throw new IllegalArgumentException("Borrower or Lender is not in a valid status to make the request");
        }
    }


    private User validateLender(TransactionDto transactionDto) {

        String lenderUserName = transactionDto.getLenderUserName();

        return     userRepository.findByUserNameAndUserType(lenderUserName,"LENDER").orElseThrow(
                () -> { throw new IllegalArgumentException(lenderUserName +" doesnot exist as  LENDER");}
        );
    }

    private User validateBorrower(TransactionDto transactionDto) {
        String borrowerUserName =  transactionDto.getBorrowerUserName();

        return   userRepository.findByUserNameAndUserType(borrowerUserName,"BORROWER").orElseThrow(
                () -> { throw new IllegalArgumentException(borrowerUserName +" doesnot exist as BORROWER" );}
        );

    }

    private void checkPreviousTransactionExists(Long lenderId, Long borrowerId) {

        if(    transacitonRepository.existsByLenderIdAndBorrowerId(lenderId,borrowerId))
            throw new IllegalArgumentException("Transaction is already made");

    }



}

