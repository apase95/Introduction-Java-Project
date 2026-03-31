package com.example.salesmis.service.impl;

import com.example.salesmis.dao.AccountDAO;
import com.example.salesmis.model.entity.Account;
import com.example.salesmis.model.enumtype.AccountRole;
import com.example.salesmis.service.AccountService;

import java.util.Optional;

public class AccountServiceImpl implements AccountService {
    
    private final AccountDAO accountDAO;

    /** Constructor nhận AccountDAO từ bên ngoài (dependency injection thủ công). */
    public AccountServiceImpl(AccountDAO accountDAO) {
        this.accountDAO = accountDAO;
    }

    /** Xác thực tên đăng nhập và mật khẩu; ném ngoại lệ nếu sai. */
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

    /** Tạo tài khoản mới; ném ngoại lệ nếu tên đăng nhập đã tồn tại. */
    @Override
    public Account createAccount(String username, String password, AccountRole role) {
        if (accountDAO.findByUsername(username).isPresent()) {
            throw new IllegalArgumentException("Tên đăng nhập đã tồn tại.");
        }
        Account acc = new Account(username, password, role);
        return accountDAO.save(acc);
    }

    /** Tạo tài khoản admin và staff mặc định khi khởi động lần đầu. */
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
