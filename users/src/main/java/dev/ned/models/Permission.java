package dev.ned.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@JsonIgnoreProperties(value = "users", allowSetters = true)
public class Permission {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long permission_id;

    @Column(nullable = false)
    private String permissionName;

    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.DETACH, CascadeType.REFRESH})
    @JoinTable(name = "user_permissions",
            joinColumns = @JoinColumn(name = "permission_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id"))
    private List<User> users = new ArrayList<>();

    public Permission() {
    }

    public Permission(String permissionName) {
        this.permissionName = permissionName;
    }

    // adding User to permissions
    public void addUser(User user) {
        if (users == null) {
            users = new ArrayList<>();
        }
        user.getPermissions().add(this);
        users.add(user);
    }

    public Long getPermission_id() {
        return permission_id;
    }

    public void setPermission_id(Long permission_id) {
        this.permission_id = permission_id;
    }

    public String getPermissionName() {
        return permissionName;
    }

    public void setPermissionName(String permissionName) {
        this.permissionName = permissionName;
    }

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        users.forEach(user -> user.getPermissions().add(this));
        this.users = users;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Permission that = (Permission) o;
        return Objects.equals(permission_id, that.permission_id) &&
                Objects.equals(permissionName, that.permissionName) &&
                Objects.equals(users, that.users);
    }

    @Override
    public int hashCode() {
        return Objects.hash(permission_id, permissionName, users);
    }
}
