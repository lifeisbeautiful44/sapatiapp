package global.citytech.transactionrequest.service.adapter;

import io.micronaut.core.annotation.Introspected;
import io.micronaut.serde.annotation.Serdeable;

@Introspected
@Serdeable
public class TransactionAcceptDto {

    private String status;
    private String borroweUserName;
    private String lenderUserName;

    public TransactionAcceptDto(String status, String borroweUserName, String lenderUserName) {
        this.status = status;
        this.borroweUserName = borroweUserName;
        this.lenderUserName = lenderUserName;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getBorroweUserName() {
        return borroweUserName;
    }

    public void setBorroweUserName(String borroweUserName) {
        this.borroweUserName = borroweUserName;
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
                ", borroweUserName='" + borroweUserName + '\'' +
                ", lenderUserName='" + lenderUserName + '\'' +
                '}';
    }
}
