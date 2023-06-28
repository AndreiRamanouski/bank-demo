package com.springbank.account.query.queries;

import com.springbank.account.query.dto.EqualityType;
import java.math.BigDecimal;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class FindAccountsWithBalanceQuery {

    private EqualityType equalityType;
    private BigDecimal balance;
}
