package com.springbank.account.query.handlers;

import com.springbank.account.query.dto.AccountLookupResponse;
import com.springbank.account.query.queries.FindAccountByHolderIdQuery;
import com.springbank.account.query.queries.FindAccountByIdQuery;
import com.springbank.account.query.queries.FindAccountsWithBalanceQuery;
import com.springbank.account.query.queries.FindAllAccountsQuery;

public interface AccountQueryHandler {

    AccountLookupResponse findAll(FindAllAccountsQuery findAllAccountsQuery);
    AccountLookupResponse findById(FindAccountByIdQuery findAccountByIdQuery);
    AccountLookupResponse findByHolderId(FindAccountByHolderIdQuery findAccountByHolderIdQuery);
    AccountLookupResponse findWithBalance(FindAccountsWithBalanceQuery findAccountsWithBalanceQuery);

}
