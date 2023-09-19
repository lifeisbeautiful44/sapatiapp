package global.citytech.transactionrequest.service.adapter;

import io.micronaut.core.annotation.Introspected;
import io.micronaut.serde.annotation.Serdeable;

import java.math.BigDecimal;

@Introspected
@Serdeable
public class TransactionDto {

    private BigDecimal amount;

    private Long willPay;

    public TransactionDto(BigDecimal amount, Long willPay) {
        this.amount = amount;
        this.willPay = willPay;
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

    @Override
    public String toString() {
        return "TransactionDto{" +
                "amount=" + amount +
                ", willPay=" + willPay +
                '}';
    }
}
