package global.citytech.transactionrequest.service.adapter;

import io.micronaut.core.annotation.Introspected;
import io.micronaut.serde.annotation.Serdeable;

import java.math.BigDecimal;

@Introspected
@Serdeable
public class TransactionDto {

    private BigDecimal amount;

    private Long willPay;

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

    public TransactionDto(BigDecimal amount, Long willPay, String borrowerUserName, String lenderUserName) {
        this.amount = amount;
        this.willPay = willPay;
        this.borrowerUserName =borrowerUserName;
        this.lenderUserName =lenderUserName;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public Long getWillPay() {
        return willPay;
    }

    public void setWillPay(Long willPay) {
        this.willPay = willPay;
    }

}
