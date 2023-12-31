package global.citytech.user.repository;

import io.micronaut.data.annotation.Repository;
import io.micronaut.data.jdbc.annotation.JdbcRepository;
import io.micronaut.data.model.query.builder.sql.Dialect;
import io.micronaut.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

@Repository
@JdbcRepository(dialect = Dialect.POSTGRES)
public interface UserRepository extends CrudRepository<User, Long> {

    Boolean existsByUserName(String userName);
    Boolean existsByEmail(String email);
    Optional<User> findByUserName(String userName);
    Optional<User> findByUserNameAndUserType(String userName, String userType);
    List<User> findByUserType(String userType);

}
