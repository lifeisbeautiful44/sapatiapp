package global.citytech.transactionrequest.service.adapter;

import io.micronaut.core.annotation.Introspected;
import io.micronaut.serde.annotation.Serdeable;

@Introspected
@Serdeable
public class TransactionAcceptDto {

    private String status;
    private String borrowerUserName;
    private String lenderUserName;

    public TransactionAcceptDto(String status, String borroweUserName, String lenderUserName) {
        this.status = status;
        this.borrowerUserName = borroweUserName;
        this.lenderUserName = lenderUserName;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getBorrowerUserName() {
        return borrowerUserName;
    }

    public void setBorrowerUserName(String borroweUserName) {
        this.borrowerUserName = borroweUserName;
    }

    public String getLenderUserName() {
        return lenderUserName;
    }

    public void setLenderUserName(String lenderUserName) {
        this.lenderUserName = lenderUserName;
    }

    @Override
    public String toString() {
        return "TransactionAcceptDto{" +
                "status='" + status + '\'' +
                ", borroweUserName='" + borrowerUserName + '\'' +
                ", lenderUserName='" + lenderUserName + '\'' +
                '}';
    }
}
