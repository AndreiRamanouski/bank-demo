package com.springbank.account.core.events;

import java.math.BigDecimal;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class FundsWithdrawnEvent {

    private String id;
    private BigDecimal amount;
    private BigDecimal balance;
}
