package global.citytech.cashflow.service;

import global.citytech.cashflow.repository.CashFlow;
import global.citytech.cashflow.repository.CashFlowRepository;
import global.citytech.transactionrequest.repository.Transaction;
import global.citytech.user.repository.User;
import global.citytech.user.repository.UserRepository;
import jakarta.inject.Inject;

import java.util.Optional;

public class ClassFlowServiceImpl implements CashFlowSevice {
    @Inject
    private CashFlowRepository cashFlowRepository;
    @Inject
    private UserRepository userRepository;

    public void updateCashTransactionAccepted(Transaction transaction) {
        Optional<CashFlow> updateLenderCash = cashFlowRepository.findByLenderId(transaction.getLenderId());
        CashFlow updatedLenderCash = updateLenderCash.get();
        updatedLenderCash.setCashAmount(updatedLenderCash.getCashAmount() - transaction.getAmount());
        cashFlowRepository.update(updatedLenderCash);
        System.out.println(cashFlowRepository);

        Optional<CashFlow> updateBorrowerCash = cashFlowRepository.findByBorrowerId(transaction.getBorrowerId());
        CashFlow updatedBorrowerCash = updateBorrowerCash.get();
        updatedBorrowerCash.setCashAmount(updatedBorrowerCash.getCashAmount() + transaction.getAmount());
        cashFlowRepository.update(updatedBorrowerCash);
        System.out.println(cashFlowRepository);
    }

    public String loadBalance(CashDto cashDto) {
        // CashFlow depoistCash = new CashFlow();
        User userName = userRepository.findByUserName(cashDto.getUserName()).orElseThrow(() ->
        {
            throw new IllegalArgumentException("User not found");
        });

        if (userName.getStatus() == false) {
            throw new IllegalArgumentException("Only verified user can load balance.");
        }
        System.out.println(userName);
        System.out.println(userName.getId());
        //In case further validation
        //Optional<CashFlow> depoistCash =  cashFlowRepository.findById(userName.getId());
        CashFlow depoistCash = cashFlowRepository.findByLenderIdOrBorrowerId(userName.getId(), userName.getId()).get();


        System.out.println(depoistCash);
        System.out.println(userName.getUserType());

        if (userName.getUserType().equals("BORROWER")) {
            if (cashDto.getAmount() >= 50000) {
                return "Transacition limit is only 50000. ";
            }
            depoistCash.setCashAmount(cashDto.getAmount() + depoistCash.getCashAmount());
            cashFlowRepository.update(depoistCash);
            return "amount has been successfully updated";
        } else {
            depoistCash.setCashAmount(cashDto.getAmount() + depoistCash.getCashAmount());
            cashFlowRepository.update(depoistCash);
            return "amount has been successfully updated";
        }
    }

    //saves the newly created user in CashInfo Table
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

    @Override
    public Boolean isSufficientBalance(Long userId, double checkBalance) {

        Optional<CashFlow> userBalanceCheck = cashFlowRepository.findByLenderIdOrBorrowerId(userId,userId);
        System.out.println(userBalanceCheck);
        if (userBalanceCheck.get().getCashAmount() >= checkBalance) {
            return true;
        } else {
            throw new IllegalArgumentException("Not sufficient balance to make the request");
        }
    }


    public void updatePaymentSuccessfull(Long borrowerId, Long lenderId, Double amount) {
        CashFlow borrowerCashInformation = cashFlowRepository.findByBorrowerId(borrowerId).get();
        //updating borrower information , subtracting  amount
        System.out.println(borrowerCashInformation);

        borrowerCashInformation.setCashAmount(borrowerCashInformation.getCashAmount() - amount);
        cashFlowRepository.update(borrowerCashInformation);

        CashFlow lenderCashInformation = cashFlowRepository.findByLenderId(lenderId).get();
        System.out.println(lenderCashInformation);
        //updating lender information , adding   amount
        lenderCashInformation.setCashAmount(lenderCashInformation.getCashAmount() + amount);
        cashFlowRepository.update(lenderCashInformation);

    }

   public void   checkAmountPaid(Double amountToReturn , Double amountProivded)
    {
        System.out.println(amountToReturn + " "+ amountProivded);
        if(amountProivded < amountToReturn)
        {
            throw new IllegalArgumentException( "Return amount  is : " + amountToReturn + "  Provided amount: "+ amountProivded);
        }

    }


}
