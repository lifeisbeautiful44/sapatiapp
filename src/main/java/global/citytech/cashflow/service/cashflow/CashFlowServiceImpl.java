package global.citytech.cashflow.service.cashflow;

import global.citytech.cashflow.repository.CashFlow;
import global.citytech.cashflow.repository.CashFlowRepository;
import global.citytech.cashflow.service.CashDto;
import global.citytech.transaction.repository.Transaction;
import global.citytech.user.repository.User;
import global.citytech.user.repository.UserRepository;
import jakarta.inject.Inject;

import java.util.Optional;

public class CashFlowServiceImpl implements CashFlowSevice {
    @Inject
    private CashFlowRepository cashFlowRepository;
    @Inject
    private UserRepository userRepository;


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

    public String loadBalance(CashDto cashDto) {
        // CashFlow depoistCash = new CashFlow();
        User userName = userRepository.findByUserName(cashDto.getUserName()).orElseThrow(() ->
        {
            throw new IllegalArgumentException("User not found");
        });

        if (!userName.getStatus()) {
            throw new IllegalArgumentException("Only verified user can load balance.");
        }
        //In case further validation
        //Optional<CashFlow> depoistCash =  cashFlowRepository.findById(userName.getId());
        CashFlow depoistCash = cashFlowRepository.findByLenderIdOrBorrowerId(userName.getId(), userName.getId()).get();
        String userType = userName.getUserType();
        if (userType.equals("BORROWER")) {
            checkAmountLimit(cashDto);
            depoistCash.setCashAmount(cashDto.getAmount() + depoistCash.getCashAmount());
            cashFlowRepository.update(depoistCash);
            return "amount has been successfully updated";
        } else {
            checkAmountLimit(cashDto);
            depoistCash.setCashAmount(cashDto.getAmount() + depoistCash.getCashAmount());
            cashFlowRepository.update(depoistCash);
            return "amount has been successfully updated";
        }
    }

    private void checkAmountLimit(CashDto cashDto) {
        if (cashDto.getAmount() >= 50000) {
            throw new IllegalArgumentException("Transaction limit is only 50000");
        }

    }




}