package com.clicks.fulafiacampuselectionsystem.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Candidate {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne
    private ElectionPosition position;

    @OneToOne
    private AppUser user;
    private Integer voteCount;
    private long ussdCode;

    @ManyToOne
    private Election election;
}
