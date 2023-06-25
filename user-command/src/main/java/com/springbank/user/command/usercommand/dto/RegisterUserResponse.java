package com.springbank.user.command.usercommand.dto;

import com.springbank.user.core.usercore.dto.BaseResponse;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@AllArgsConstructor
@NoArgsConstructor
@Data
@SuperBuilder
public class RegisterUserResponse extends BaseResponse {

    private String id;
}
