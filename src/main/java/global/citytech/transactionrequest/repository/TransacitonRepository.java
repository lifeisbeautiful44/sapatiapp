package global.citytech.transactionrequest.repository;

import io.micronaut.data.annotation.Repository;
import io.micronaut.data.jdbc.annotation.JdbcRepository;
import io.micronaut.data.model.query.builder.sql.Dialect;
import io.micronaut.data.repository.CrudRepository;

import java.util.Optional;

@Repository
@JdbcRepository(dialect = Dialect.POSTGRES)

public interface TransacitonRepository extends CrudRepository<Transaction, Long> {

    Boolean existsByLenderIdAndBorrowerId(long lenderId, long borrowerId);




}
