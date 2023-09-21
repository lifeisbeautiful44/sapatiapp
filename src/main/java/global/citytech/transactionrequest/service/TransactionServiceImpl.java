package global.citytech.transactionrequest.service;

import global.citytech.transactionrequest.repository.Transaction;
import global.citytech.transactionrequest.repository.TransacitonRepository;
import global.citytech.transactionrequest.service.adapter.TransactionDto;
import global.citytech.transactionrequest.service.adapter.mapper.Mapper;
import global.citytech.user.repository.User;
import global.citytech.user.repository.UserRepository;
import global.citytech.user.service.adaptor.ApiResponse;
import jakarta.inject.Inject;

import java.util.List;
import java.util.Optional;

public class TransactionServiceImpl implements TransactionService {

    @Inject
    TransacitonRepository transacitonRepository;
    @Inject
    UserRepository userRepository;

    @Override
    public ApiResponse<?> requestMoney(TransactionDto transactionDto) {

        User validLender = validateLender(transactionDto);
        User validBorrower =  validateBorrower(transactionDto);

             checkPreviousTransactionExists(validLender.getId(),validBorrower.getId());
            Transaction mappedTransaction = Mapper.MapTransactionDtoToEntity(transactionDto, validLender.getId(), validBorrower.getId());
            Transaction requestMade = transacitonRepository.save(mappedTransaction);
            ApiResponse apiResponse = new ApiResponse<>(200, "Money request has made successfully", requestMade);
            return apiResponse;


    }


    private User validateLender(TransactionDto transactionDto) {

        String lenderUserName = transactionDto.getLenderUserName();
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

    private User validateBorrower(TransactionDto transactionDto) {
        String borrowerUserName =  transactionDto.getBorrowerUserName();

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

    private void checkPreviousTransactionExists(Long lenderId, Long borrowerId) {

               Optional<Transaction> user = transacitonRepository.findByLenderIdAndBorrowerIdAndStatus(lenderId,borrowerId,"PENDING");

               if(user.isPresent()) {
                   throw new IllegalArgumentException("You already have pending transaction, transaction failed.." );
               }


    }





}

