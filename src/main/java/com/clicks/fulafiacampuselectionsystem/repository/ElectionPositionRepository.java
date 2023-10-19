package com.clicks.fulafiacampuselectionsystem.repository;

import com.clicks.fulafiacampuselectionsystem.model.ElectionPosition;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ElectionPositionRepository extends JpaRepository<ElectionPosition, Long> {

    boolean existsByTitleAndOwnerId(String title, Long owner);

    long countByOwner_Id(Long id);

    Optional<ElectionPosition> findByOwner_IdAndUssdCode(Long ownerId, Long ussdCode);
}
