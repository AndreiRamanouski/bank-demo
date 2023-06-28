package com.springbank.account.command.aggregates;

import static java.time.LocalDateTime.now;

import com.springbank.account.command.commands.CloseAccountCommand;
import com.springbank.account.command.commands.DepositFoundCommand;
import com.springbank.account.command.commands.OpenAccountCommand;
import com.springbank.account.command.commands.WithdrawFundsCommand;
import com.springbank.account.core.events.AccountClosedEvent;
import com.springbank.account.core.events.AccountOpenedEvent;
import com.springbank.account.core.events.FundsDepositedEvent;
import com.springbank.account.core.events.FundsWithdrawnEvent;
import java.math.BigDecimal;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.modelling.command.AggregateLifecycle;
import org.axonframework.spring.stereotype.Aggregate;

@Aggregate
@NoArgsConstructor
@Slf4j
public class AccountAggregate {

    @AggregateIdentifier
    private String id;
    private String accountHolderId;
    private BigDecimal balance;

    @CommandHandler
    public AccountAggregate(OpenAccountCommand openAccountCommand) {

        log.info("Open account {}", openAccountCommand);
        AggregateLifecycle.apply(AccountOpenedEvent.builder()
                .id(openAccountCommand.getId())
                .accountHolderId(openAccountCommand.getAccountHolderId())
                .openingBalance(openAccountCommand.getOpeningBalance())
                .accountType(openAccountCommand.getAccountType())
                .createdAt(now())
                .build());
    }

    @EventSourcingHandler
    public void on(AccountOpenedEvent accountOpenedEvent) {
        log.info("Set account aggregate fields {}", accountOpenedEvent);
        this.id = accountOpenedEvent.getId();
        this.accountHolderId = accountOpenedEvent.getAccountHolderId();
        this.balance = accountOpenedEvent.getOpeningBalance();
    }

    @CommandHandler
    public void handle(DepositFoundCommand depositFoundCommand) {
        log.info("Top up the account {}", depositFoundCommand);
        AggregateLifecycle.apply(FundsDepositedEvent.builder()
                .amount(depositFoundCommand.getAmount())
                .balance(new BigDecimal(String.valueOf(this.balance)).add(depositFoundCommand.getAmount()))
                .id(depositFoundCommand.getId()));
    }

    @EventSourcingHandler
    public void on(FundsDepositedEvent fundsDepositedEvent) {
        log.info("Add to aggregate account {}", fundsDepositedEvent);
        this.balance = new BigDecimal(String.valueOf(fundsDepositedEvent.getBalance()));
    }

    @CommandHandler
    public void handle(WithdrawFundsCommand withdrawFundsCommand) {
        log.info("Withdraw from account {}", withdrawFundsCommand);
        if (this.balance.compareTo(withdrawFundsCommand.getAmount()) != 1
                || this.balance.compareTo(withdrawFundsCommand.getAmount()) != 0) {
            throw new IllegalStateException("Insufficient funds");
        }

        AggregateLifecycle.apply(FundsWithdrawnEvent.builder()
                .amount(withdrawFundsCommand.getAmount())
                .balance(new BigDecimal(String.valueOf(this.balance)).subtract(withdrawFundsCommand.getAmount()))
                .id(withdrawFundsCommand.getId())
                .build());
    }

    @EventSourcingHandler
    public void on(FundsWithdrawnEvent fundsWithdrawnEvent) {
        log.info("Subtract from aggregate balance {}", fundsWithdrawnEvent);
        this.balance = new BigDecimal(String.valueOf(this.balance)).subtract(fundsWithdrawnEvent.getAmount());
    }


    @CommandHandler
    public void handle(CloseAccountCommand closeAccountCommand) {
        log.info("Close account {}", closeAccountCommand.getId());
        AggregateLifecycle.apply(AccountClosedEvent.builder()
                .id(closeAccountCommand.getId())
                .build());
    }

    @EventSourcingHandler
    public void oh(AccountClosedEvent accountClosedEvent){
        log.info("Close aggregate account {}", accountClosedEvent.getId());
        AggregateLifecycle.markDeleted();
    }

}
