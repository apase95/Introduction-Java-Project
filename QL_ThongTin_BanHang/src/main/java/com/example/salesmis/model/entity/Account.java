package com.example.salesmis.model.entity;

import com.example.salesmis.model.enumtype.AccountRole;
import jakarta.persistence.*;

import java.util.Objects;

@Entity
@Table(name = "accounts")
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, length = 50)
    private String username;

    @Column(nullable = false, length = 100)
    private String password;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private AccountRole role = AccountRole.STAFF;

    /** Constructor mặc định không tham số, JPA yêu cầu. */
    public Account() {}

    /** Constructor tạo tài khoản với đầy đủ thông tin. */
    public Account(String username, String password, AccountRole role) {
        this.username = username;
        this.password = password;
        this.role = role;
    }

    public Long getId() { return id; }
    public String getUsername() { return username; }
    public String getPassword() { return password; }
    public AccountRole getRole() { return role; }

    public void setId(Long id) { this.id = id; }
    public void setUsername(String username) { this.username = username; }
    public void setPassword(String password) { this.password = password; }
    public void setRole(AccountRole role) { this.role = role; }

    /** So sánh hai tài khoản dựa trên ID. */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Account that)) return false;
        return id != null && Objects.equals(id, that.id);
    }

    /** Trả về hash code của lớp, đảm bảo đồng nhất với equals. */
    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
