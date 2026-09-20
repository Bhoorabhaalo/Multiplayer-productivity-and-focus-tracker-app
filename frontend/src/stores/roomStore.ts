import { create } from 'zustand';
import type { Room, RoomMember, ChatMessage, SprintTarget, ChecklistItem } from '../types';

interface RoomStats {
  totalFocusMinutes: number;
  totalBreakMinutes: number;
  averageFocusMinutes: number;
  efficiencyPercent: number;
  totalKarmaPoints: number;
  activeMemberCount: number;
}

interface RoomState {
  room: Room | null;
  members: RoomMember[];
  chatMessages: ChatMessage[];
  sprintTarget: SprintTarget | null;
  checklist: ChecklistItem[];
  stats: RoomStats | null;
  isConnected: boolean;

  setRoom: (room: Room | null) => void;
  updateRoom: (partial: Partial<Room>) => void;
  setMembers: (members: RoomMember[]) => void;
  upsertMember: (member: RoomMember) => void;
  removeMember: (userId: number) => void;
  updateMemberState: (userId: number, focusState: RoomMember['focusState']) => void;
  setChatMessages: (messages: ChatMessage[]) => void;
  addChatMessage: (msg: ChatMessage) => void;
  setSprintTarget: (target: SprintTarget | null) => void;
  setChecklist: (items: ChecklistItem[]) => void;
  setStats: (stats: RoomStats | null) => void;
  setConnected: (connected: boolean) => void;
  reset: () => void;
}

export const useRoomStore = create<RoomState>((set) => ({
  room: null,
  members: [],
  chatMessages: [],
  sprintTarget: null,
  checklist: [],
  stats: null,
  isConnected: false,

  setRoom: (room) => set({
    room,
    members: room?.members || [],
    sprintTarget: room?.sprintTarget || null,
    checklist: room?.checklistItems || [],
  }),

  updateRoom: (partial) => set((state) => ({
    room: state.room ? { ...state.room, ...partial } : null,
  })),

  setMembers: (members) => set({ members }),

  upsertMember: (member) => set((state) => {
    const exists = state.members.some((m) => m.userId === member.userId);
    const updated = exists
      ? state.members.map((m) => (m.userId === member.userId ? member : m))
      : [...state.members, member];
    return { members: updated };
  }),

  removeMember: (userId) => set((state) => ({
    members: state.members.filter((m) => m.userId !== userId),
  })),

  updateMemberState: (userId, focusState) => set((state) => ({
    members: state.members.map((m) => (m.userId === userId ? { ...m, focusState } : m)),
  })),

  setChatMessages: (chatMessages) => set({ chatMessages }),

  addChatMessage: (msg) => set((state) => ({
    chatMessages: [...state.chatMessages, msg],
  })),

  setSprintTarget: (sprintTarget) => set({ sprintTarget }),

  setChecklist: (checklist) => set({ checklist }),

  setStats: (stats) => set({ stats }),

  setConnected: (isConnected) => set({ isConnected }),

  reset: () => set({
    room: null,
    members: [],
    chatMessages: [],
    sprintTarget: null,
    checklist: [],
    stats: null,
    isConnected: false,
  }),
}));
