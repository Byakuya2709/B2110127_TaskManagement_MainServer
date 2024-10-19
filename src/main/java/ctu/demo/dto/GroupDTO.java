/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ctu.demo.dto;

import ctu.demo.model.Group;
import ctu.demo.model.User;
import java.util.List;
import java.util.stream.Collectors;

/**
 *
 * @author ADMIN
 */
public class GroupDTO {
   private Long id;
   private String name;
   private String description;
   private User leader;
   private List<Long> usersId;
   public static GroupDTO convertToDto(Group group){
    GroupDTO dto = new GroupDTO();
        dto.setId(group.getId());
        dto.setName(group.getName());
        dto.setDescription(group.getDescription());
        dto.setLeader(group.getLeader());
        dto.setUsersId(
            group.getUsers() // Giả sử Group có phương thức getUsers() trả về List<User>
                .stream()
                .map(User::getId) // Lấy ID của từng User
                .collect(Collectors.toList()) // Thu thập thành danh sách các ID
        );
         return dto;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public User getLeader() {
        return leader;
    }

    public void setLeader(User leader) {
        this.leader = leader;
    }

    public List<Long> getUsersId() {
        return usersId;
    }

    public void setUsersId(List<Long> usersId) {
        this.usersId = usersId;
    }
   
}
