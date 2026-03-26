package com.example.salesmis.service;

import com.example.salesmis.model.entity.Account;
import com.example.salesmis.model.enumtype.AccountRole;

public interface AccountService {
    Account login(String username, String password);
    Account createAccount(String username, String password, AccountRole role);
    void ensureDefaultAccountsExist();
}
