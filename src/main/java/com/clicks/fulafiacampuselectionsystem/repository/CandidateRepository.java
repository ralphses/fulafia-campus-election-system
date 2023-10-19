package com.clicks.fulafiacampuselectionsystem.repository;

import com.clicks.fulafiacampuselectionsystem.model.Candidate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CandidateRepository extends JpaRepository<Candidate, Long> {
    boolean existsByUserNameAndElectionTitle(String userName, String electionTitle);
    long countByElection_Id(Long id);

    @Query(value = "SELECT candidate FROM Candidate candidate WHERE candidate.election.id = ?1 AND candidate.ussdCode = ?2")
    Optional<Candidate> findByElectionAndUssdCode(Long election, Long ussdCode);
}
