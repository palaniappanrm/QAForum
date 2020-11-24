package com.eceplatform.QAForum.model;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "USER_TOKEN")
public class UserToken {

    @Id
    @Column(name = "USER_TOKEN_ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "TOKEN", nullable = false)
    private String token;

    @ManyToOne
    @JoinColumn(name = "USER_ID", referencedColumnName = "USER_ID", nullable = false, foreignKey = @ForeignKey(name = "FK_USER_TOKEN_USER"))
    private User user;

    @Column(name = "CREATED_AT", nullable = false)
    private LocalDateTime createdAt = LocalDateTime.now();

    @Column(name = "VALID_TILL", nullable = false)
    private LocalDateTime validTill = LocalDateTime.now().plusDays(1);

    public UserToken() {
    }

    public UserToken(User user) {
        this.user = user;
        this.token = UUID.randomUUID().toString();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getValidTill() {
        return validTill;
    }

    public void setValidTill(LocalDateTime validTill) {
        this.validTill = validTill;
    }
}
