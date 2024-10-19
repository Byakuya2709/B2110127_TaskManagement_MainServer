/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ctu.demo.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.io.Serializable;
import java.util.Collection;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;

/**
 *
 * @author ADMIN
 */
@Entity
@Table(name="account")
public class Account implements Serializable,UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String password;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role;

    @OneToOne(mappedBy = "account", cascade = CascadeType.ALL,fetch = FetchType.LAZY)
//    @JsonIgnore
    private User user;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        // Trả về quyền hạn dưới dạng danh sách
        return AuthorityUtils.createAuthorityList("ROLE_" + this.getRole());
    }
    @Override
public String getUsername() {
    return this.email; // hoặc bất kỳ thuộc tính nào khác dùng làm tên đăng nhập
}

@Override
public boolean isAccountNonExpired() {
    return true; // Thay đổi nếu bạn có logic kiểm tra ngày hết hạn
}

@Override
public boolean isAccountNonLocked() {
    return true; // Thay đổi nếu bạn có logic kiểm tra khóa tài khoản
}

@Override
public boolean isCredentialsNonExpired() {
    return true; // Thay đổi nếu bạn có logic kiểm tra ngày hết hạn mật khẩu
}

@Override
public boolean isEnabled() {
    return true; // Thay đổi nếu bạn có logic kiểm tra kích hoạt tài khoản
}

    // getters and setters

    public enum Role {
        CUSTOMER,
        MANAGER,
        ADMIN
    }

    public Account() {
    }

    public Account(String email, String password, User user) {
        this.email = email;
        this.password = password;
        this.user = user;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public void setUser(User user) {
        this.user = user;
    }

    
    public Long getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public Role getRole() {
        return role;
    }

    public User getUser() {
        return user;
    }
    
    
}
