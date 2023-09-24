package global.citytech.transactionhistory.repository;

import global.citytech.transactionrequest.repository.Transaction;
import io.micronaut.data.annotation.Query;
import io.micronaut.data.annotation.Repository;
import io.micronaut.data.jdbc.annotation.JdbcRepository;
import io.micronaut.data.model.query.builder.sql.Dialect;
import io.micronaut.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

@Repository
@JdbcRepository(dialect = Dialect.POSTGRES)
public interface TransactionHistoryRepository extends CrudRepository<TransactionHistory, Long> {

    @Query("SELECT * FROM transaction_history t WHERE t.lender_id = :lenderId AND t.borrower_id = :borrowerId AND t.status = :status")
    List<TransactionHistory> findByLenderIdAndBorrowerIdWhereStatus(long lenderId, long borrowerId, String status);

    @Query("SELECT * FROM transaction_history t WHERE t.lender_id = :lenderId AND t.borrower_id = :borrowerId AND t.payment_status = :paymentStatus")
    Optional<TransactionHistory> findByLenderIdAndBorrowerIdWherePaymentStatus(long lenderId, long borrowerId, String paymentStatus);
}
