package com.focusforge.repository;

import com.focusforge.entity.Room;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RoomRepository extends JpaRepository<Room, Long> {
    Optional<Room> findByCode(String code);
    List<Room> findByStatus(Room.Status status);
    List<Room> findByPhase(Room.Phase phase);
    boolean existsByCode(String code);
}
