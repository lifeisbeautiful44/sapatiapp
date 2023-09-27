package global.citytech.transaction.service.payment;

import io.micronaut.core.annotation.Introspected;
import io.micronaut.serde.annotation.Serdeable;

@Introspected
@Serdeable
public class TransactionPaymentBackResponse {

    private String borrowerName;
    private String lenderName;

    private Double amount;

    private Double interestRate;

    private Double paidBackAmmount;

    public TransactionPaymentBackResponse() {
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

    public Double getInterestRate() {
        return interestRate;
    }

    public void setInterestRate(Double interestRate) {
        this.interestRate = interestRate;
    }

    public Double getPaidBackAmmount() {
        return paidBackAmmount;
    }

    public void setPaidBackAmmount(Double paidBackAmmount) {
        this.paidBackAmmount = paidBackAmmount;
    }

    @Override
    public String toString() {
        return "TransactionPaymentBackResponse{" +
                "borrowerName='" + borrowerName + '\'' +
                ", lenderName='" + lenderName + '\'' +
                ", amount=" + amount +
                ", interestRate=" + interestRate +
                ", paidBackAmmount=" + paidBackAmmount +
                '}';
    }
}
