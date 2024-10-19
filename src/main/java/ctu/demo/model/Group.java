package ctu.demo.model;

import ctu.demo.model.User;
import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "groups")
public class Group implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    @NotNull(message = "Group name must not be null.")
    @NotEmpty(message = "Group name must not be empty.")
    private String name;

     @Column(nullable = true)
    private String description;
    
    @OneToMany(mappedBy = "group", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<User> users = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "leader_id") // FK for the leader
    private User leader;

    public Group() {
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

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }

    public User getLeader() {
        return leader;
    }

    public void setLeader(User leader) {
        this.leader = leader;
    }
public void addUser(User user) {
    if (!users.contains(user)) {
        users.add(user);
        user.setGroup(this); // Set the group in the user
    }
}

public void removeUser(User user) {
    if (users.contains(user)) {
        users.remove(user);
        user.setGroup(null); // Clear the group in the user
    }
}

    @Override
    public String toString() {
        return "Group{" + "id=" + id + ", name=" + name + ", description=" + description + ", users=" + users + ", leader=" + leader + '}';
    }

}
