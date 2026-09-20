package com.focusforge.repository;

import com.focusforge.entity.SprintTarget;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SprintTargetRepository extends JpaRepository<SprintTarget, Long> {
    Optional<SprintTarget> findByRoomId(Long roomId);
    Optional<SprintTarget> findByRoomCode(String roomCode);
}
