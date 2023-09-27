package global.citytech.transaction.service.request;

import io.micronaut.core.annotation.Introspected;
import io.micronaut.serde.annotation.Serdeable;

@Introspected
@Serdeable
public class TransactionResponse {


    private String borrowerName;
            private String lenderName;
    private Double amount;
    private String status;
    private String requestDate;



    public TransactionResponse() {
    }

    public TransactionResponse(String borrowerName, String lenderName, Double amount, String status, String requestDate) {
        this.borrowerName = borrowerName;
        this.lenderName = lenderName;
        this.amount = amount;
        this.status = status;
        this.requestDate = requestDate;
    }

    public String getBorrowerName() {
        return borrowerName;
    }

    public void setBorrowerName(String borrowerName) {
        this.borrowerName = borrowerName;
    }

    public String getLenderName() {
        return lenderName;
    }

    public void setLenderName(String lenderName) {
        this.lenderName = lenderName;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getRequestDate() {
        return requestDate;
    }

    public void setRequestDate(String requestDate) {
        this.requestDate = requestDate;
    }

    @Override
    public String toString() {
        return "TransactionResponse{" +
                "borrowerName='" + borrowerName + '\'' +
                ", lenderName='" + lenderName + '\'' +
                ", amount=" + amount +
                ", status='" + status + '\'' +
                ", requestDate='" + requestDate + '\'' +
                '}';
    }
}
