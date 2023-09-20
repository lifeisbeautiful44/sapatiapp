package global.citytech.transactionrequest.repository;

import io.micronaut.core.annotation.Introspected;
import io.micronaut.data.annotation.DateCreated;
import io.micronaut.data.annotation.GeneratedValue;
import io.micronaut.data.annotation.Id;
import io.micronaut.data.annotation.MappedEntity;
import io.micronaut.serde.annotation.Serdeable;
import jakarta.annotation.Nullable;

import java.math.BigDecimal;


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
    private String paidDate;

    @Nullable
    private String timeLeft;

    private int willPay;

    public Transaction() {
    }

    ;

    public Transaction(Long id, Long borrowerId, Long lenderId, Double amount, String status, String requestDate, @Nullable Double interestRate, @Nullable String paidDate, @Nullable String timeLeft, int willPay) {
        this.id = id;
        this.borrowerId = borrowerId;
        this.lenderId = lenderId;
        this.amount = amount;
        this.status = status;
        this.requestDate = requestDate;
        this.interestRate = interestRate;
        this.paidDate = paidDate;
        this.timeLeft = timeLeft;
        this.willPay = willPay;
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

    public void setAmount(
                                  Double amount) {
        this.amount = amount;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(
                              String status) {
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
    public String getPaidDate() {
        return paidDate;
    }

    public void setPaidDate(  @Nullable
                                String paidDate) {
        this.paidDate = paidDate;
    }

    @Nullable
    public String getTimeLeft() {
        return timeLeft;
    }

    public void setTimeLeft(@Nullable String timeLeft) {
        this.timeLeft = timeLeft;
    }

    public int getWillPay() {
        return willPay;
    }

    public void setWillPay(int willPay) {
        this.willPay = willPay;
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
                ", paidDate='" + paidDate + '\'' +
                ", timeLeft='" + timeLeft + '\'' +
                ", willPay=" + willPay +
                '}';
    }
}
