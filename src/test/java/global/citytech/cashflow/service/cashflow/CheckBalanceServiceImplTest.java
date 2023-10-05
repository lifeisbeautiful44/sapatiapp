package global.citytech.cashflow.service.cashflow;

import global.citytech.cashflow.repository.CashFlow;
import global.citytech.cashflow.repository.CashFlowRepository;
import global.citytech.cashflow.service.balancevalidation.CheckBalanceServiceImpl;
import global.citytech.common.exception.CustomResponseException;
import global.citytech.transaction.repository.Transaction;
import global.citytech.user.repository.UserRepository;
import io.micronaut.test.extensions.junit5.annotation.MicronautTest;
import jakarta.inject.Inject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyLong;

@MicronautTest
public class CheckBalanceServiceImplTest {

    private CashFlowRepository cashFlowRepository;
    private UserRepository userRepository;
    private CheckBalanceServiceImpl checkBalanceService;

    @BeforeEach
    public void setUp() {
        this.cashFlowRepository = Mockito.mock(CashFlowRepository.class);
        this.userRepository = Mockito.mock(UserRepository.class);
        this.checkBalanceService = new CheckBalanceServiceImpl(cashFlowRepository, userRepository);
    }


    @Test
    public void testCheckLenderBalanceWithSufficientBalance() {
          //given
        CashFlow mockCashFlow = new CashFlow();
        mockCashFlow.setCashAmount(1000.0);

        Mockito.when(cashFlowRepository.findByLenderId(anyLong())).thenReturn(java.util.Optional.of(mockCashFlow));
        Transaction  transaction = new Transaction();
        transaction.setLenderId(1L);
        double checkBalance = 500.0;

        //when
        boolean result = checkBalanceService.checkLenderBalance(transaction, checkBalance);
        //then
        assertTrue(result);
    }

    @Test
    public void testCheckLenderBalanceWithInsufficientBalance() {
        CashFlow mockCashFlow = new CashFlow();
        mockCashFlow.setCashAmount(200.0);

        Mockito.when(cashFlowRepository.findByLenderId(anyLong())).thenReturn(java.util.Optional.of(mockCashFlow));
        Transaction transaction = new Transaction();
        transaction.setLenderId(1L);
        double checkBalance = 500.0;

        assertThrows(CustomResponseException.class, () -> {
            checkBalanceService.checkLenderBalance(transaction, checkBalance);
        });
    }


}
