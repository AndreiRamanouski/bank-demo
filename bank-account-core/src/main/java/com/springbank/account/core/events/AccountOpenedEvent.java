package com.springbank.account.core.events;

import com.springbank.account.core.models.AccountType;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AccountOpenedEvent {

    private String id;
    private String accountHolderId;
    private AccountType accountType;
    private LocalDateTime createdAt;
    private BigDecimal openingBalance;
}
