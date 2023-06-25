package com.springbanking.user.query.userquery.handlers;

import com.springbank.user.core.usercore.models.User;
import com.springbanking.user.query.userquery.dto.UserLookupResponse;
import com.springbanking.user.query.userquery.queries.FindAllUsersQuery;
import com.springbanking.user.query.userquery.queries.FindUserByIdQuery;
import com.springbanking.user.query.userquery.queries.SearchUsersQuery;
import com.springbanking.user.query.userquery.rerpositories.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.queryhandling.QueryHandler;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Service
public class UserQueryHandlerImpl implements UserQueryHandler {


    private final UserRepository userRepository;

    @QueryHandler
    @Override
    public UserLookupResponse getUerById(FindUserByIdQuery query) {
        log.info("Find user by id {}", query.getId());
        return new UserLookupResponse(userRepository.findById(query.getId()).orElse(null));
    }

    @QueryHandler
    @Override
    public UserLookupResponse searchUsers(SearchUsersQuery query) {
        log.info("Search users by criteria {}", query.getFilter());
        return new UserLookupResponse(userRepository.findByFilterRegex(query.getFilter()));
    }

    @QueryHandler
    @Override
    public UserLookupResponse getAllUsers(FindAllUsersQuery query) {
        log.info("Find all users");
        return new UserLookupResponse(userRepository.findAll());
    }
}
