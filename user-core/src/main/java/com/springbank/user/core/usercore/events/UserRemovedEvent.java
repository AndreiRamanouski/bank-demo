package com.springbank.user.core.usercore.events;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserRemovedEvent {

    private String id;
}
