package global.citytech.transactionrequest.repository;

import io.micronaut.core.annotation.Introspected;
import io.micronaut.data.annotation.DateCreated;
import io.micronaut.data.annotation.GeneratedValue;
import io.micronaut.data.annotation.Id;
import io.micronaut.data.annotation.MappedEntity;
import io.micronaut.serde.annotation.Serdeable;
import jakarta.annotation.Nullable;

import java.time.LocalDateTime;


@Serdeable
@Introspected
@MappedEntity("transaction")
public class Transaction {
    @Id
    @GeneratedValue(GeneratedValue.Type.IDENTITY)
    private Long id;
    private Long borrowerId;
    private Long lenderId;
    private Double amount;
    private String status;
    @DateCreated
    private String requestDate;
    @Nullable
    private Double interestRate;
    @Nullable
    private LocalDateTime  paymentAcceptedDate;
    @Nullable
    private Double amountWithInterest;
    private Long estimatedReturnTime;
    public Transaction() {
    };

    public Transaction(Long id, Long borrowerId, Long lenderId, Double amount, String status, String requestDate, @Nullable Double interestRate, @Nullable LocalDateTime paymentAcceptedDate, @Nullable Double amountWithInterest, Long estimatedReturnTime) {
        this.id = id;
        this.borrowerId = borrowerId;
        this.lenderId = lenderId;
        this.amount = amount;
        this.status = status;
        this.requestDate = requestDate;
        this.interestRate = interestRate;
        this.paymentAcceptedDate = paymentAcceptedDate;
        this.amountWithInterest = amountWithInterest;
        this.estimatedReturnTime = estimatedReturnTime;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getBorrowerId() {
        return borrowerId;
    }

    public void setBorrowerId(Long borrowerId) {
        this.borrowerId = borrowerId;
    }

    public Long getLenderId() {
        return lenderId;
    }

    public void setLenderId(Long lenderId) {
        this.lenderId = lenderId;
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

    @Nullable
    public Double getInterestRate() {
        return interestRate;
    }

    public void setInterestRate(@Nullable Double interestRate) {
        this.interestRate = interestRate;
    }

    @Nullable
    public LocalDateTime getPaymentAcceptedDate() {
        return paymentAcceptedDate;
    }

    public void setPaymentAcceptedDate(@Nullable LocalDateTime paymentAcceptedDate) {
        this.paymentAcceptedDate = paymentAcceptedDate;
    }

    @Nullable
    public Double getAmountWithInterest() {
        return amountWithInterest;
    }

    public void setAmountWithInterest(@Nullable Double amountWithInterest) {
        this.amountWithInterest = amountWithInterest;
    }

    public Long getEstimatedReturnTime() {
        return estimatedReturnTime;
    }

    public void setEstimatedReturnTime(Long estimatedReturnTime) {
        this.estimatedReturnTime = estimatedReturnTime;
    }

    @Override
    public String toString() {
        return "Transaction{" +
                "id=" + id +
                ", borrowerId=" + borrowerId +
                ", lenderId=" + lenderId +
                ", amount=" + amount +
                ", status='" + status + '\'' +
                ", requestDate='" + requestDate + '\'' +
                ", interestRate=" + interestRate +
                ", paymentAcceptedDate=" + paymentAcceptedDate +
                ", amountWithInterest=" + amountWithInterest +
                ", estimatedReturnTime=" + estimatedReturnTime +
                '}';
    }
}
