package com.springbanking.user.query.userquery.handlers;

import com.springbanking.user.query.userquery.dto.UserLookupResponse;
import com.springbanking.user.query.userquery.queries.FindAllUsersQuery;
import com.springbanking.user.query.userquery.queries.FindUserByIdQuery;
import com.springbanking.user.query.userquery.queries.SearchUsersQuery;

public interface UserQueryHandler {

    UserLookupResponse getUerById(FindUserByIdQuery query);
    UserLookupResponse searchUsers(SearchUsersQuery query);
    UserLookupResponse getAllUsers(FindAllUsersQuery query);
}
