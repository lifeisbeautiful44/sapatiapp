package global.citytech.cashflow.repository;


import io.micronaut.core.annotation.Introspected;
import io.micronaut.core.annotation.Nullable;
import io.micronaut.data.annotation.GeneratedValue;
import io.micronaut.data.annotation.Id;
import io.micronaut.data.annotation.MappedEntity;
import io.micronaut.serde.annotation.Serdeable;

@Introspected
@Serdeable
@MappedEntity("cash_flow")
public class CashFlow {
    @Id
    @GeneratedValue
    private Long id;
    @Nullable
    private Long lenderId;
    @Nullable
    private Long borrowerId;
    @Nullable
    private Double cashAmount;
    public CashFlow() {
    }
    public CashFlow(Long id, @Nullable Long lenderId,@Nullable Long borrowerId,@Nullable Double cashAmount) {
        this.id = id;
        this.lenderId = lenderId;
        this.borrowerId = borrowerId;
        this.cashAmount = cashAmount;
    }
    @Nullable
    public Long getId() {
        return id;
    }
    @Nullable
    public void setId(Long id) {
        this.id = id;
    }
    @Nullable
    public Long getLenderId() {
        return lenderId;
    }
    @Nullable
    public void setLenderId(Long lenderId) {
        this.lenderId = lenderId;
    }
    @Nullable
    public Long getBorrowerId() {
        return borrowerId;
    }
    @Nullable
    public void setBorrowerId(Long borrowerId) {
        this.borrowerId = borrowerId;
    }
    @Nullable
    public Double getCashAmount() {
        return cashAmount;
    }
    @Nullable
    public void setCashAmount(Double cashAmount) {
        this.cashAmount = cashAmount;
    }
    @Override
    public String toString() {
        return "CashFlow{" +
                "id=" + id +
                ", lenderId=" + lenderId +
                ", borrowerId=" + borrowerId +
                ", cashAmount=" + cashAmount +
                '}';
    }
}
