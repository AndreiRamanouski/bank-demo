package com.springbank.account.core.events;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AccountClosedEvent {

    private String id;
}
