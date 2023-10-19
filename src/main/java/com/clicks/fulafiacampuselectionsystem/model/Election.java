package com.clicks.fulafiacampuselectionsystem.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Election {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String title;
    private LocalDateTime createTime;
    private LocalTime startTime;
    private LocalTime stopTime;
    private LocalDate startDate;
    private LocalDate stopDate;

    @ManyToOne
    private Client owner;
    private long ussdCode;

    @OneToMany
    private List<Candidate> candidates;

}
