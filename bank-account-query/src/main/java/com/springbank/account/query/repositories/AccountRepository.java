package com.springbank.account.query.repositories;

import com.springbank.account.core.models.BankAccount;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountRepository extends JpaRepository<BankAccount, String> {

    Optional<BankAccount> findByAccountHolderId(String holderId);
    List<BankAccount> findByBalanceGreaterThan(BigDecimal balance);
    List<BankAccount> findByBalanceLessThan(BigDecimal balance);

}
