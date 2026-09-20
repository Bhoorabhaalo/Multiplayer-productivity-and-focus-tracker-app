package com.focusforge.websocket;

import com.focusforge.dto.request.SendChatMessageRequest;
import com.focusforge.dto.request.UpdateSessionStateRequest;
import com.focusforge.dto.response.ChatMessageResponse;
import com.focusforge.entity.RoomMember;
import com.focusforge.mapper.EntityMapper;
import com.focusforge.repository.RoomMemberRepository;
import com.focusforge.security.UserPrincipal;
import com.focusforge.service.RoomService;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;

import java.time.LocalDateTime;

@Controller
public class StompRoomController {
    private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(StompRoomController.class);

    public StompRoomController(RoomService roomService, RoomMemberRepository roomMemberRepository, RoomEventPublisher eventPublisher) {
        this.roomService = roomService;
        this.roomMemberRepository = roomMemberRepository;
        this.eventPublisher = eventPublisher;
    }



    private final RoomService roomService;
    private final RoomMemberRepository roomMemberRepository;
    private final RoomEventPublisher eventPublisher;

    @MessageMapping("/room/{code}/state")
    public void handleStateUpdate(@DestinationVariable String code,
                                  @Payload UpdateSessionStateRequest request,
                                  Authentication authentication) {
        if (authentication == null || !(authentication.getPrincipal() instanceof UserPrincipal principal)) {
            return;
        }

        roomMemberRepository.findByRoomCodeAndUserIdAndLeftAtIsNull(code.toUpperCase(), principal.getId())
                .ifPresent(member -> {
                    member.setState(request.getState());
                    if (request.getActivity() != null) {
                        member.setCurrentActivity(request.getActivity().trim());
                    }
                    member.setLastHeartbeatAt(LocalDateTime.now());
                    roomMemberRepository.save(member);

                    eventPublisher.publishToRoom(code.toUpperCase(), WsEventType.MEMBER_STATE_UPDATED,
                            EntityMapper.toRoomMemberResponse(member, null));
                });
    }

    @MessageMapping("/room/{code}/chat")
    public void handleChatMessage(@DestinationVariable String code,
                                 @Payload SendChatMessageRequest request,
                                 Authentication authentication) {
        if (authentication == null || !(authentication.getPrincipal() instanceof UserPrincipal principal)) {
            return;
        }

        ChatMessageResponse response = roomService.sendChatMessage(
                principal.getId(), code.toUpperCase(), request.getContent());
        log.debug("Chat message broadcast via STOMP: {}", response.getId());
    }

    @MessageMapping("/room/{code}/heartbeat")
    public void handleHeartbeat(@DestinationVariable String code, Authentication authentication) {
        if (authentication == null || !(authentication.getPrincipal() instanceof UserPrincipal principal)) {
            return;
        }

        roomMemberRepository.findByRoomCodeAndUserIdAndLeftAtIsNull(code.toUpperCase(), principal.getId())
                .ifPresent(member -> {
                    member.setLastHeartbeatAt(LocalDateTime.now());
                    if (member.getState() == RoomMember.MemberState.IDLE) {
                        member.setState(RoomMember.MemberState.FOCUS);
                    }
                    roomMemberRepository.save(member);
                });
    }
}
