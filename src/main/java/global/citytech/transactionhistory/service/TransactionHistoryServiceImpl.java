package global.citytech.transactionhistory.service;

import global.citytech.transactionhistory.repository.TransactionHistory;
import global.citytech.transactionhistory.repository.TransactionHistoryRepository;
import global.citytech.transaction.repository.Transaction;
import jakarta.inject.Inject;

public class TransactionHistoryServiceImpl implements TransactionHistoryService {

    @Inject
    TransactionHistoryRepository transactionHistoryRepository;


    @Override
    public void create(Transaction transactionRequest) {

        TransactionHistory transactionHistory = new TransactionHistory();
        transactionHistory.setTransactionId(transactionRequest.getId());
        transactionHistory.setBorrowerId(transactionRequest.getBorrowerId());
        transactionHistory.setLenderId(transactionRequest.getLenderId());
        transactionHistory.setTransactionStatus(transactionRequest.getStatus());
        transactionHistory.setPaymentStatus("REQUEST_ON_PENDING");
        transactionHistoryRepository.save(transactionHistory);
    }

    @Override
    public void updateTransactionAccepted(Transaction transaction) {

        TransactionHistory transactionHistory = transactionHistoryRepository.findById(transaction.getId()).get();
        String status = transaction.getStatus();
        transactionHistory.setTransactionStatus(status);
        transactionHistory.setPaymentStatus(status.equals("REJECTED") ? "REJECTED" : "UNPAID");
        transactionHistoryRepository.update(transactionHistory);
    }


    public void updateTransactionPayment(Transaction transaction) {

        TransactionHistory transactionHistory = transactionHistoryRepository.findById(transaction.getId()).get();
        transactionHistory.setPaymentStatus("PAID");
        transactionHistoryRepository.update(transactionHistory);
    }



}
