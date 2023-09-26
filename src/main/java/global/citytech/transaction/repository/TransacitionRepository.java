package global.citytech.transaction.repository;

import io.micronaut.data.annotation.Repository;
import io.micronaut.data.jdbc.annotation.JdbcRepository;
import io.micronaut.data.model.query.builder.sql.Dialect;
import io.micronaut.data.repository.CrudRepository;

import java.util.Optional;

@Repository
@JdbcRepository(dialect = Dialect.POSTGRES)

public interface TransacitionRepository extends CrudRepository<Transaction, Long> {
    Boolean existsByLenderIdAndBorrowerId(long lenderId, long borrowerId);
    Optional<Transaction> findByLenderIdAndBorrowerId(long lenderId, long borrowerId);
/*  Using optional , because one lender can request with one borrower  */
    Optional<Transaction> findByLenderIdAndBorrowerIdAndStatus(long lenderId, long borrowerId,String status);
    Optional<Transaction> findByIdAndLenderIdAndBorrowerId(long id, long lenderId, long borrowerId);
}
