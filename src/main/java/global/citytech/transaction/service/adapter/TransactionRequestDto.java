package global.citytech.transaction.service.adapter;

import io.micronaut.core.annotation.Introspected;
import io.micronaut.serde.annotation.Serdeable;

@Introspected
@Serdeable
public class TransactionRequestDto {

    private Double amount;

    private long estimatedReturnTime;

    private String borrowerUserName;

    private String lenderUserName;


    public TransactionRequestDto() {
    }

    public TransactionRequestDto(Double amount, long estimatedReturnTime, String borrowerUserName, String lenderUserName) {
        this.amount = amount;
        this.estimatedReturnTime = estimatedReturnTime;
        this.borrowerUserName = borrowerUserName;
        this.lenderUserName = lenderUserName;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public long getEstimatedReturnTime() {
        return estimatedReturnTime;
    }

    public void setEstimatedReturnTime(long estimatedReturnTime) {
        this.estimatedReturnTime = estimatedReturnTime;
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
        return "TransactionDto{" +
                "amount=" + amount +
                ", estimatedReturnTime=" + estimatedReturnTime +
                ", borrowerUserName='" + borrowerUserName + '\'' +
                ", lenderUserName='" + lenderUserName + '\'' +
                '}';
    }
}
