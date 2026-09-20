package com.focusforge.repository;

import com.focusforge.entity.RoomMember;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface RoomMemberRepository extends JpaRepository<RoomMember, Long> {

    List<RoomMember> findByRoomIdAndLeftAtIsNullOrderByJoinedAtAsc(Long roomId);

    List<RoomMember> findByRoomCodeAndLeftAtIsNullOrderByJoinedAtAsc(String roomCode);

    Optional<RoomMember> findByRoomIdAndUserIdAndLeftAtIsNull(Long roomId, Long userId);

    Optional<RoomMember> findByUserIdAndLeftAtIsNull(Long userId);

    Optional<RoomMember> findByRoomCodeAndUserIdAndLeftAtIsNull(String roomCode, Long userId);

    long countByRoomIdAndLeftAtIsNull(Long roomId);

    @Query("SELECT rm FROM RoomMember rm WHERE rm.leftAt IS NULL AND rm.lastHeartbeatAt < :threshold")
    List<RoomMember> findStaleHeartbeatMembers(@Param("threshold") LocalDateTime threshold);
}
