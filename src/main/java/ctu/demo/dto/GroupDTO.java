package ctu.demo.dto;

import ctu.demo.model.Group;
import ctu.demo.model.User;
import java.util.Base64;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class GroupDTO {
    private Long id;
    private String name;
    private String description;
    private Map<String, Object> leader = new HashMap<>();
    private List<Map<String, Object>> listUser;

    public static GroupDTO convertToDto(Group group) {
        GroupDTO dto = new GroupDTO();
        dto.setId(group.getId());
        dto.setName(group.getName());
        dto.setDescription(group.getDescription());

        dto.getLeader().put("leaderId", group.getLeader().getId());
        dto.getLeader().put("leaderName", group.getLeader().getFullname());
        dto.getLeader().put("leaderAvatar", 
            group.getLeader().getAvatar() != null ? encodeImageToBase64(group.getLeader().getAvatar()) : null
        );

        dto.setListUser(
            group.getUsers() // Assuming Group has a method getUsers() that returns List<User>
                .stream()
                .map(user -> {
                    Map<String, Object> userMap = new HashMap<>();
                    userMap.put("userId", user.getId());
                    userMap.put("userFullname", user.getFullname());
                    userMap.put("userAvatar", 
                        user.getAvatar() != null ? encodeImageToBase64(user.getAvatar()) : null
                    );
                    return userMap;
                })
                .collect(Collectors.toList()) // Collect the user maps into a list
        );

        return dto;
    }
    public static String encodeImageToBase64(byte[] imageBytes) {
        return Base64.getEncoder().encodeToString(imageBytes);
    }

    // Getters and Setters
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

    public Map<String, Object> getLeader() {
        return leader;
    }

    public void setLeader(Map<String, Object> leader) {
        this.leader = leader;
    }

    public List<Map<String, Object>> getListUser() {
        return listUser;
    }

    public void setListUser(List<Map<String, Object>> listUser) {
        this.listUser = listUser;
    }
}
