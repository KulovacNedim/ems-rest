package dev.ned.config.models;

import dev.ned.models.User;

import javax.persistence.*;
import java.util.Date;

@Entity
public class EmailConfirm {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @OneToOne
    @MapsId
    private User user;
    @Column(nullable = false)
    private String randomString;
    @Column(nullable = false)
    private Date expirationDate;

    public EmailConfirm() {
    }

    public EmailConfirm(User user, String randomString, Date expirationDate) {
        this.user = user;
        this.randomString = randomString;
        this.expirationDate = expirationDate;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getRandomString() {
        return randomString;
    }

    public void setRandomString(String randomString) {
        this.randomString = randomString;
    }

    public Date getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(Date expirationDate) {
        this.expirationDate = expirationDate;
    }
}
