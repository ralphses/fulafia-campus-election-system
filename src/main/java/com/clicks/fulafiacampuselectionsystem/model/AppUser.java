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

    @OneToMany
    private List<Client> ownedClients;

    @Enumerated(EnumType.STRING)
    private UserRole role;

    @ManyToMany(mappedBy = "members", fetch = FetchType.LAZY, cascade = CascadeType.DETACH)
    private List<Client> clients;

    @OneToMany
    private List<Transaction> transactions;
}
