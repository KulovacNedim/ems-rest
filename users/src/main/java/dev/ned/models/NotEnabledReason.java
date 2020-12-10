package dev.ned.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.Objects;

@Entity
public class NotEnabledReason {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private UserNotEnabledReasons reason;

    @Column(nullable = false)
    private Date timestamp;

    private boolean valid;

    @ManyToOne(cascade = {
            CascadeType.PERSIST, CascadeType.MERGE,
            CascadeType.DETACH, CascadeType.REFRESH})
    @JoinColumn(name = "user_id")
    @JsonIgnoreProperties(value = "notEnabledReasons", allowSetters = true)
    @NotNull
    private User user;

    public NotEnabledReason() {
    }

    public NotEnabledReason(UserNotEnabledReasons reason, Date timestamp, @NotNull User user, boolean valid) {
        this.reason = reason;
        this.timestamp = timestamp;
        this.user = user;
        this.valid = valid;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public UserNotEnabledReasons getReason() {
        return reason;
    }

    public void setReason(UserNotEnabledReasons reason) {
        this.reason = reason;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public boolean isValid() {
        return valid;
    }

    public void setValid(boolean valid) {
        this.valid = valid;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        NotEnabledReason that = (NotEnabledReason) o;
        return valid == that.valid &&
                Objects.equals(id, that.id) &&
                reason == that.reason &&
                Objects.equals(timestamp, that.timestamp) &&
                Objects.equals(user, that.user);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, reason, timestamp, valid, user);
    }
}
