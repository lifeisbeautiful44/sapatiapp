package global.citytech.cashflow.service.cashflow;

import io.micronaut.core.annotation.Introspected;
import io.micronaut.serde.annotation.Serdeable;

@Introspected
@Serdeable
public class LoadBalanceResponse {

    private String userName;
    private Double amount;

    public LoadBalanceResponse() {
    }

    public LoadBalanceResponse(String userName, Double amount) {
        this.userName = userName;
        this.amount = amount;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    @Override
    public String toString() {
        return "LoadBalanceResponse{" +
                "userName='" + userName + '\'' +
                ", amount=" + amount +
                '}';
    }
}
