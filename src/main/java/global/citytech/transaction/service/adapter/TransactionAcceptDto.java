package global.citytech.transaction.service.adapter;

import io.micronaut.core.annotation.Introspected;
import io.micronaut.serde.annotation.Serdeable;

@Introspected
@Serdeable
public class TransactionAcceptDto {

    private Double interestRate;
    private String borrowerUserName;
    private String lenderUserName;

    public TransactionAcceptDto() {
    }

    public TransactionAcceptDto(Double interestRate, String borrowerUserName, String lenderUserName) {
        this.interestRate = interestRate;
        this.borrowerUserName = borrowerUserName;
        this.lenderUserName = lenderUserName;
    }

    public Double getInterestRate() {
        return interestRate;
    }

    public void setInterestRate(Double interestRate) {
        this.interestRate = interestRate;
    }

    public String getBorrowerUserName() {
        return borrowerUserName;
    }

    public void setBorrowerUserName(String borrowerUserName) {
        this.borrowerUserName = borrowerUserName;
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
                "interestRate=" + interestRate +
                ", borrowerUserName='" + borrowerUserName + '\'' +
                ", lenderUserName='" + lenderUserName + '\'' +
                '}';
    }
}
