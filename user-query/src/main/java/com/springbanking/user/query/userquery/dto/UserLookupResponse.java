package com.springbanking.user.query.userquery.dto;

import com.springbank.user.core.usercore.dto.BaseResponse;
import com.springbank.user.core.usercore.models.User;
import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.experimental.SuperBuilder;

@Data
@AllArgsConstructor
@SuperBuilder
public class UserLookupResponse extends BaseResponse {

    private List<User> users;

    public UserLookupResponse(User user){
        users = new ArrayList<>();
        users.add(user);
    }
}
