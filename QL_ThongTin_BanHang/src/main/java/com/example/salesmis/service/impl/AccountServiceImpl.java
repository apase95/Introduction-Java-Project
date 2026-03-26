package com.example.salesmis.service.impl;

import com.example.salesmis.dao.AccountDAO;
import com.example.salesmis.model.entity.Account;
import com.example.salesmis.model.enumtype.AccountRole;
import com.example.salesmis.service.AccountService;

import java.util.Optional;

public class AccountServiceImpl implements AccountService {
    
    private final AccountDAO accountDAO;

    public AccountServiceImpl(AccountDAO accountDAO) {
        this.accountDAO = accountDAO;
    }

    @Override
    public Account login(String username, String password) {
        Optional<Account> accOpt = accountDAO.findByUsername(username);
        if (accOpt.isEmpty()) {
            throw new IllegalArgumentException("Tài khoản không tồn tại.");
        }
        
        Account acc = accOpt.get();
        if (!acc.getPassword().equals(password)) {
            throw new IllegalArgumentException("Mật khẩu không chính xác.");
        }
        
        return acc;
    }

    @Override
    public Account createAccount(String username, String password, AccountRole role) {
        if (accountDAO.findByUsername(username).isPresent()) {
            throw new IllegalArgumentException("Tên đăng nhập đã tồn tại.");
        }
        Account acc = new Account(username, password, role);
        return accountDAO.save(acc);
    }

    @Override
    public void ensureDefaultAccountsExist() {
        if (accountDAO.findByUsername("admin").isEmpty()) {
            createAccount("admin", "123456", AccountRole.ADMIN);
        }
        if (accountDAO.findByUsername("staff").isEmpty()) {
            createAccount("staff", "123456", AccountRole.STAFF);
        }
    }
}
