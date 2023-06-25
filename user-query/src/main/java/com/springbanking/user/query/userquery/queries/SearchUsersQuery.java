package com.springbanking.user.query.userquery.queries;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class SearchUsersQuery {

    private String filter;
}
