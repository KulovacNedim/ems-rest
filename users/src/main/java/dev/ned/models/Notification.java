package dev.ned.models;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;
import java.util.*;

@Entity
public class Notification {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.DETACH, CascadeType.REFRESH})
    @LazyCollection(LazyCollectionOption.FALSE)
    @JoinTable(name = "notifications_roles",
            joinColumns = @JoinColumn(name = "notification_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private List<Role> rolesToNotify = new ArrayList<>();

    @ManyToOne(cascade = {
            CascadeType.PERSIST, CascadeType.MERGE,
            CascadeType.DETACH, CascadeType.REFRESH})
    @JoinColumn(name = "user_id")
    private User userToNotify;

    private String message;
    private NotificationType type;

    @ElementCollection
    @MapKeyColumn(name = "name")
    @Column(name = "value")
    @CollectionTable(name = "notification_args", joinColumns = @JoinColumn(name = "arg_id"))
    Map<String, String> args = new HashMap<>();

    private String taskCreatorName;
    private Date createdAt;
    private Date resolvedAt;

    public Notification() {
    }

    public void addArg(String name, String value) {
        this.args.put(name, value);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<Role> getRolesToNotify() {
        return rolesToNotify;
    }

    public void setRolesToNotify(List<Role> rolesToNotify) {
        this.rolesToNotify = rolesToNotify;
    }

    public User getUserToNotify() {
        return userToNotify;
    }

    public void setUserToNotify(User userToNotify) {
        this.userToNotify = userToNotify;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public NotificationType getType() {
        return type;
    }

    public void setType(NotificationType type) {
        this.type = type;
    }

    public Map<String, String> getArgs() {
        return args;
    }

    public void setArgs(Map<String, String> args) {
        this.args = args;
    }

    public String getTaskCreatorName() {
        return taskCreatorName;
    }

    public void setTaskCreatorName(String taskCreatorName) {
        this.taskCreatorName = taskCreatorName;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Date getResolvedAt() {
        return resolvedAt;
    }

    public void setResolvedAt(Date resolvedAt) {
        this.resolvedAt = resolvedAt;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Notification that = (Notification) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(rolesToNotify, that.rolesToNotify) &&
                Objects.equals(userToNotify, that.userToNotify) &&
                Objects.equals(message, that.message) &&
                type == that.type &&
                Objects.equals(args, that.args) &&
                Objects.equals(taskCreatorName, that.taskCreatorName) &&
                Objects.equals(createdAt, that.createdAt) &&
                Objects.equals(resolvedAt, that.resolvedAt);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, rolesToNotify, userToNotify, message, type, args, taskCreatorName, createdAt, resolvedAt);
    }
}
