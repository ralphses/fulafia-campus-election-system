package com.clicks.fulafiacampuselectionsystem.repository;

import com.clicks.fulafiacampuselectionsystem.model.ElectionResult;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ElectionResultRepository extends JpaRepository<ElectionResult, Long> {
    Optional<ElectionResult> findByElection_Id(Long electionId);
}
