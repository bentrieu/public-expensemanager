package com.expensemanager.expensemanager.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private double amount;
    private String name;
    private String category;
    private String source;
    private Date dateCreated;
    private Date dateUpdated;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserEntity user;

    @PrePersist
    public void prePersist() {
        this.setDateCreated(new Date());
        this.setDateUpdated(new Date());
    }

    @PreUpdate
    public void preUpdate() {
        this.setDateUpdated(new Date());
    }
}
