package global.citytech.cashflow.repository;

import io.micronaut.data.annotation.Repository;
import io.micronaut.data.jdbc.annotation.JdbcRepository;
import io.micronaut.data.model.query.builder.sql.Dialect;
import io.micronaut.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

@Repository
@JdbcRepository(dialect = Dialect.POSTGRES)
public interface CashFlowRepository extends CrudRepository<CashFlow,Long> {
    Optional<CashFlow> findByLenderId( long lenderId);
    Optional<CashFlow> findByBorrowerId(Long borrowerId);
}
