package global.citytech.transaction.service.acceptrequest;

import io.micronaut.core.annotation.Introspected;
import io.micronaut.serde.annotation.Serdeable;

@Introspected
@Serdeable
public class TransactionAcceptResponse {

    private String borrowerUserName;
    private String lenderUserName;
    private String status;
    private String requestedDate;
    private Double interestRate;
    private String paymentAcceptedDate;

    public TransactionAcceptResponse() {
    }

    public TransactionAcceptResponse(String borrowerUserName, String lenderUserName, String status, String requestedDate, Double interestRate, String paymentAcceptedDate) {
        this.borrowerUserName = borrowerUserName;
        this.lenderUserName = lenderUserName;
        this.status = status;
        this.requestedDate = requestedDate;
        this.interestRate = interestRate;
        this.paymentAcceptedDate = paymentAcceptedDate;
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getRequestedDate() {
        return requestedDate;
    }

    public void setRequestedDate(String requestedDate) {
        this.requestedDate = requestedDate;
    }

    public Double getInterestRate() {
        return interestRate;
    }

    public void setInterestRate(Double interestRate) {
        this.interestRate = interestRate;
    }

    public String getPaymentAcceptedDate() {
        return paymentAcceptedDate;
    }

    public void setPaymentAcceptedDate(String paymentAcceptedDate) {
        this.paymentAcceptedDate = paymentAcceptedDate;
    }

    @Override
    public String toString() {
        return "TransactionAcceptResponse{" +
                "borrowerUserName='" + borrowerUserName + '\'' +
                ", lenderUserName='" + lenderUserName + '\'' +
                ", status='" + status + '\'' +
                ", requestedDate='" + requestedDate + '\'' +
                ", interestRate='" + interestRate + '\'' +
                ", paymentAcceptedDate='" + paymentAcceptedDate + '\'' +
                '}';
    }
}
