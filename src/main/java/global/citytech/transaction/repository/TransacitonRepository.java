package global.citytech.transaction.repository;

import io.micronaut.data.annotation.Repository;
import io.micronaut.data.jdbc.annotation.JdbcRepository;
import io.micronaut.data.model.query.builder.sql.Dialect;
import io.micronaut.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

@Repository
@JdbcRepository(dialect = Dialect.POSTGRES)

public interface TransacitonRepository extends CrudRepository<Transaction, Long> {
    Boolean existsByLenderIdAndBorrowerId(long lenderId, long borrowerId);
    Optional<Transaction> findByLenderIdAndBorrowerId(long lenderId, long borrowerId);

    Optional<Transaction> findByLenderIdAndBorrowerIdAndStatus(long lenderId, long borrowerId,String status);
    List<Transaction> findByBorrowerIdAndStatus(long borrowerId, String status);

    List<Transaction> findByBorrowerId(long borrowerId);

    Optional<Transaction> findByIdAndLenderIdAndBorrowerId(long id, long lenderId, long borrowerId);



 //   List<Transaction> findByBorrowerIdAndLenderIdAndStatus(long borrowerId, long lenderId, String status);

//    @Query("SELECT * FROM Transaction t WHERE t.lender_id = :lenderId AND t.borrower_id = :borrowerId AND t.status = :status")
//    List<Transaction> findByLenderIdAndBorrowerIdWhereStatus(long lenderId, long borrowerId, String status);



}
