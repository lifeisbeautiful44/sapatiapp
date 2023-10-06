package global.citytech.notification.service;

import global.citytech.transaction.repository.TransacitionRepository;
import global.citytech.transaction.repository.Transaction;
import global.citytech.transactionhistory.repository.TransactionHistory;
import global.citytech.transactionhistory.repository.TransactionHistoryRepository;
import global.citytech.user.repository.User;
import global.citytech.user.repository.UserRepository;
import global.citytech.user.service.updateblacklist.UpdateBlackListUserStatusService;
import global.citytech.user.service.updateuserstatus.UserStatusDto;
import jakarta.inject.Inject;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Optional;

public class CheckPaymentDeadlineServiceImpl implements CheckPaymentDeadLineService{
    private TransactionHistoryRepository transactionHistoryRepository;
    private TransacitionRepository transacitionRepository;
    private UserRepository userRepository;
    private UpdateBlackListUserStatusService updateBlackListUserStatusService;

    @Inject
    public CheckPaymentDeadlineServiceImpl(TransactionHistoryRepository transactionHistoryRepository, TransacitionRepository transacitionRepository, UserRepository userRepository, UpdateBlackListUserStatusService updateBlackListUserStatusService) {
        this.transactionHistoryRepository = transactionHistoryRepository;
        this.transacitionRepository = transacitionRepository;
        this.userRepository = userRepository;
        this.updateBlackListUserStatusService = updateBlackListUserStatusService;
    }

    public void findThePaymentDeadline(String userName) {
        Optional<User> user = userRepository.findByUserNameAndUserType(userName, "BORROWER");
        if (user.isPresent()) {
            String from = "srijansil.bohara@gmail.com";
            String to = user.get().getEmail();
            String subject = "Payment Notification";
            Optional<TransactionHistory> unpaidTransactionHistory = transactionHistoryRepository.findByBorrowerIdAndPaymentStatus(user.get().getId(), "UNPAID");
            if (unpaidTransactionHistory.isPresent()) {
                Optional<Transaction> unpaidTransaction = transacitionRepository.findById(unpaidTransactionHistory.get().getTransactionId());
                LocalDateTime paymentAcceptedDate = unpaidTransaction.get().getPaymentAcceptedDate();
                LocalDateTime currentDate = LocalDateTime.now();
                LocalDateTime deadLine = ChronoUnit.DAYS.addTo(paymentAcceptedDate, unpaidTransaction.get().getEstimatedReturnTime());
                long daysDifference = ChronoUnit.DAYS.between(currentDate, deadLine);
                switch ((int) daysDifference) {
                    case 4:
                        SendNotificationService.sendMail(from, to, subject, "You have four days for your payment . Amount: " + unpaidTransaction.get().getAmount());
                        break;
                    case 1:
                        SendNotificationService.sendMail(from, to, subject, "You have 1 day for your payment . Amount: " + unpaidTransaction.get().getAmount());
                        break;
                    case 0:
                        UserStatusDto userStatusDto = new UserStatusDto();
                        userStatusDto.setUserName(user.get().getUserName());
                        updateBlackListUserStatusService.updateBlackListUser(userStatusDto);
                        break;
                }
            }
        }

    }
}


