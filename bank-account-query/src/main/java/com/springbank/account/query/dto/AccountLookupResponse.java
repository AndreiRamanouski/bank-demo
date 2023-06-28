package com.springbank.account.query.dto;

import com.springbank.account.core.dto.BaseResponse;
import com.springbank.account.core.models.BankAccount;
import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.SuperBuilder;

@Data
@AllArgsConstructor
@SuperBuilder
public class AccountLookupResponse extends BaseResponse {

    @Builder.Default
    private List<BankAccount> accounts = new ArrayList<>();

    public AccountLookupResponse(BankAccount bankAccount, String message){
        super(message);
        accounts.add(bankAccount);
    }
}
