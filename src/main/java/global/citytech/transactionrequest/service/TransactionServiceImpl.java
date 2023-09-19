package global.citytech.transactionrequest.service;

import global.citytech.transactionrequest.entity.Transaction;
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
    public ApiResponse<?> requestMoney(Long lenderId, Long borrowerId, TransactionDto transactionDto) {

        User validLender = validateLender(lenderId);
        User validBorrower = validateBorrower(borrowerId);

        if ((validLender.getStatus() && validBorrower.getStatus())) {
            checkPreviousTransactionExists(lenderId,borrowerId);
            Transaction mappedTransaction = Mapper.MapTransactionDtoToEntity(transactionDto, lenderId, borrowerId);
            Transaction requestMade = transacitonRepository.save(mappedTransaction);
            ApiResponse apiResponse = new ApiResponse<>(200, "Money request has made successfully", requestMade);
            return apiResponse;
        } else {
            throw new IllegalArgumentException("Borrower or Lender is not in a valid status to make the request");
        }
    }

    private void checkPreviousTransactionExists(Long lenderId, Long borrowerId) {

        throw new IllegalArgumentException("Transaction is already made");
    }

    private User validateBorrower(Long borrowerId) {
        User borrower = userRepository.findByIdAndUserType(borrowerId, "BORROWER").orElseThrow(
                () -> {
                    throw new IllegalArgumentException("Borrower unspecified");
                }
        );
        return borrower;

    }

    private User validateLender(Long lenderId) {

        User lender = userRepository.findByIdAndUserType(lenderId, "LENDER").orElseThrow(
                () -> {
                    throw new IllegalArgumentException("Lender unspecified");
                });
        return lender;
    }
}

