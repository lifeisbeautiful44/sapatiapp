package global.citytech.transactionrequest.service.adapter.mapper;

import global.citytech.transactionrequest.entity.Transaction;
import global.citytech.transactionrequest.service.adapter.TransactionDto;

public class Mapper {

    public static Transaction MapTransactionDtoToEntity(TransactionDto transactionDto , Long lenderId, Long borrowerId)
    {

        Transaction transaction = new Transaction();
        transaction.setAmount(transactionDto.getAmount());
        transaction.setWillPay(transactionDto.getWillPay());
        transaction.setLenderId(lenderId);
        transaction.setBorrowerId(borrowerId);
        transaction.setStatus("PENDING");

        return  transaction;

    }
}
