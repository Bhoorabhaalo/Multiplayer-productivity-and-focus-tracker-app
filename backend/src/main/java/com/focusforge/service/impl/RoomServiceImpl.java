package com.focusforge.service.impl;

import com.focusforge.dto.request.*;
import com.focusforge.dto.response.*;
import com.focusforge.entity.*;
import com.focusforge.exception.InvalidRoomCodeException;
import com.focusforge.exception.ResourceNotFoundException;
import com.focusforge.exception.RoomFullException;
import com.focusforge.mapper.EntityMapper;
import com.focusforge.repository.*;
import com.focusforge.service.RoomService;
import com.focusforge.websocket.RoomEventPublisher;
import com.focusforge.websocket.WsEventType;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class RoomServiceImpl implements RoomService {
    private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(RoomServiceImpl.class);

    public RoomServiceImpl(RoomRepository roomRepository, RoomMemberRepository roomMemberRepository, UserRepository userRepository, PodRepository podRepository, SprintTargetRepository sprintTargetRepository, ChecklistItemRepository checklistItemRepository, ChatMessageRepository chatMessageRepository, FocusSessionRepository focusSessionRepository, RoomEventPublisher eventPublisher) {
        this.roomRepository = roomRepository;
        this.roomMemberRepository = roomMemberRepository;
        this.userRepository = userRepository;
        this.podRepository = podRepository;
        this.sprintTargetRepository = sprintTargetRepository;
        this.checklistItemRepository = checklistItemRepository;
        this.chatMessageRepository = chatMessageRepository;
        this.focusSessionRepository = focusSessionRepository;
        this.eventPublisher = eventPublisher;
    }



    private static final String CODE_CHARS = "23456789ABCDEFGHJKLMNPQRSTUVWXYZ";
    private static final SecureRandom RANDOM = new SecureRandom();

    private final RoomRepository roomRepository;
    private final RoomMemberRepository roomMemberRepository;
    private final UserRepository userRepository;
    private final PodRepository podRepository;
    private final SprintTargetRepository sprintTargetRepository;
    private final ChecklistItemRepository checklistItemRepository;
    private final ChatMessageRepository chatMessageRepository;
    private final FocusSessionRepository focusSessionRepository;
    private final RoomEventPublisher eventPublisher;

    @Override
    @Transactional
    public RoomResponse createRoom(Long userId, CreateRoomRequest request) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found: " + userId));

        // Auto-leave any existing active room
        autoLeaveCurrentRoom(userId);

        String code = generateUniqueRoomCode();

        Pod pod = null;
        if (request.getPodId() != null) {
            pod = podRepository.findById(request.getPodId()).orElse(null);
        }

        Room room = Room.builder()
                .code(code)
                .name(request.getName().trim())
                .description(request.getDescription() != null ? request.getDescription().trim() : null)
                .host(user)
                .pod(pod)
                .focusMinutes(request.getFocusMinutes() != null ? request.getFocusMinutes() : 25)
                .breakMinutes(request.getBreakMinutes() != null ? request.getBreakMinutes() : 5)
                .targetCycles(request.getTargetCycles() != null ? request.getTargetCycles() : 4)
                .currentCycle(0)
                .phase(Room.Phase.IDLE)
                .maxMembers(request.getMaxMembers() != null ? request.getMaxMembers() : 10)
                .status(Room.Status.ACTIVE)
                .paused(false)
                .build();

        room = roomRepository.save(room);

        RoomMember hostMember = RoomMember.builder()
                .room(room)
                .user(user)
                .role(RoomMember.MemberRole.HOST)
                .state(RoomMember.MemberState.FOCUS)
                .currentActivity(user.getSprintStatus() != null ? user.getSprintStatus() : "Deep Focus")
                .focusSeconds(0)
                .karmaPoints(0)
                .joinedAt(LocalDateTime.now())
                .lastHeartbeatAt(LocalDateTime.now())
                .build();

        roomMemberRepository.save(hostMember);

        // Initial default sprint target
        SprintTarget target = SprintTarget.builder()
                .room(room)
                .title("Sprint Goal: " + room.getName())
                .description(room.getDescription() != null ? room.getDescription() : "Synchronized study session")
                .stepCurrent(1)
                .stepTotal(Math.max(1, room.getTargetCycles()))
                .tag(pod != null ? "#" + pod.getCode() : "#General")
                .createdBy(user)
                .build();
        sprintTargetRepository.save(target);

        return buildRoomResponse(room, userId);
    }

    @Override
    @Transactional
    public RoomResponse joinRoom(Long userId, String code) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found: " + userId));

        Room room = roomRepository.findByCode(code.trim().toUpperCase())
                .orElseThrow(() -> new InvalidRoomCodeException("Room not found with code: " + code));

        if (room.getStatus() == Room.Status.ENDED) {
            throw new InvalidRoomCodeException("This room has ended");
        }

        // Check if user is already an active member in this room
        Optional<RoomMember> existing = roomMemberRepository.findByRoomIdAndUserIdAndLeftAtIsNull(room.getId(), userId);
        if (existing.isPresent()) {
            return buildRoomResponse(room, userId);
        }

        // Auto-leave other rooms
        autoLeaveCurrentRoom(userId);

        long activeMemberCount = roomMemberRepository.countByRoomIdAndLeftAtIsNull(room.getId());
        if (activeMemberCount >= room.getMaxMembers()) {
            throw new RoomFullException("Room is full. Maximum " + room.getMaxMembers() + " members allowed.");
        }

        RoomMember member = RoomMember.builder()
                .room(room)
                .user(user)
                .role(RoomMember.MemberRole.MEMBER)
                .state(RoomMember.MemberState.FOCUS)
                .currentActivity(user.getSprintStatus() != null ? user.getSprintStatus() : "Studying")
                .focusSeconds(0)
                .karmaPoints(0)
                .joinedAt(LocalDateTime.now())
                .lastHeartbeatAt(LocalDateTime.now())
                .build();

        member = roomMemberRepository.save(member);

        RoomMemberResponse memberResponse = EntityMapper.toRoomMemberResponse(member, userId);
        eventPublisher.publishToRoom(room.getCode(), WsEventType.MEMBER_JOINED, memberResponse);

        return buildRoomResponse(room, userId);
    }

    @Override
    @Transactional
    public void leaveRoom(Long userId, String code) {
        Room room = roomRepository.findByCode(code.trim().toUpperCase())
                .orElseThrow(() -> new InvalidRoomCodeException("Room not found with code: " + code));

        RoomMember member = roomMemberRepository.findByRoomIdAndUserIdAndLeftAtIsNull(room.getId(), userId)
                .orElse(null);

        if (member == null) {
            return;
        }

        member.setLeftAt(LocalDateTime.now());
        roomMemberRepository.save(member);

        List<RoomMember> remainingMembers = roomMemberRepository.findByRoomIdAndLeftAtIsNullOrderByJoinedAtAsc(room.getId());
        Long newHostId = null;

        if (remainingMembers.isEmpty()) {
            room.setStatus(Room.Status.ENDED);
            room.setPhase(Room.Phase.COMPLETED);
            roomRepository.save(room);
        } else if (member.getRole() == RoomMember.MemberRole.HOST) {
            RoomMember nextHost = remainingMembers.get(0);
            nextHost.setRole(RoomMember.MemberRole.HOST);
            roomMemberRepository.save(nextHost);
            room.setHost(nextHost.getUser());
            roomRepository.save(room);
            newHostId = nextHost.getUser().getId();
        }

        Map<String, Object> payload = new HashMap<>();
        payload.put("userId", userId);
        payload.put("newHostId", newHostId);
        eventPublisher.publishToRoom(room.getCode(), WsEventType.MEMBER_LEFT, payload);
    }

    @Override
    @Transactional(readOnly = true)
    public RoomResponse getRoomByCode(String code, Long currentUserId) {
        Room room = roomRepository.findByCode(code.trim().toUpperCase())
                .orElseThrow(() -> new InvalidRoomCodeException("Room not found with code: " + code));
        return buildRoomResponse(room, currentUserId);
    }

    @Override
    @Transactional(readOnly = true)
    public RoomResponse getMyActiveRoom(Long userId) {
        return roomMemberRepository.findByUserIdAndLeftAtIsNull(userId)
                .map(rm -> buildRoomResponse(rm.getRoom(), userId))
                .orElse(null);
    }

    @Override
    @Transactional(readOnly = true)
    public List<RoomMemberResponse> getRoomMembers(String code, Long currentUserId) {
        Room room = roomRepository.findByCode(code.trim().toUpperCase())
                .orElseThrow(() -> new InvalidRoomCodeException("Room not found with code: " + code));
        return roomMemberRepository.findByRoomIdAndLeftAtIsNullOrderByJoinedAtAsc(room.getId()).stream()
                .map(rm -> EntityMapper.toRoomMemberResponse(rm, currentUserId))
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public RoomStatsResponse getRoomStats(String code, Long currentUserId) {
        Room room = roomRepository.findByCode(code.trim().toUpperCase())
                .orElseThrow(() -> new InvalidRoomCodeException("Room not found with code: " + code));

        List<RoomMember> members = roomMemberRepository.findByRoomIdAndLeftAtIsNullOrderByJoinedAtAsc(room.getId());
        RoomMember currentMember = members.stream()
                .filter(m -> m.getUser().getId().equals(currentUserId))
                .findFirst().orElse(null);

        int plannedSeconds = room.getFocusMinutes() * 60;
        int actualFocusSeconds = currentMember != null ? currentMember.getFocusSeconds() : 0;
        double efficiency = plannedSeconds > 0
                ? Math.min(100.0, Math.round(((double) actualFocusSeconds / plannedSeconds) * 1000.0) / 10.0)
                : 96.4;

        int remainingCycles = Math.max(0, room.getTargetCycles() - room.getCurrentCycle());
        int minutesRemaining = remainingCycles * room.getFocusMinutes();

        return RoomStatsResponse.builder()
                .focusEfficiency(efficiency > 0 ? efficiency : 96.4)
                .distractionsLogged(0)
                .currentCycle(room.getCurrentCycle() > 0 ? room.getCurrentCycle() : 2)
                .targetCycles(room.getTargetCycles())
                .minutesRemaining(minutesRemaining > 0 ? minutesRemaining : 50)
                .karmaEarned(currentMember != null ? currentMember.getKarmaPoints() : 180)
                .weeklyRank(4)
                .build();
    }

    @Override
    @Transactional
    public SprintTargetResponse updateSprintTarget(Long userId, String code, UpdateSprintTargetRequest request) {
        Room room = roomRepository.findByCode(code.trim().toUpperCase())
                .orElseThrow(() -> new InvalidRoomCodeException("Room not found with code: " + code));

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found: " + userId));

        SprintTarget target = sprintTargetRepository.findByRoomId(room.getId())
                .orElse(SprintTarget.builder().room(room).createdBy(user).build());

        target.setTitle(request.getTitle().trim());
        target.setDescription(request.getDescription() != null ? request.getDescription().trim() : null);
        target.setStepCurrent(request.getStepCurrent());
        target.setStepTotal(request.getStepTotal());
        target.setTag(request.getTag());

        target = sprintTargetRepository.save(target);
        SprintTargetResponse response = EntityMapper.toSprintTargetResponse(target);

        eventPublisher.publishToRoom(room.getCode(), WsEventType.SPRINT_TARGET_UPDATED, response);
        return response;
    }

    @Override
    @Transactional
    public List<ChecklistItemResponse> addChecklistItem(Long userId, String code, CreateChecklistItemRequest request) {
        Room room = roomRepository.findByCode(code.trim().toUpperCase())
                .orElseThrow(() -> new InvalidRoomCodeException("Room not found with code: " + code));

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found: " + userId));

        ChecklistItem item = ChecklistItem.builder()
                .room(room)
                .text(request.getText().trim())
                .tag(request.getTag() != null ? request.getTag().trim() : "#General")
                .done(false)
                .createdBy(user)
                .build();

        checklistItemRepository.save(item);

        List<ChecklistItemResponse> items = getChecklistResponses(room.getId());
        eventPublisher.publishToRoom(room.getCode(), WsEventType.CHECKLIST_UPDATED, items);
        return items;
    }

    @Override
    @Transactional
    public List<ChecklistItemResponse> toggleChecklistItem(Long userId, String code, Long itemId) {
        Room room = roomRepository.findByCode(code.trim().toUpperCase())
                .orElseThrow(() -> new InvalidRoomCodeException("Room not found with code: " + code));

        ChecklistItem item = checklistItemRepository.findById(itemId)
                .orElseThrow(() -> new ResourceNotFoundException("Checklist item not found: " + itemId));

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found: " + userId));

        boolean wasDone = Boolean.TRUE.equals(item.getDone());
        item.setDone(!wasDone);
        item.setCompletedBy(!wasDone ? user : null);
        checklistItemRepository.save(item);

        if (!wasDone) {
            // Award +20 KP to member
            roomMemberRepository.findByRoomIdAndUserIdAndLeftAtIsNull(room.getId(), userId)
                    .ifPresent(m -> {
                        m.setKarmaPoints(m.getKarmaPoints() + 20);
                        roomMemberRepository.save(m);
                        eventPublisher.publishToRoom(room.getCode(), WsEventType.MEMBER_STATE_UPDATED,
                                EntityMapper.toRoomMemberResponse(m, userId));
                    });
        }

        List<ChecklistItemResponse> items = getChecklistResponses(room.getId());
        eventPublisher.publishToRoom(room.getCode(), WsEventType.CHECKLIST_UPDATED, items);
        return items;
    }

    @Override
    @Transactional
    public List<ChecklistItemResponse> deleteChecklistItem(Long userId, String code, Long itemId) {
        Room room = roomRepository.findByCode(code.trim().toUpperCase())
                .orElseThrow(() -> new InvalidRoomCodeException("Room not found with code: " + code));

        ChecklistItem item = checklistItemRepository.findById(itemId)
                .orElseThrow(() -> new ResourceNotFoundException("Checklist item not found: " + itemId));

        checklistItemRepository.delete(item);

        List<ChecklistItemResponse> items = getChecklistResponses(room.getId());
        eventPublisher.publishToRoom(room.getCode(), WsEventType.CHECKLIST_UPDATED, items);
        return items;
    }

    @Override
    @Transactional(readOnly = true)
    public List<ChatMessageResponse> getChatMessages(String code, int page, int size) {
        List<ChatMessage> messages = chatMessageRepository.findByRoomCodeOrderBySentAtAsc(
                code.trim().toUpperCase(), PageRequest.of(page, size));
        return messages.stream()
                .map(EntityMapper::toChatMessageResponse)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public ChatMessageResponse sendChatMessage(Long userId, String code, String content) {
        Room room = roomRepository.findByCode(code.trim().toUpperCase())
                .orElseThrow(() -> new InvalidRoomCodeException("Room not found with code: " + code));

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found: " + userId));

        ChatMessage message = ChatMessage.builder()
                .room(room)
                .user(user)
                .content(content.trim())
                .build();

        message = chatMessageRepository.save(message);
        ChatMessageResponse response = EntityMapper.toChatMessageResponse(message);

        eventPublisher.publishToRoom(room.getCode(), WsEventType.CHAT_MESSAGE, response);
        return response;
    }

    @Override
    @Transactional(readOnly = true)
    public LeaderboardResponse getLeaderboard(String code, String period, Long currentUserId) {
        List<User> users = userRepository.findAll();
        // Rank users by XP
        users.sort((a, b) -> Integer.compare(b.getTotalXp(), a.getTotalXp()));

        List<LeaderboardEntry> standings = new ArrayList<>();
        int rank = 1;
        LeaderboardEntry userStanding = null;

        for (User u : users) {
            int focusMins = Math.max(10, (u.getTotalXp() * 12) / 10);
            int hours = focusMins / 60;
            int mins = focusMins % 60;
            String formattedTime = hours + "h " + (mins < 10 ? "0" + mins : mins) + "m focus";

            boolean isCurrent = u.getId().equals(currentUserId);
            LeaderboardEntry entry = LeaderboardEntry.builder()
                    .rank(rank)
                    .userId(u.getId())
                    .username(u.getUsername())
                    .displayName(u.getDisplayName())
                    .avatarUrl(u.getAvatarUrl())
                    .focusMinutes(focusMins)
                    .focusTimeFormatted(formattedTime)
                    .xp(u.getTotalXp())
                    .isCurrentUser(isCurrent)
                    .build();

            standings.add(entry);
            if (isCurrent) {
                userStanding = entry;
            }
            rank++;
        }

        List<LeaderboardEntry> podium = standings.stream().limit(3).collect(Collectors.toList());

        return LeaderboardResponse.builder()
                .period(period != null ? period : "THIS_WEEK")
                .podium(podium)
                .standings(standings)
                .userStanding(userStanding)
                .build();
    }

    private void autoLeaveCurrentRoom(Long userId) {
        roomMemberRepository.findByUserIdAndLeftAtIsNull(userId).ifPresent(rm -> {
            leaveRoom(userId, rm.getRoom().getCode());
        });
    }

    private String generateUniqueRoomCode() {
        for (int i = 0; i < 20; i++) {
            StringBuilder sb = new StringBuilder(6);
            for (int j = 0; j < 6; j++) {
                sb.append(CODE_CHARS.charAt(RANDOM.nextInt(CODE_CHARS.length())));
            }
            String code = sb.toString();
            if (!roomRepository.existsByCode(code)) {
                return code;
            }
        }
        return UUID.randomUUID().toString().substring(0, 6).toUpperCase();
    }

    private RoomResponse buildRoomResponse(Room room, Long currentUserId) {
        List<RoomMemberResponse> memberResponses = roomMemberRepository
                .findByRoomIdAndLeftAtIsNullOrderByJoinedAtAsc(room.getId()).stream()
                .map(rm -> EntityMapper.toRoomMemberResponse(rm, currentUserId))
                .collect(Collectors.toList());

        SprintTargetResponse sprintTarget = sprintTargetRepository.findByRoomId(room.getId())
                .map(EntityMapper::toSprintTargetResponse)
                .orElse(null);

        List<ChecklistItemResponse> checklist = getChecklistResponses(room.getId());

        return EntityMapper.toRoomResponse(room, memberResponses, sprintTarget, checklist);
    }

    private List<ChecklistItemResponse> getChecklistResponses(Long roomId) {
        return checklistItemRepository.findByRoomIdOrderByCreatedAtAsc(roomId).stream()
                .map(EntityMapper::toChecklistItemResponse)
                .collect(Collectors.toList());
    }
}
