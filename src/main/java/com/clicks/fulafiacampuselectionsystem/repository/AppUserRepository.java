package com.clicks.fulafiacampuselectionsystem.repository;

import com.clicks.fulafiacampuselectionsystem.model.AppUser;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AppUserRepository extends JpaRepository<AppUser, Long> {

    @Query(value = "SELECT user FROM AppUser user WHERE user.phone = ?1")
    Optional<AppUser> findByPhone(String phone);

    boolean existsByEmailOrPhone(String email, String phone);

    @Query(value = "SELECT user FROM AppUser user WHERE user.role = ?1")
    Page<AppUser> findByRole(String role, Pageable pageable);
}
