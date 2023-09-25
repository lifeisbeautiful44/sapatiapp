package global.citytech.transactionhistory.service.transactionhistorylist;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.micronaut.core.annotation.Introspected;
import io.micronaut.serde.annotation.Serdeable;

@Introspected
@Serdeable
public class  TransactionHistoryResponse  {



@JsonInclude(JsonInclude.Include.NON_NULL)
    private String lenderUsername;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String borrowerUserName;

    private String paymentStatus;

    private String transactionStatus;

    private Double amount;

    public TransactionHistoryResponse() {
    }

    public TransactionHistoryResponse(String lenderUsername, String borrowerUserName, String paymentStatus, String transactionStatus, Double amount) {
        this.lenderUsername = lenderUsername;
        this.borrowerUserName = borrowerUserName;
        this.paymentStatus = paymentStatus;
        this.transactionStatus = transactionStatus;
        this.amount = amount;
    }

    public String getLenderUsername() {
        return lenderUsername;
    }

    public void setLenderUsername(String lenderUsername) {
        this.lenderUsername = lenderUsername;
    }

    public String getBorrowerUserName() {
        return borrowerUserName;
    }

    public void setBorrowerUserName(String borrowerUserName) {
        this.borrowerUserName = borrowerUserName;
    }

    public String getPaymentStatus() {
        return paymentStatus;
    }

    public void setPaymentStatus(String paymentStatus) {
        this.paymentStatus = paymentStatus;
    }

    public String getTransactionStatus() {
        return transactionStatus;
    }

    public void setTransactionStatus(String transactionStatus) {
        this.transactionStatus = transactionStatus;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    @Override
    public String toString() {
        return "TransactionHistoryResponse{" +
                "lenderUsername='" + lenderUsername + '\'' +
                ", borrowerUserName='" + borrowerUserName + '\'' +
                ", paymentStatus='" + paymentStatus + '\'' +
                ", transactionStatus='" + transactionStatus + '\'' +
                ", amount=" + amount +
                '}';
    }
}
