package com.focusforge.scheduler;

import com.focusforge.entity.Room;
import com.focusforge.entity.RoomMember;
import com.focusforge.mapper.EntityMapper;
import com.focusforge.repository.RoomMemberRepository;
import com.focusforge.repository.RoomRepository;
import com.focusforge.websocket.RoomEventPublisher;
import com.focusforge.websocket.WsEventType;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class RoomCleanupScheduler {
    private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(RoomCleanupScheduler.class);

    public RoomCleanupScheduler(RoomRepository roomRepository, RoomMemberRepository roomMemberRepository, RoomEventPublisher eventPublisher) {
        this.roomRepository = roomRepository;
        this.roomMemberRepository = roomMemberRepository;
        this.eventPublisher = eventPublisher;
    }



    private final RoomRepository roomRepository;
    private final RoomMemberRepository roomMemberRepository;
    private final RoomEventPublisher eventPublisher;

    @Scheduled(fixedRate = 30000) // Every 30s
    @Transactional
    public void cleanupInactiveRoomsAndMembers() {
        LocalDateTime now = LocalDateTime.now();

        // 1. Members with no heartbeat for 90s marked IDLE; for 10 min, auto-removed
        LocalDateTime idleThreshold = now.minusSeconds(90);
        LocalDateTime removeThreshold = now.minusMinutes(10);

        List<RoomMember> staleMembers = roomMemberRepository.findStaleHeartbeatMembers(idleThreshold);
        for (RoomMember m : staleMembers) {
            if (m.getLastHeartbeatAt() != null && m.getLastHeartbeatAt().isBefore(removeThreshold)) {
                log.info("Auto-removing member {} from room {} due to 10m inactivity",
                        m.getUser().getUsername(), m.getRoom().getCode());
                m.setLeftAt(now);
                roomMemberRepository.save(m);

                Map<String, Object> payload = new HashMap<>();
                payload.put("userId", m.getUser().getId());
                payload.put("newHostId", null);
                eventPublisher.publishToRoom(m.getRoom().getCode(), WsEventType.MEMBER_LEFT, payload);
            } else if (m.getState() != RoomMember.MemberState.IDLE) {
                m.setState(RoomMember.MemberState.IDLE);
                roomMemberRepository.save(m);

                eventPublisher.publishToRoom(m.getRoom().getCode(), WsEventType.MEMBER_STATE_UPDATED,
                        EntityMapper.toRoomMemberResponse(m, null));
            }
        }

        // 2. End active rooms that have had 0 active members for 30 minutes
        List<Room> activeRooms = roomRepository.findByStatus(Room.Status.ACTIVE);
        for (Room r : activeRooms) {
            long count = roomMemberRepository.countByRoomIdAndLeftAtIsNull(r.getId());
            if (count == 0 && r.getUpdatedAt() != null && r.getUpdatedAt().isBefore(now.minusMinutes(30))) {
                log.info("Auto-closing empty room {}", r.getCode());
                r.setStatus(Room.Status.ENDED);
                r.setPhase(Room.Phase.COMPLETED);
                roomRepository.save(r);
            }
        }
    }
}
