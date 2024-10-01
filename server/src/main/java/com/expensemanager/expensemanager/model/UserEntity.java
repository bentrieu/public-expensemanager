package com.expensemanager.expensemanager.model;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "app_user")
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String name;
    private float asset;
    private String profileImage;
    private String email;
    private String password;
    private Date dateCreated;
    private Date dateUpdated;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Transaction> transactions;

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(name = "user_roles",
            joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "role_id", referencedColumnName = "id"))
    private List<Role> roles = new ArrayList<>();

    @PrePersist
    public void prePersist() {
        this.setDateCreated(new Date());
        this.setDateUpdated(new Date());
    }

    @PreUpdate
    public void preUpdate() {
        this.setDateUpdated(new Date());
    }

    public UserEntity(String email, String password) {
        this.name = "Your Name";
        this.asset = 0;
        this.profileImage = "image-link";
        this.email = email;
        this.password = password;
    }
}

