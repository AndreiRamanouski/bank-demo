package com.springbanking.user.query.userquery.controllers;

import com.springbanking.user.query.userquery.dto.UserLookupResponse;
import com.springbanking.user.query.userquery.queries.FindAllUsersQuery;
import com.springbanking.user.query.userquery.queries.FindUserByIdQuery;
import com.springbanking.user.query.userquery.queries.SearchUsersQuery;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.messaging.responsetypes.ResponseTypes;
import org.axonframework.queryhandling.QueryGateway;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/user-lookup")
@Slf4j
@RequiredArgsConstructor
public class UserLookupController {

    private final QueryGateway queryGateway;

    @GetMapping
    public ResponseEntity<UserLookupResponse> getAllUsers() {
        UserLookupResponse returnedValue = queryGateway.query(new FindAllUsersQuery(),
                ResponseTypes.instanceOf(UserLookupResponse.class)).join();
        return ResponseEntity.ok().body(returnedValue);
    }

    @GetMapping("{id}")
    public ResponseEntity<UserLookupResponse> getById(@PathVariable String id) {
        return ResponseEntity.ok()
                .body(queryGateway.query(new FindUserByIdQuery(id), ResponseTypes.instanceOf(UserLookupResponse.class))
                        .join());
    }

    @GetMapping("filter/{filter}")
    public ResponseEntity<UserLookupResponse> getByFilter(@PathVariable String filter) {
        UserLookupResponse returnedValue = queryGateway.query(new SearchUsersQuery(filter),
                ResponseTypes.instanceOf(UserLookupResponse.class)).join();
        if (returnedValue.getUsers().isEmpty()) {
            returnedValue.setMessage("Not users were found!");
        } else {
            returnedValue.setMessage(String.format("Retrieved %s users", returnedValue.getUsers().size()));
        }
        return ResponseEntity.ok().body(returnedValue);
    }
}
