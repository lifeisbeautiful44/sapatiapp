package global.citytech.transactionhistory.service.transactionhistorylist;

import global.citytech.transaction.repository.TransacitonRepository;
import global.citytech.transaction.repository.Transaction;
import global.citytech.transactionhistory.repository.TransactionHistory;
import global.citytech.transactionhistory.repository.TransactionHistoryRepository;
import global.citytech.user.repository.User;
import global.citytech.user.repository.UserRepository;
import global.citytech.user.service.adaptor.ApiResponse;
import jakarta.inject.Inject;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class TransactionHistoryListServiceImpl implements TransactionHistoryListService {
    @Inject
    UserRepository userRepository;
    @Inject
    TransacitonRepository transacitonRepository;
    @Inject
    TransactionHistoryRepository transactionHistoryRepository;

    @Override
    public ApiResponse<List<TransactionHistoryResponse>> findAllBorrowerTransactionHistory(TransactionHistoryDto transactionHistory) {


        User user = getUserByUsernameOrThrow(transactionHistory.getUserName());
        List<TransactionHistoryResponse> response = new ArrayList<>();

        if (user.getUserType().equals("BORROWER")) {
            List<TransactionHistory> borrowerTransactionHistoryList = transactionHistoryRepository.findByBorrowerId(user.getId());
            if (borrowerTransactionHistoryList.isEmpty()) {
                return new ApiResponse<>(200, "No transaction history", response);
            }

            for (TransactionHistory borrowerTransactionHistory : borrowerTransactionHistoryList) {
                Optional<User> lenderUser = userRepository.findById(borrowerTransactionHistory.getLenderId());
                Optional<User> borrowerUser = userRepository.findById(borrowerTransactionHistory.getBorrowerId());
                Transaction transaction = transacitonRepository.findByIdAndLenderIdAndBorrowerId(borrowerTransactionHistory.getTransactionId(), lenderUser.get().getId(), borrowerUser.get().getId()).get();
                TransactionHistoryResponse transactionHistoryResponse = new TransactionHistoryResponse();
                transactionHistoryResponse.setLenderUsername(lenderUser.get().getUserName());
                transactionHistoryResponse.setTransactionStatus(borrowerTransactionHistory.getTransactionStatus());
                transactionHistoryResponse.setPaymentStatus(borrowerTransactionHistory.getPaymentStatus());
                transactionHistoryResponse.setAmount(borrowerTransactionHistory.getPaymentStatus().equals("PAID") ? transaction.getAmountWithInterest() : transaction.getAmount());
                response.add(transactionHistoryResponse);
            }
            return new ApiResponse<>(200, "No transaction history", response);
        } else {
            List<TransactionHistory> lenderTransactionHistoryList = transactionHistoryRepository.findByLenderId(user.getId());
            if (lenderTransactionHistoryList.isEmpty()) {
                return new ApiResponse<>(200, "No transaction history", response);
            }
            for (TransactionHistory lenderTransactionHistory : lenderTransactionHistoryList) {
                Optional<User> lenderUser = userRepository.findById(lenderTransactionHistory.getLenderId());
                Optional<User> borrowerUser = userRepository.findById(lenderTransactionHistory.getBorrowerId());
                Transaction transaction = transacitonRepository.findByIdAndLenderIdAndBorrowerId(lenderTransactionHistory.getTransactionId(), lenderUser.get().getId(), borrowerUser.get().getId()).get();
                TransactionHistoryResponse transactionHistoryResponse = new TransactionHistoryResponse();
                transactionHistoryResponse.setBorrowerUserName(borrowerUser.get().getUserName());
                transactionHistoryResponse.setTransactionStatus(lenderTransactionHistory.getTransactionStatus());
                transactionHistoryResponse.setPaymentStatus(lenderTransactionHistory.getPaymentStatus());
                transactionHistoryResponse.setAmount(lenderTransactionHistory.getPaymentStatus().equals("PAID") ? transaction.getAmountWithInterest() : transaction.getAmount());
                response.add(transactionHistoryResponse);
            }
            return new ApiResponse<>(200, "No transaction history", response);
        }
    }


    private User getUserByUsernameOrThrow(String username) {
        User user = userRepository.findByUserName(username)
                .orElseThrow(() -> new IllegalArgumentException("No such User exists."));
        if (!user.getStatus()) {
            throw new IllegalArgumentException("User is not verified");
        }
        return user;
    }

}
