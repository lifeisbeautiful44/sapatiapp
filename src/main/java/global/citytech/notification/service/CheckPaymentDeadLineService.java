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

public interface CheckPaymentDeadLineService {

    void findThePaymentDeadline(String userName);





}
