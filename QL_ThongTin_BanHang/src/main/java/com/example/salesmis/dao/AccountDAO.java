package com.example.salesmis.dao;

import com.example.salesmis.model.entity.Account;
import java.util.Optional;

public interface AccountDAO {
    /** Tìm tài khoản theo tên đăng nhập. */
    Optional<Account> findByUsername(String username);
    /** Lưu hoặc cập nhật tài khoản vào CSDL. */
    Account save(Account account);
    /** Đếm tổng số tài khoản đang có trong hệ thống. */
    long countAccounts();
}
