package com.clicks.fulafiacampuselectionsystem.model;

import com.clicks.fulafiacampuselectionsystem.enums.TransactionPurpose;
import com.clicks.fulafiacampuselectionsystem.enums.TransactionStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import static jakarta.persistence.EnumType.STRING;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne
    private Client client;

    private String reference;

    @Enumerated(STRING)
    private TransactionPurpose purpose;

    @Enumerated(STRING)
    private TransactionStatus status;

    @ManyToOne
    private AppUser user;

    private Double amount;


}
