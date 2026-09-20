// API types matching backend DTOs
export interface User {
  id: number;
  email: string;
  username: string;
  displayName: string;
  avatarUrl?: string;
  university?: string;
  major?: string;
  focusStatement?: string;
  level: number;
  totalXp: number;
  xpForNextLevel: number;
  xpProgressInLevel: number;
  streakDays: number;
  role: string;
  interfaceDensity: 'COMPACT' | 'BALANCED' | 'SPACIOUS';
  soundEffectsEnabled: boolean;
  createdAt: string;
}

export interface AuthResponse {
  accessToken: string;
  refreshToken: string;
  tokenType: string;
  user: User;
}

export interface Room {
  id: number;
  code: string;
  name: string;
  description?: string;
  hostId: number;
  hostName: string;
  podId?: number;
  podName?: string;
  focusMinutes: number;
  breakMinutes: number;
  targetCycles: number;
  currentCycle: number;
  phase: 'IDLE' | 'FOCUS' | 'BREAK' | 'COMPLETED';
  phaseStartedAt?: string;
  phaseEndsAt?: string;
  maxMembers: number;
  status: 'ACTIVE' | 'ENDED';
  paused: boolean;
  remainingSecondsWhenPaused?: number;
  memberCount: number;
  members: RoomMember[];
  sprintTarget?: SprintTarget;
  checklistItems: ChecklistItem[];
  createdAt: string;
}

export interface RoomMember {
  id: number;
  userId: number;
  displayName: string;
  username: string;
  avatarUrl?: string;
  level: number;
  focusState: 'FOCUSING' | 'ON_BREAK' | 'IDLE' | 'AWAY';
  joinedAt: string;
  lastHeartbeatAt: string;
}

export interface SprintTarget {
  id: number;
  roomId: number;
  title: string;
  description?: string;
  stepCurrent: number;
  stepTotal: number;
  tag?: string;
}

export interface ChecklistItem {
  id: number;
  content: string;
  completed: boolean;
  createdAt: string;
}

export interface Task {
  id: number;
  userId: number;
  title: string;
  description?: string;
  status: 'TODO' | 'IN_PROGRESS' | 'DONE';
  priority: 'LOW' | 'MEDIUM' | 'HIGH';
  estimatedMinutes?: number;
  dueDate?: string;
  xpAwarded?: number;
  completedAt?: string;
  podId?: number;
  podName?: string;
  createdAt: string;
}

export interface FocusSession {
  id: number;
  userId: number;
  roomId?: number;
  roomName?: string;
  roomCode?: string;
  focusMinutesCompleted: number;
  cyclesCompleted: number;
  xpEarned: number;
  sessionDate: string;
  startedAt: string;
  endedAt?: string;
}

export interface Achievement {
  id: number;
  key: string;
  name: string;
  description: string;
  tier: 'BRONZE' | 'SILVER' | 'GOLD' | 'PLATINUM';
  xpReward: number;
  iconName: string;
  totalRequired: number;
  unlocked: boolean;
  unlockedAt?: string;
  currentProgress: number;
  progressPercent: number;
}

export interface LeaderboardEntry {
  rank: number;
  userId: number;
  displayName: string;
  username: string;
  avatarUrl?: string;
  level: number;
  totalXp: number;
  focusMinutesWeek: number;
  streakDays: number;
  isCurrentUser: boolean;
}

export interface AnalyticsStat {
  date: string;           // ISO date
  dayLabel: string;       // Mon, Tue, etc.
  focusMinutes: number;
  sessionsCount: number;
  cyclesCompleted: number;
  xpEarned: number;
}

export interface DashboardData {
  user: User;
  todayFocusMinutes: number;
  weekFocusMinutes: number;
  totalSessions: number;
  activeRoom?: Room;
  nextTask?: Task;
  recentAchievements: Achievement[];
  weekStats: AnalyticsStat[];
}

export interface ChatMessage {
  id: number;
  userId: number;
  displayName: string;
  avatarUrl?: string;
  content: string;
  sentAt: string;
}

// WebSocket envelope
export interface WsEnvelope<T = unknown> {
  eventType: string;
  roomCode: string;
  timestamp: string;
  payload: T;
}

// Pagination
export interface Page<T> {
  content: T[];
  totalElements: number;
  totalPages: number;
  number: number;
  size: number;
}
