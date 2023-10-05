package global.citytech.transactionhistory.service;


import global.citytech.transaction.repository.Transaction;
import global.citytech.transactionhistory.repository.TransactionHistory;
import global.citytech.transactionhistory.repository.TransactionHistoryRepository;
import io.micronaut.test.extensions.junit5.annotation.MicronautTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@MicronautTest
public class TransactionHistoryServiceImplTest {


   private TransactionHistoryRepository transactionHistoryRepository;
   private TransactionHistoryServiceImpl transactionHistoryService;

   @BeforeEach
    public void setUp()
   {
       this.transactionHistoryRepository = Mockito.mock(TransactionHistoryRepository.class);
       this.transactionHistoryService = new TransactionHistoryServiceImpl(transactionHistoryRepository);

   }

    @Test
    public void testCreateTransaction() {
        // Given
        Transaction transactionRequest = new Transaction();
        transactionRequest.setId(1L);
        transactionRequest.setBorrowerId(2L);
        transactionRequest.setLenderId(3L);
        transactionRequest.setStatus("PENDING");

        // When
        transactionHistoryService.create(transactionRequest);

        // Then
        verify(transactionHistoryRepository, times(1)).save(any(TransactionHistory.class));
    }


}
