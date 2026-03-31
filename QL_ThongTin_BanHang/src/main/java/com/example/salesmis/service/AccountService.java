package com.example.salesmis.service;

import com.example.salesmis.model.entity.Account;
import com.example.salesmis.model.enumtype.AccountRole;

public interface AccountService {
    /** Xác thực đăng nhập và trả về tài khoản nếu hợp lệ. */
    Account login(String username, String password);
    /** Tạo tài khoản mới với vai trò chỉ định. */
    Account createAccount(String username, String password, AccountRole role);
    /** Kiểm tra và tạo tài khoản mặc định (admin/staff) nếu chưa tồn tại. */
    void ensureDefaultAccountsExist();
}
