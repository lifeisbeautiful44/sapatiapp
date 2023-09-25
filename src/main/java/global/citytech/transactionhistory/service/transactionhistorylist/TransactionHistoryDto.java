package global.citytech.transactionhistory.service.transactionhistorylist;

import io.micronaut.core.annotation.Introspected;
import io.micronaut.serde.annotation.Serdeable;

@Introspected
@Serdeable
public class TransactionHistoryDto {

    private String userName;

    public TransactionHistoryDto(String userName) {
        this.userName = userName;
    }

    public TransactionHistoryDto() {
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    @Override
    public String toString() {
        return "TransactionHistoryDto{" +
                "userName='" + userName + '\'' +
                '}';
    }
}
