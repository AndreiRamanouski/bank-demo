package com.springbank.user.core.usercore.events;

import com.springbank.user.core.usercore.models.User;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserRegisteredEvent {

    private String id;
    private User user;
}
