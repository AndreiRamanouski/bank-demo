package com.springbank.account.query.handlers;

import com.springbank.account.core.events.AccountClosedEvent;
import com.springbank.account.core.events.AccountOpenedEvent;
import com.springbank.account.core.events.FundsDepositedEvent;
import com.springbank.account.core.events.FundsWithdrawnEvent;

public interface AccountEventHandler {

    void on(AccountOpenedEvent accountOpenedEvent);
    void on(FundsDepositedEvent fundsDepositedEvent);
    void on(FundsWithdrawnEvent fundsWithdrawnEvent);
    void on(AccountClosedEvent accountClosedEvent);
}
