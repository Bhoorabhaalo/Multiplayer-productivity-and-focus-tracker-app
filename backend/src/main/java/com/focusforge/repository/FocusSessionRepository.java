package com.focusforge.repository;

import com.focusforge.entity.FocusSession;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface FocusSessionRepository extends JpaRepository<FocusSession, Long> {

    Page<FocusSession> findByUserIdOrderByStartedAtDesc(Long userId, Pageable pageable);

    List<FocusSession> findByUserIdAndStartedAtBetweenOrderByStartedAtAsc(Long userId, LocalDateTime start, LocalDateTime end);

    @Query("SELECT fs FROM FocusSession fs WHERE fs.room.code = :roomCode AND fs.startedAt >= :start AND fs.startedAt < :end")
    List<FocusSession> findByRoomCodeAndDateRange(@Param("roomCode") String roomCode,
                                                 @Param("start") LocalDateTime start,
                                                 @Param("end") LocalDateTime end);

    @Query("SELECT fs FROM FocusSession fs WHERE fs.user.id IN :userIds AND fs.startedAt >= :start AND fs.startedAt < :end")
    List<FocusSession> findByUserIdsAndDateRange(@Param("userIds") List<Long> userIds,
                                                @Param("start") LocalDateTime start,
                                                @Param("end") LocalDateTime end);

    long countByUserIdAndCompletedTrue(Long userId);
}
