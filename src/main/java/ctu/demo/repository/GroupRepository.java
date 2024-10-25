/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ctu.demo.repository;

import ctu.demo.model.Group;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 *
 * @author ADMIN
 */
@Repository
public interface GroupRepository extends JpaRepository<Group, Long> {

    Group findByName(String name);

    @Query("SELECT g FROM Group g JOIN g.users u WHERE u.id = :userId")
    Group findByUserId(@Param("userId") Long userId);
}
