package com.example.salesmis.dao;

import com.example.salesmis.model.entity.Account;
import java.util.Optional;

public interface AccountDAO {
    Optional<Account> findByUsername(String username);
    Account save(Account account);
    long countAccounts();
}
