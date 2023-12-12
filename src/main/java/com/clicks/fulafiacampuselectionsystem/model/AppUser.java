package com.clicks.fulafiacampuselectionsystem.model;

import com.clicks.fulafiacampuselectionsystem.enums.UserRole;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AppUser {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String name;
    private String identity;
    private String email;
    private String phone;


    @Enumerated(EnumType.STRING)
    private UserRole role;

    @OneToMany
    private List<Transaction> transactions;
}
