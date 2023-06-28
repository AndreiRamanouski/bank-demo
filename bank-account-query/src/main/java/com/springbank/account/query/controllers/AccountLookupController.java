package com.springbank.account.query.controllers;

import com.springbank.account.query.dto.AccountLookupResponse;
import com.springbank.account.query.dto.EqualityType;
import com.springbank.account.query.queries.FindAccountByHolderIdQuery;
import com.springbank.account.query.queries.FindAccountByIdQuery;
import com.springbank.account.query.queries.FindAccountsWithBalanceQuery;
import com.springbank.account.query.queries.FindAllAccountsQuery;
import java.math.BigDecimal;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.queryhandling.QueryGateway;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("api/v1/accounts")
public class AccountLookupController {

    private final QueryGateway queryGateway;


    @GetMapping
    public AccountLookupResponse findAll() {
        log.info("Find all accounts");
        return queryGateway.query(new FindAllAccountsQuery(), AccountLookupResponse.class).join();
    }

    @GetMapping("holder/{holderId}")
    public AccountLookupResponse findByHolderId(@PathVariable String holderId) {
        log.info("Find by holder id {}", holderId);
        return queryGateway.query(new FindAccountByHolderIdQuery(holderId), AccountLookupResponse.class).join();
    }

    @GetMapping("{id}")
    public AccountLookupResponse findById(@PathVariable String id) {
        log.info("Find by id {}", id);
        return queryGateway.query(new FindAccountByIdQuery(id), AccountLookupResponse.class).join();
    }

    @GetMapping("balance")
    public AccountLookupResponse findWithBalance(@RequestParam("equality_type") EqualityType equalityType,
            @RequestParam BigDecimal balance) {
        log.info("Find with balance {} {}", equalityType, balance);
        return queryGateway.query(new FindAccountsWithBalanceQuery(equalityType, balance), AccountLookupResponse.class)
                .join();
    }

}
