package com.focusforge.repository;

import com.focusforge.entity.ChatMessage;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ChatMessageRepository extends JpaRepository<ChatMessage, Long> {
    List<ChatMessage> findByRoomCodeOrderBySentAtAsc(String roomCode, Pageable pageable);
    List<ChatMessage> findByRoomIdOrderBySentAtAsc(Long roomId, Pageable pageable);
}
