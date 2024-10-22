/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ctu.demo.dto;

import ctu.demo.model.User;
import ctu.demo.model.User.Gender;
import java.util.Base64;
import java.util.Date;

/**
 *
 * @author ADMIN
 */
public class UserDTO {
    private Long id;
    private String fullname;
    private Date birth;
    private String address;
    private Gender gender;
    private String base64Image; 
    private String detail;
    private User.UserStatus status;
    private String groupName;
    
   public static UserDTO convertToDto(User user) {
    UserDTO dto = new UserDTO();
    dto.setId(user.getId());
    dto.setFullname(user.getFullname());
    dto.setBirth(user.getBirth());
    dto.setAddress(user.getAddress());
    dto.setGender(user.getGender());
    dto.setDetail(user.getDetail());
    dto.setStatus(user.getStatus());
    dto.setGroupName(user.getGroup()!=null ? user.getGroup().getName(): "Không có nhóm");
    // Chuyển đổi avatar sang base64
    if (user.getAvatar() != null) {
        dto.setBase64Image(encodeImageToBase64(user.getAvatar()));  // Gọi trực tiếp phương thức encodeImageToBase64
    }

    return dto;
}
public static String encodeImageToBase64(byte[] imageBytes) {
    return Base64.getEncoder().encodeToString(imageBytes);
}
    public Long getId() {
        return id;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public User.UserStatus getStatus() {
        return status;
    }

    public void setStatus(User.UserStatus status) {
        this.status = status;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public Date getBirth() {
        return birth;
    }

    public void setBirth(Date birth) {
        this.birth = birth;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public String getBase64Image() {
        return base64Image;
    }

    public void setBase64Image(String base64Image) {
        this.base64Image = base64Image;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }
    
    
}
