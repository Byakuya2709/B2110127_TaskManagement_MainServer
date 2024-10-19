/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package ctu.demo.repository;

import ctu.demo.model.Account;
import ctu.demo.model.Group;
import ctu.demo.model.Task;
import ctu.demo.model.User;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 *
 * @author ADMIN
 */
@Repository
public interface UserRepository extends JpaRepository<User, Long> {
     Optional<User> findByAccountId(Long accountId);
     @Query("SELECT u FROM User u WHERE u.account.role <> :role")
    List<User> findUserNotHasRoleAdmin(@Param("role") Account.Role role);
     @Query("SELECT u FROM User u WHERE u.account.role = :role")
    List<User> findUserHasRoleAdmin(@Param("role") Account.Role role);
    //đếm số lượng nhân viên còn hoạt động
    long countByStatus(User.UserStatus status);
    List<User> findByGroup(Group group);
}
