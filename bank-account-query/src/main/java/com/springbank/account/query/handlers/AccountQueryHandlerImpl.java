package com.springbank.account.query.handlers;

import com.springbank.account.core.models.BankAccount;
import com.springbank.account.query.dto.AccountLookupResponse;
import com.springbank.account.query.dto.EqualityType;
import com.springbank.account.query.queries.FindAccountByHolderIdQuery;
import com.springbank.account.query.queries.FindAccountByIdQuery;
import com.springbank.account.query.queries.FindAccountsWithBalanceQuery;
import com.springbank.account.query.queries.FindAllAccountsQuery;
import com.springbank.account.query.repositories.AccountRepository;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.queryhandling.QueryHandler;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Slf4j
@Service
public class AccountQueryHandlerImpl implements AccountQueryHandler {

    private final AccountRepository accountRepository;

    @QueryHandler
    @Override
    public AccountLookupResponse findAll(FindAllAccountsQuery findAllAccountsQuery) {
        log.info("Find All accounts");
        return AccountLookupResponse.builder().accounts(accountRepository.findAll())
                .message("Successfully found accounts").build();
    }

    @QueryHandler
    @Override
    public AccountLookupResponse findById(FindAccountByIdQuery findAccountByIdQuery) {
        log.info("Find by id {}", findAccountByIdQuery);
        Optional<BankAccount> byId = accountRepository.findById(findAccountByIdQuery.getId());
        return byId.isPresent() ? new AccountLookupResponse(byId.get(), "Successfully found account")
                : AccountLookupResponse.builder().message("Account has not been found").build();
    }

    @QueryHandler
    @Override
    public AccountLookupResponse findByHolderId(FindAccountByHolderIdQuery findAccountByHolderIdQuery) {
        log.info("Find account by holder id {}", findAccountByHolderIdQuery.getHolderId());
        Optional<BankAccount> byId = accountRepository.findByAccountHolderId(findAccountByHolderIdQuery.getHolderId());
        return byId.isPresent() ? new AccountLookupResponse(byId.get(), "Successfully found account")
                : AccountLookupResponse.builder().message("Account has not been found").build();
    }

    @QueryHandler
    @Override
    public AccountLookupResponse findWithBalance(FindAccountsWithBalanceQuery findAccountsWithBalanceQuery) {
        return findAccountsWithBalanceQuery.getEqualityType() == EqualityType.GREATER_THAN ?
                AccountLookupResponse.builder()
                        .accounts(accountRepository.findByBalanceGreaterThan(findAccountsWithBalanceQuery.getBalance()))
                        .message("Successfully found accounts").build()
                : AccountLookupResponse.builder()
                        .accounts(accountRepository.findByBalanceLessThan(findAccountsWithBalanceQuery.getBalance()))
                        .message("Successfully found accounts").build();
    }
}
