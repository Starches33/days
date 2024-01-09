package com.clever.days.repository;

import com.clever.days.entity.UserEO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<UserEO, UUID> {

    Optional<UserEO> findByTgId(long tgId);
}