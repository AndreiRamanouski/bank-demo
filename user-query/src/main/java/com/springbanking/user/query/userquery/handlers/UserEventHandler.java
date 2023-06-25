package com.springbanking.user.query.userquery.handlers;

import com.springbank.user.core.usercore.events.UserRegisteredEvent;
import com.springbank.user.core.usercore.events.UserRemovedEvent;
import com.springbank.user.core.usercore.events.UserUpdateEvent;

public interface UserEventHandler {
    void on(UserRegisteredEvent userRegisteredEvent);
    void on(UserUpdateEvent userUpdateEvent);
    void on(UserRemovedEvent userRemovedEvent);

}
