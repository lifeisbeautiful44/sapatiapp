package global.citytech.cashflow.service.cashflow;

import global.citytech.cashflow.repository.CashFlow;
import global.citytech.cashflow.repository.CashFlowRepository;
import global.citytech.cashflow.service.dto.CashDto;
import global.citytech.user.service.blacklist.BlackListService;
import global.citytech.common.exception.CustomResponseException;
import global.citytech.transaction.repository.Transaction;
import global.citytech.user.repository.User;
import global.citytech.user.repository.UserRepository;
import global.citytech.common.apiresponse.ApiResponse;
import jakarta.inject.Inject;

import java.util.Optional;

public class CashFlowServiceImpl implements CashFlowSevice {
    @Inject
    private CashFlowRepository cashFlowRepository;
    @Inject
    private UserRepository userRepository;
    @Inject
    private BlackListService blackListService;


    /*saves the newly created user in CashInfo Table*/
    @Override
    public void saveNewUserCashInformation(User verifiedUser) {
        CashFlow newUserCashTable = new CashFlow();
        if (verifiedUser.getUserType().equals("LENDER")) {
            newUserCashTable.setLenderId(verifiedUser.getId());
        } else {
            newUserCashTable.setBorrowerId(verifiedUser.getId());
        }
        newUserCashTable.setCashAmount(0.0);
        cashFlowRepository.save(newUserCashTable);
    }


    public void updateCashTransactionAccepted(Transaction transaction) {
        Optional<CashFlow> updateLenderCash = cashFlowRepository.findByLenderId(transaction.getLenderId());
        CashFlow updatedLenderCash = updateLenderCash.get();
        updatedLenderCash.setCashAmount(updatedLenderCash.getCashAmount() - transaction.getAmount());
        cashFlowRepository.update(updatedLenderCash);

        Optional<CashFlow> updateBorrowerCash = cashFlowRepository.findByBorrowerId(transaction.getBorrowerId());
        CashFlow updatedBorrowerCash = updateBorrowerCash.get();
        updatedBorrowerCash.setCashAmount(updatedBorrowerCash.getCashAmount() + transaction.getAmount());
        cashFlowRepository.update(updatedBorrowerCash);
    }

    public void updatePaymentSuccessfull(Long borrowerId, Long lenderId, Double amountWithInterest) {
        CashFlow borrowerCashInformation = cashFlowRepository.findByBorrowerId(borrowerId).get();
        //updating borrower information , subtracting  amount
        borrowerCashInformation.setCashAmount(borrowerCashInformation.getCashAmount() - amountWithInterest);
        cashFlowRepository.update(borrowerCashInformation);

        CashFlow lenderCashInformation = cashFlowRepository.findByLenderId(lenderId).get();
        System.out.println(lenderCashInformation);
        //updating lender information , adding   amount
        lenderCashInformation.setCashAmount(lenderCashInformation.getCashAmount() + amountWithInterest);
        cashFlowRepository.update(lenderCashInformation);
    }

    public ApiResponse<LoadBalanceResponse> loadBalance(CashDto cashDto) {

        checkIfUserIsBlackListed(cashDto);
        // CashFlow depoistCash = new CashFlow();
        User userName = userRepository.findByUserName(cashDto.getUserName()).orElseThrow(() ->
        {
            throw new CustomResponseException(400, "bad request", "User not found");
        });
        if (!userName.getStatus()) {
            throw new CustomResponseException(400, "bad request", "Only verified user can load balance.");
        }
        //In case further validation
        //Optional<CashFlow> depoistCash =  cashFlowRepository.findById(userName.getId());
        CashFlow depoistCash = cashFlowRepository.findByLenderIdOrBorrowerId(userName.getId(), userName.getId()).get();
        String userType = userName.getUserType();
        if (userType.equals("BORROWER")) {
            checkAmountLimit(cashDto);
            depoistCash.setCashAmount(cashDto.getAmount() + depoistCash.getCashAmount());
           CashFlow updatedBorrowerBalance =  cashFlowRepository.update(depoistCash);

           LoadBalanceResponse loadBalanceResponse = new LoadBalanceResponse();
           loadBalanceResponse.setUserName(cashDto.getUserName());
           loadBalanceResponse.setAmount(updatedBorrowerBalance.getCashAmount());
            return new ApiResponse<>(200, "success",loadBalanceResponse);
        } else {
            checkAmountLimit(cashDto);
            depoistCash.setCashAmount(cashDto.getAmount() + depoistCash.getCashAmount());

           CashFlow updatedLenderBalance =  cashFlowRepository.update(depoistCash);
            LoadBalanceResponse loadBalanceResponse = new LoadBalanceResponse();
            loadBalanceResponse.setUserName(cashDto.getUserName());
            loadBalanceResponse.setAmount(updatedLenderBalance.getCashAmount());
            return new ApiResponse<>(200, "success",loadBalanceResponse);
        }
    }

    private void checkAmountLimit(CashDto cashDto) {
        if (cashDto.getAmount() >= 50000) {
            throw new CustomResponseException(400, "bad request", "Transaction limit is only 50000.");
        }


    }
    private void checkIfUserIsBlackListed(CashDto cashDto) {
        String isUserBlacklisted =  cashDto.getUserName();
        Optional<User> user =  userRepository.findByUserName(isUserBlacklisted);
        if(user.isPresent())
        {
            blackListService.isUserBlacklisted(user.get().getId());
        }
    }



}
