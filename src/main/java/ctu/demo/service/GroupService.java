package ctu.demo.service;

import ctu.demo.model.Group;
import ctu.demo.model.User;
import ctu.demo.repository.GroupRepository;
import ctu.demo.request.GroupRequest;
import ctu.demo.request.UpdateGroupRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class GroupService {

    @Autowired
    private GroupRepository groupRepository; // Injecting the repository
    @Autowired
    private UserService userService;

    public Group findById(Long id) {
        return groupRepository.findById(id).orElse(null);
    }

    // Method to create a new group
    public Group createGroup(GroupRequest request) {
        // Kiểm tra xem tên nhóm đã tồn tại chưa
        if (groupRepository.findByName(request.getName()) != null) {
            throw new IllegalArgumentException("Group with name " + request.getName() + " already exists.");
        }

        Group group = new Group();
        group.setName(request.getName());
        group.setDescription(request.getDescription());

        // Tìm người dùng theo ID
        User user = userService.getUserById((long) request.getUserId());
        if (user == null) {
            throw new RuntimeException("User with ID " + request.getUserId() + " does not exist.");
        }

        // Kiểm tra xem người dùng đã thuộc nhóm nào chưa
        if (user.getGroup() != null) {
            throw new RuntimeException("User with ID " + request.getUserId() + " already belongs to a group.");
        }
        
        
        group.setLeader(user);
        user.setGroup(group);
        group.getUsers().add(user);
        
        return groupRepository.save(group);
    }

    // Method to find a group by name
    public Optional<Group> findGroupByName(String name) {
        return Optional.ofNullable(groupRepository.findByName(name)); // Find and return the group if exists
    }

    // Method to get a group by ID
    public Optional<Group> findGroupById(Long id) {
        return groupRepository.findById(id); // Find group by ID
    }

    // Method to get all groups
    public List<Group> getAllGroups() {
        return groupRepository.findAll(); // Retrieve all groups
    }

    // Method to update a group
    public Group updateGroup(UpdateGroupRequest req) {
        // Retrieve the group from the repository
    Optional<Group> optionalGroup = groupRepository.findById((long)req.getGroupId());
    if (optionalGroup.isPresent()) {
        Group group = optionalGroup.get();

        // Update the group's description if provided
        if (req.getDescription() != null) {
            group.setDescription(req.getDescription());
        }

        // Retrieve the user by ID
        User user = userService.getUserById((long)req.getUserId());
        if (user != null) { // Ensure the user exists
            user.setGroup(group); // Set the group for the user
            group.getUsers().add(user); // Add the user to the group's list
        } else {
            throw new IllegalArgumentException("User not found with ID: " + req.getUserId());
        }

        // Save the updated group and return
        return groupRepository.save(group);
    } else {
        throw new IllegalArgumentException("Group not found with ID: " + req.getGroupId());
    }
    }

    // Method to delete a group by ID
    public void deleteGroup(Long id) {
        if (!groupRepository.existsById(id)) {
            throw new IllegalArgumentException("Group not found with ID: " + id); // Check if group exists
        }
        groupRepository.deleteById(id); // Deletes the group by ID
    }
}
