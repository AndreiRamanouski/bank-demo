package com.springbank.account.query.handlers;

import com.springbank.account.core.events.AccountClosedEvent;
import com.springbank.account.core.events.AccountOpenedEvent;
import com.springbank.account.core.events.FundsDepositedEvent;
import com.springbank.account.core.events.FundsWithdrawnEvent;
import com.springbank.account.core.models.BankAccount;
import com.springbank.account.query.repositories.AccountRepository;
import java.math.BigDecimal;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.config.ProcessingGroup;
import org.axonframework.eventhandling.EventHandler;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Slf4j
@Service
@ProcessingGroup("bank-account-group")
public class AccountEventHandlerImpl implements AccountEventHandler {

    private final AccountRepository accountRepository;

    @EventHandler
    @Override
    public void on(AccountOpenedEvent accountOpenedEvent) {
        log.info("Open account {}", accountOpenedEvent);
        accountRepository.save(BankAccount.builder()
                .accountHolderId(accountOpenedEvent.getAccountHolderId())
                .accountType(accountOpenedEvent.getAccountType())
                .balance(accountOpenedEvent.getOpeningBalance())
                .createdAt(accountOpenedEvent.getCreatedAt())
                .id(accountOpenedEvent.getId())
                .build());
    }

    @EventHandler
    @Override
    public void on(FundsDepositedEvent fundsDepositedEvent) {
        log.info("Top up account {}", fundsDepositedEvent);
        updateAccountBalance(fundsDepositedEvent.getId(), fundsDepositedEvent.getBalance());
    }

    @EventHandler
    @Override
    public void on(FundsWithdrawnEvent fundsWithdrawnEvent) {
        log.info("Withdraw money from account {}", fundsWithdrawnEvent);
        updateAccountBalance(fundsWithdrawnEvent.getId(), fundsWithdrawnEvent.getBalance());

    }

    private void updateAccountBalance(String id, BigDecimal fundsEvent) {
        BankAccount account = accountRepository.findById(id).orElseThrow(() ->
                new RuntimeException("Account not found"));
        account.setBalance(fundsEvent);
        accountRepository.save(account);
    }

    @EventHandler
    @Override
    public void on(AccountClosedEvent accountClosedEvent) {
        log.info("Close account {}", accountClosedEvent.getId());
        accountRepository.deleteById(accountClosedEvent.getId());
    }
}
