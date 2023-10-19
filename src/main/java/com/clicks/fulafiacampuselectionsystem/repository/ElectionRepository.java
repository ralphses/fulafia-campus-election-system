package com.clicks.fulafiacampuselectionsystem.repository;

import com.clicks.fulafiacampuselectionsystem.model.Election;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ElectionRepository extends JpaRepository<Election, Long> {

    boolean existsByTitleAndOwnerName(String title, String ownerName);

    @Query(value = "SELECT election FROM Election election WHERE election.owner.id = ?1")
    Page<Election> findByOwner(Long owner, Pageable pageable);

    Optional<Election> findByUssdCode(Long ussdCode);
}
