package global.citytech.transactionhistory.repository;

import io.micronaut.core.annotation.Introspected;
import io.micronaut.data.annotation.GeneratedValue;
import io.micronaut.data.annotation.Id;
import io.micronaut.data.annotation.MappedEntity;
import io.micronaut.serde.annotation.Serdeable;

@Introspected
@Serdeable
@MappedEntity("transaction_history")
public class TransactionHistory {

    @Id
    @GeneratedValue
    private Long id;
    private Long lenderId;
    private long borrowerId;
    private long transactionId;
    private String transactionStatus;
    private String paymentStatus;
    public TransactionHistory() {};
    public TransactionHistory(Long id, Long lenderId, long borrowerId, long transactionId, String transactionStatus, String paymentStatus) {
        this.id = id;
        this.lenderId = lenderId;
        this.borrowerId = borrowerId;
        this.transactionId = transactionId;
        this.transactionStatus = transactionStatus;
        this.paymentStatus = paymentStatus;
    }
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getLenderId() {
        return lenderId;
    }

    public void setLenderId(Long lenderId) {
        this.lenderId = lenderId;
    }

    public long getBorrowerId() {
        return borrowerId;
    }

    public void setBorrowerId(long borrowerId) {
        this.borrowerId = borrowerId;
    }

    public long getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(long transactionId) {
        this.transactionId = transactionId;
    }

    public String getTransactionStatus() {
        return transactionStatus;
    }

    public void setTransactionStatus(String transactionStatus) {
        this.transactionStatus = transactionStatus;
    }

    public String getPaymentStatus() {
        return paymentStatus;
    }

    public void setPaymentStatus(String paymentStatus) {
        this.paymentStatus = paymentStatus;
    }

    @Override
    public String toString() {
        return "TransactionHistory{" +
                "id=" + id +
                ", lenderId=" + lenderId +
                ", borrowerId=" + borrowerId +
                ", transactionId=" + transactionId +
                ", transactionStatus='" + transactionStatus + '\'' +
                ", paymentStatus='" + paymentStatus + '\'' +
                '}';
    }
}


