package global.citytech.transactionrequest.service.adapter.mapper;

import global.citytech.transactionrequest.repository.Transaction;
import global.citytech.transactionrequest.service.adapter.TransactionDto;

public class Mapper {

    public static Transaction MapTransactionDtoToEntity(TransactionDto transactionDto , Long lenderId, Long borrowerId)
    {

        Transaction transaction = new Transaction();
        transaction.setAmount(transactionDto.getAmount());
        transaction.setEstimatedReturnTime(transactionDto.getEstimatedReturnTime());
        transaction.setLenderId(lenderId);
        transaction.setBorrowerId(borrowerId);
        transaction.setStatus("PENDING");

        return  transaction;

    }
}
