package global.citytech.transaction.service.adapter.mapper;

import global.citytech.transaction.repository.Transaction;
import global.citytech.transaction.service.adapter.TransactionRequestDto;

public class Mapper {

    public static Transaction MapTransactionDtoToEntity(TransactionRequestDto transactionDto , Long lenderId, Long borrowerId)
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
