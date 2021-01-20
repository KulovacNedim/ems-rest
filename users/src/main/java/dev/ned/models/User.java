package dev.ned.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import dev.ned.models.app_users.Address;
import dev.ned.models.app_users.Phone;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@JsonIgnoreProperties(value = "notificationDTO", allowSetters = true)
public class User {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String firstName;

    @Column(nullable = false)
    private String lastName;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private boolean isEnabled;

    @Column(nullable = false)
    private boolean isLocked;

    @OneToMany(mappedBy = "user", cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.DETACH, CascadeType.REFRESH})
    List<NotEnabledReason> notEnabledReasons;

    @ManyToMany(mappedBy = "users", cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.DETACH, CascadeType.REFRESH})
    private List<Role> roles = new ArrayList<>();

    @ManyToMany(mappedBy = "users", cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.DETACH, CascadeType.REFRESH})
    private List<Permission> permissions = new ArrayList<>();

    @OneToMany(mappedBy = "userToNotify", cascade = {
            CascadeType.PERSIST, CascadeType.MERGE,
            CascadeType.DETACH, CascadeType.REFRESH
    })
    private List<Notification> notification;

    @Column(nullable = false)
    private String ucrn; //Unified Citizens' Registration Number

    @Column(nullable = false)
    private Date dob;

    @OneToOne @JoinColumn(name = "address_id")
    private Address address;

    @OneToMany(mappedBy = "user", cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.DETACH, CascadeType.REFRESH})
    private List<Phone> phones;

    public User() {
    }

    // adding Role to User's roles
    public void addRole(Role role) {
        if (roles == null) {
            roles = new ArrayList<>();
        }
        role.getUsers().add(this);
        roles.add(role);
    }

    // adding Permission to User's permissions
    public void addPermission(Permission permission) {
        if (permissions == null) {
            permissions = new ArrayList<>();
        }
        permission.getUsers().add(this);
        permissions.add(permission);
    }

    // adding Phone to User's phones
    public void addPhone(Phone phone) {
        if (phones == null) {
            phones = new ArrayList<>();
        }
        phone.setUser(this);
        phones.add(phone);
    }

    // adding Notification to User's Notifications
    public void addNotification(Notification notification) {
        if (this.notification == null) {
            this.notification = new ArrayList<>();
        }
        notification.setUserToNotify(this);
        this.notification.add(notification);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isEnabled() {
        return isEnabled;
    }

    public void setEnabled(boolean enabled) {
        isEnabled = enabled;
    }

    public boolean isLocked() {
        return isLocked;
    }

    public void setLocked(boolean locked) {
        isLocked = locked;
    }

    public List<NotEnabledReason> getNotEnabledReasons() {
        return notEnabledReasons;
    }

    public void setNotEnabledReasons(List<NotEnabledReason> notEnabledReasons) {
        this.notEnabledReasons = notEnabledReasons;
    }

    public List<Role> getRoles() {
        return roles;
    }

    public void setRoles(List<Role> roles) {
        roles.forEach(role -> role.getUsers().add(this));
        this.roles = roles;
    }

    public List<Permission> getPermissions() {
        return permissions;
    }

    public void setPermissions(List<Permission> permissions) {
        permissions.forEach(permission -> permission.getUsers().add(this));
        this.permissions = permissions;
    }

    public List<Notification> getNotification() {
        return notification;
    }

    public void setNotification(List<Notification> notification) {
        this.notification = notification;
    }

    public String getUcrn() {
        return ucrn;
    }

    public void setUcrn(String ucrn) {
        this.ucrn = ucrn;
    }

    public Date getDob() {
        return dob;
    }

    public void setDob(Date dob) {
        this.dob = dob;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public List<Phone> getPhones() {
        return phones;
    }

    public void setPhones(List<Phone> phones) {
        this.phones = phones;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return isEnabled == user.isEnabled &&
                isLocked == user.isLocked &&
                Objects.equals(id, user.id) &&
                Objects.equals(firstName, user.firstName) &&
                Objects.equals(lastName, user.lastName) &&
                Objects.equals(email, user.email) &&
                Objects.equals(password, user.password) &&
                Objects.equals(notEnabledReasons, user.notEnabledReasons) &&
                Objects.equals(roles, user.roles) &&
                Objects.equals(permissions, user.permissions) &&
                Objects.equals(notification, user.notification) &&
                Objects.equals(ucrn, user.ucrn) &&
                Objects.equals(dob, user.dob) &&
                Objects.equals(address, user.address) &&
                Objects.equals(phones, user.phones);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, firstName, lastName, email, password, isEnabled, isLocked, notEnabledReasons, roles, permissions, notification, ucrn, dob, address, phones);
    }
}