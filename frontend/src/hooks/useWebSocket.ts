import { useEffect, useRef } from 'react';
import { Client } from '@stomp/stompjs';
import SockJS from 'sockjs-client';
import { useRoomStore } from '../stores/roomStore';
import type { WsEnvelope, RoomMember, ChatMessage, SprintTarget, ChecklistItem, Room } from '../types';

export function useWebSocket(roomCode: string | undefined) {
  const clientRef = useRef<Client | null>(null);
  const {
    setConnected,
    updateRoom,
    upsertMember,
    removeMember,
    updateMemberState,
    addChatMessage,
    setSprintTarget,
    setChecklist,
  } = useRoomStore();

  useEffect(() => {
    if (!roomCode) return;
    const token = localStorage.getItem('accessToken');

    const client = new Client({
      webSocketFactory: () => new SockJS('/ws'),
      connectHeaders: token ? { Authorization: `Bearer ${token}` } : {},
      debug: () => {},
      reconnectDelay: 4000,
      heartbeatIncoming: 4000,
      heartbeatOutgoing: 4000,
      onConnect: () => {
        setConnected(true);
        // Subscribe to room topic
        client.subscribe(`/topic/room/${roomCode}`, (message) => {
          try {
            const envelope: WsEnvelope<any> = JSON.parse(message.body);
            const { eventType, payload } = envelope;

            switch (eventType) {
              case 'MEMBER_JOINED':
                upsertMember(payload as RoomMember);
                break;
              case 'MEMBER_LEFT':
                if (payload?.userId) removeMember(payload.userId);
                break;
              case 'MEMBER_STATE_UPDATED':
                if (payload?.userId && payload?.focusState) {
                  updateMemberState(payload.userId, payload.focusState);
                }
                break;
              case 'TIMER_UPDATED':
              case 'PHASE_CHANGED':
                updateRoom(payload as Partial<Room>);
                break;
              case 'SPRINT_TARGET_UPDATED':
                setSprintTarget(payload as SprintTarget);
                break;
              case 'CHECKLIST_UPDATED':
                setChecklist(payload as ChecklistItem[]);
                break;
              case 'CHAT_MESSAGE':
                addChatMessage(payload as ChatMessage);
                break;
              default:
                break;
            }
          } catch (e) {
            console.error('Failed to parse WS envelope', e);
          }
        });
      },
      onDisconnect: () => {
        setConnected(false);
      },
      onStompError: (frame) => {
        console.error('STOMP error', frame);
        setConnected(false);
      },
    });

    client.activate();
    clientRef.current = client;

    return () => {
      client.deactivate();
      setConnected(false);
    };
  }, [roomCode, setConnected, updateRoom, upsertMember, removeMember, updateMemberState, addChatMessage, setSprintTarget, setChecklist]);

  return clientRef.current;
}
