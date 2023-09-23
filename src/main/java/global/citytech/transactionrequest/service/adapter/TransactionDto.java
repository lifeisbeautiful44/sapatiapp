package global.citytech.transactionrequest.service.adapter;

import io.micronaut.core.annotation.Introspected;
import io.micronaut.serde.annotation.Serdeable;

import java.math.BigDecimal;

@Introspected
@Serdeable
public class TransactionDto implements TransactionDI {

    private Double amount;

    private int willPay;

    private String borrowerUserName;

    private String lenderUserName;

    public String getBorrowerUserName() {
        return borrowerUserName;
    }

    @Override
    public String toString() {
        return "TransactionDto{" +
                "amount=" + amount +
                ", willPay=" + willPay +
                ", borrowerUserName='" + borrowerUserName + '\'' +
                ", lenderUserName='" + lenderUserName + '\'' +
                '}';
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

    public TransactionDto(Double amount, int willPay, String borrowerUserName, String lenderUserName) {
        this.amount = amount;
        this.willPay = willPay;
        this.borrowerUserName =borrowerUserName;
        this.lenderUserName =lenderUserName;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public int getWillPay() {
        return willPay;
    }

    public void setWillPay(int willPay) {
        this.willPay = willPay;
    }

}
