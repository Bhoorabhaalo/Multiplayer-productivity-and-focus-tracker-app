import React, { useEffect, useState, useMemo } from 'react';
import { useParams, useNavigate } from 'react-router-dom';
import {
  Play,
  Pause,
  SkipForward,
  Copy,
  Check,
  LogOut,
  Send,
  Users,
  Target,
  CheckSquare,
  MessageSquare,
  Radio,
} from 'lucide-react';
import api from '../lib/api';
import { useAuthStore } from '../stores/authStore';
import { useRoomStore } from '../stores/roomStore';
import { useWebSocket } from '../hooks/useWebSocket';
import type { Room, ChatMessage, ChecklistItem } from '../types';

export const FocusRoom: React.FC = () => {
  const { code } = useParams<{ code?: string }>();
  const navigate = useNavigate();
  const { user } = useAuthStore();
  const {
    room,
    setRoom,
    members,
    sprintTarget,
    checklist,
    setChecklist,
    chatMessages,
    setChatMessages,
    isConnected,
  } = useRoomStore();

  const [copied, setCopied] = useState(false);
  const [chatInput, setChatInput] = useState('');
  const [newChecklistText, setNewChecklistText] = useState('');
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState('');
  const [now, setNow] = useState(Date.now());

  // WebSocket connection for real-time room sync
  useWebSocket(code);

  // Local tick every second for timer interpolation
  useEffect(() => {
    const timer = setInterval(() => setNow(Date.now()), 1000);
    return () => clearInterval(timer);
  }, []);

  // Fetch initial room state
  useEffect(() => {
    if (!code) {
      setLoading(false);
      return;
    }
    fetchRoom(code);
    fetchChat(code);
  }, [code]);

  const fetchRoom = async (roomCode: string) => {
    setLoading(true);
    setError('');
    try {
      const res = await api.get<Room>(`/rooms/${roomCode}`);
      setRoom(res.data);
    } catch (err: any) {
      setError(err.response?.data?.message || 'Room not found or session ended');
    } finally {
      setLoading(false);
    }
  };

  const fetchChat = async (roomCode: string) => {
    try {
      const res = await api.get<ChatMessage[]>(`/rooms/${roomCode}/chat`);
      setChatMessages(res.data);
    } catch (err) {
      console.error('Failed to load room chat', err);
    }
  };

  // Timer calculation: Server owns clock, client interpolates
  const timeRemaining = useMemo(() => {
    if (!room) return 0;
    if (room.paused) {
      return room.remainingSecondsWhenPaused || 0;
    }
    if (!room.phaseEndsAt) {
      return room.phase === 'BREAK' ? room.breakMinutes * 60 : room.focusMinutes * 60;
    }
    const endMs = new Date(room.phaseEndsAt).getTime();
    const diff = Math.max(0, Math.floor((endMs - now) / 1000));
    return diff;
  }, [room, now]);

  const formatTimer = (totalSeconds: number) => {
    const m = Math.floor(totalSeconds / 60);
    const s = totalSeconds % 60;
    return `${m.toString().padStart(2, '0')}:${s.toString().padStart(2, '0')}`;
  };

  // Host verification
  const isHost = user && room && user.id === room.hostId;

  // Timer Host Actions
  const handleStartTimer = async () => {
    if (!code) return;
    try {
      const res = await api.post<Room>(`/rooms/${code}/timer/start`);
      setRoom(res.data);
    } catch (err) {
      console.error(err);
    }
  };

  const handlePauseTimer = async () => {
    if (!code) return;
    try {
      const res = await api.post<Room>(`/rooms/${code}/timer/pause`);
      setRoom(res.data);
    } catch (err) {
      console.error(err);
    }
  };

  const handleResumeTimer = async () => {
    if (!code) return;
    try {
      const res = await api.post<Room>(`/rooms/${code}/timer/resume`);
      setRoom(res.data);
    } catch (err) {
      console.error(err);
    }
  };

  const handleSkipPhase = async () => {
    if (!code) return;
    try {
      const res = await api.post<Room>(`/rooms/${code}/timer/skip`);
      setRoom(res.data);
    } catch (err) {
      console.error(err);
    }
  };

  const handleExtendTimer = async () => {
    if (!code) return;
    try {
      const res = await api.post<Room>(`/rooms/${code}/timer/extend`, { minutes: 5 });
      setRoom(res.data);
    } catch (err) {
      console.error(err);
    }
  };

  const handleLeaveRoom = async () => {
    if (!code) return;
    try {
      await api.post(`/rooms/${code}/leave`);
      setRoom(null);
      navigate('/dashboard');
    } catch (err) {
      console.error(err);
      navigate('/dashboard');
    }
  };

  const handleCopyCode = () => {
    if (!room?.code) return;
    navigator.clipboard.writeText(room.code);
    setCopied(true);
    setTimeout(() => setCopied(false), 2000);
  };

  // Checklist Actions
  const handleToggleChecklist = async (itemId: number) => {
    if (!code) return;
    try {
      const res = await api.patch<ChecklistItem[]>(`/rooms/${code}/checklist/${itemId}/toggle`);
      setChecklist(res.data);
    } catch (err) {
      console.error(err);
    }
  };

  const handleAddChecklist = async (e: React.FormEvent) => {
    e.preventDefault();
    if (!code || !newChecklistText.trim()) return;
    try {
      const res = await api.post<ChecklistItem[]>(`/rooms/${code}/checklist`, {
        content: newChecklistText.trim(),
      });
      setChecklist(res.data);
      setNewChecklistText('');
    } catch (err) {
      console.error(err);
    }
  };

  // Sprint Target Step Increment
  const handleIncrementStep = async () => {
    if (!code || !sprintTarget) return;
    const nextStep = Math.min(sprintTarget.stepTotal, sprintTarget.stepCurrent + 1);
    try {
      const res = await api.put(`/rooms/${code}/sprint-target`, {
        title: sprintTarget.title,
        description: sprintTarget.description,
        stepCurrent: nextStep,
        stepTotal: sprintTarget.stepTotal,
        tag: sprintTarget.tag,
      });
      // roomStore updated via WS or res
      useRoomStore.getState().setSprintTarget(res.data);
    } catch (err) {
      console.error(err);
    }
  };

  // Chat message send
  const handleSendChat = async (e: React.FormEvent) => {
    e.preventDefault();
    if (!code || !chatInput.trim()) return;
    try {
      const res = await api.post<ChatMessage>(`/rooms/${code}/chat`, { content: chatInput.trim() });
      useRoomStore.getState().addChatMessage(res.data);
      setChatInput('');
    } catch (err) {
      console.error(err);
    }
  };

  // If no room code provided in URL, show room directory or lobby
  if (!code || (!loading && !room)) {
    return (
      <div className="max-w-2xl mx-auto text-center py-12">
        <div className="inline-flex items-center justify-center w-16 h-16 rounded-2xl bg-emerald-500/10 border border-emerald-500/30 text-emerald-400 font-bold text-3xl mb-4">
          ⚡
        </div>
        <h2 className="text-xl font-bold text-white">Join or Create a Focus Room</h2>
        <p className="text-xs text-zinc-400 mt-1 max-w-md mx-auto">
          Multiplayer focus sessions keep you accountable. Enter a 6-character room code from your squad or click Create Room above.
        </p>

        {error && (
          <div className="mt-4 p-3 bg-rose-500/10 border border-rose-500/30 text-rose-300 text-xs rounded-lg inline-block">
            {error}
          </div>
        )}

        <div className="mt-6 flex justify-center gap-3">
          <button
            onClick={() => navigate('/dashboard')}
            className="px-4 py-2 rounded-lg bg-[#101422] hover:bg-[#131827] text-zinc-300 text-xs font-semibold border border-[#1A2035]"
          >
            Back to Dashboard
          </button>
        </div>
      </div>
    );
  }

  return (
    <div className="space-y-6 max-w-7xl mx-auto">
      {/* Room Header Banner */}
      <div className="bg-[#101422] border border-[#1A2035] rounded-xl p-5 flex flex-col md:flex-row md:items-center justify-between gap-4">
        <div>
          <div className="flex items-center gap-2 mb-1">
            <span className="flex items-center gap-1.5 px-2 py-0.5 rounded text-[11px] font-bold bg-emerald-500/20 text-emerald-300 border border-emerald-500/30">
              <Radio className="w-3 h-3 text-emerald-400 animate-pulse" />
              {isConnected ? 'LIVE SYNC' : 'CONNECTING...'}
            </span>
            <span className="text-xs text-zinc-400 font-mono">
              Host: <strong className="text-white">{room?.hostName}</strong>
            </span>
          </div>
          <h2 className="text-lg font-bold text-white tracking-tight">{room?.name}</h2>
          {room?.description && <p className="text-xs text-zinc-400 mt-0.5">{room.description}</p>}
        </div>

        {/* Room code & actions */}
        <div className="flex items-center gap-2 self-start md:self-auto">
          <button
            onClick={handleCopyCode}
            className="flex items-center gap-2 px-3 py-1.5 rounded-lg bg-[#0A0C14] hover:bg-[#131827] border border-[#1A2035] text-xs font-mono text-zinc-200 transition-colors"
          >
            <span className="text-zinc-400">Code:</span>
            <strong className="text-emerald-400 tracking-wider">{room?.code}</strong>
            {copied ? <Check className="w-3.5 h-3.5 text-emerald-400" /> : <Copy className="w-3.5 h-3.5 text-zinc-500" />}
          </button>

          <button
            onClick={handleLeaveRoom}
            className="flex items-center gap-1.5 px-3 py-1.5 rounded-lg bg-rose-500/10 hover:bg-rose-500/20 text-rose-300 border border-rose-500/30 text-xs font-semibold transition-colors"
          >
            <LogOut className="w-3.5 h-3.5" />
            <span>Leave</span>
          </button>
        </div>
      </div>

      {/* Main Grid: Timer on Left, Squad & Sprints on Right */}
      <div className="grid grid-cols-1 lg:grid-cols-12 gap-6">
        {/* Left Column: Pomodoro Synchronized Timer */}
        <div className="lg:col-span-7 bg-[#101422] border border-[#1A2035] rounded-xl p-6 sm:p-8 flex flex-col items-center justify-center text-center relative overflow-hidden">
          {/* Phase Pill */}
          <div className="mb-4">
            <span
              className={`px-3 py-1 rounded-full text-xs font-mono font-bold tracking-widest uppercase border ${
                room?.phase === 'FOCUS'
                  ? 'bg-emerald-500/10 text-emerald-400 border-emerald-500/30'
                  : room?.phase === 'BREAK'
                  ? 'bg-amber-500/10 text-amber-400 border-amber-500/30'
                  : 'bg-indigo-500/10 text-indigo-400 border-indigo-500/30'
              }`}
            >
              {room?.phase === 'FOCUS'
                ? '🔥 FOCUS PHASE'
                : room?.phase === 'BREAK'
                ? '☕ BREAK PHASE'
                : room?.phase || 'IDLE'}
            </span>
          </div>

          {/* Tabular Numerals Timer Display */}
          <div className="my-6">
            <p
              className="text-7xl sm:text-8xl font-black text-white font-mono tracking-tight select-none"
              style={{ fontVariantNumeric: 'tabular-nums' }}
            >
              {formatTimer(timeRemaining)}
            </p>
            <p className="text-xs text-zinc-400 mt-2 font-mono">
              Cycle {room?.currentCycle || 1} of {room?.targetCycles || 4} • Focus Interval: {room?.focusMinutes}m
            </p>
          </div>

          {/* Host Controls or Participant notice */}
          {isHost ? (
            <div className="space-y-3 w-full max-w-sm">
              <div className="flex justify-center gap-2">
                {room?.phase === 'IDLE' ? (
                  <button
                    onClick={handleStartTimer}
                    className="flex-1 py-2.5 px-4 rounded-lg bg-emerald-600 hover:bg-emerald-500 text-white font-bold text-xs flex items-center justify-center gap-2 shadow-lg shadow-emerald-950/60"
                  >
                    <Play className="w-4 h-4 fill-white" />
                    <span>Start Session</span>
                  </button>
                ) : room?.paused ? (
                  <button
                    onClick={handleResumeTimer}
                    className="flex-1 py-2.5 px-4 rounded-lg bg-emerald-600 hover:bg-emerald-500 text-white font-bold text-xs flex items-center justify-center gap-2 shadow-lg shadow-emerald-950/60"
                  >
                    <Play className="w-4 h-4 fill-white" />
                    <span>Resume Timer</span>
                  </button>
                ) : (
                  <button
                    onClick={handlePauseTimer}
                    className="flex-1 py-2.5 px-4 rounded-lg bg-amber-600 hover:bg-amber-500 text-white font-bold text-xs flex items-center justify-center gap-2 shadow-lg shadow-amber-950/60"
                  >
                    <Pause className="w-4 h-4 fill-white" />
                    <span>Pause Timer</span>
                  </button>
                )}

                <button
                  onClick={handleSkipPhase}
                  className="py-2.5 px-4 rounded-lg bg-[#131827] hover:bg-[#182035] text-zinc-200 border border-[#1A2035] font-semibold text-xs flex items-center gap-1.5"
                  title="Skip Phase"
                >
                  <SkipForward className="w-4 h-4" />
                  <span>Skip</span>
                </button>

                <button
                  onClick={handleExtendTimer}
                  className="py-2.5 px-3 rounded-lg bg-[#131827] hover:bg-[#182035] text-zinc-200 border border-[#1A2035] font-mono text-xs"
                  title="Extend 5 mins"
                >
                  +5m
                </button>
              </div>
              <p className="text-[10px] text-zinc-500 uppercase tracking-wider font-semibold">
                You are Room Host • Timer syncs with all members
              </p>
            </div>
          ) : (
            <div className="p-2.5 px-4 rounded-lg bg-[#0A0C14] border border-[#1A2035] text-xs text-zinc-400">
              Host <strong className="text-zinc-200">{room?.hostName}</strong> is controlling the session clock.
            </div>
          )}

          {/* Bottom stats row */}
          <div className="grid grid-cols-3 gap-4 w-full mt-8 pt-6 border-t border-[#1A2035]">
            <div>
              <p className="text-[10px] text-zinc-500 font-semibold uppercase">Focus Target</p>
              <p className="text-sm font-bold text-white font-mono">{room?.focusMinutes}m</p>
            </div>
            <div>
              <p className="text-[10px] text-zinc-500 font-semibold uppercase">Break Length</p>
              <p className="text-sm font-bold text-amber-400 font-mono">{room?.breakMinutes}m</p>
            </div>
            <div>
              <p className="text-[10px] text-zinc-500 font-semibold uppercase">Completed Cycles</p>
              <p className="text-sm font-bold text-emerald-400 font-mono">{room?.currentCycle || 0}</p>
            </div>
          </div>
        </div>

        {/* Right Column: Squad, Sprints & Checklist */}
        <div className="lg:col-span-5 space-y-6">
          {/* Study Squad Members */}
          <div className="bg-[#101422] border border-[#1A2035] rounded-xl p-5">
            <div className="flex items-center justify-between mb-3">
              <h3 className="text-xs font-bold text-zinc-400 uppercase tracking-wider flex items-center gap-1.5">
                <Users className="w-4 h-4 text-emerald-400" />
                Study Squad ({members.length})
              </h3>
              <span className="text-[10px] font-mono text-emerald-400">Live Status</span>
            </div>

            <div className="space-y-2 max-h-48 overflow-y-auto">
              {members.map((m) => (
                <div
                  key={m.userId}
                  className="flex items-center justify-between p-2.5 rounded-lg bg-[#0A0C14] border border-[#1A2035]"
                >
                  <div className="flex items-center gap-2.5 min-w-0">
                    <div className="w-7 h-7 rounded-full bg-emerald-500/20 border border-emerald-500/40 flex items-center justify-center text-xs font-bold text-emerald-300">
                      {m.displayName?.slice(0, 1).toUpperCase() || 'U'}
                    </div>
                    <div className="min-w-0">
                      <p className="text-xs font-bold text-white truncate">{m.displayName}</p>
                      <p className="text-[10px] text-zinc-500 font-mono">Lv.{m.level}</p>
                    </div>
                  </div>

                  <span
                    className={`text-[10px] font-mono font-semibold px-2 py-0.5 rounded border ${
                      m.focusState === 'FOCUSING'
                        ? 'bg-emerald-500/10 text-emerald-400 border-emerald-500/30 animate-pulse'
                        : m.focusState === 'ON_BREAK'
                        ? 'bg-amber-500/10 text-amber-400 border-amber-500/30'
                        : 'bg-zinc-500/10 text-zinc-400 border-zinc-500/30'
                    }`}
                  >
                    {m.focusState}
                  </span>
                </div>
              ))}
            </div>
          </div>

          {/* Sprint Target */}
          <div className="bg-[#101422] border border-[#1A2035] rounded-xl p-5">
            <div className="flex items-center justify-between mb-2">
              <h3 className="text-xs font-bold text-zinc-400 uppercase tracking-wider flex items-center gap-1.5">
                <Target className="w-4 h-4 text-indigo-400" />
                Sprint Milestone
              </h3>
              {sprintTarget?.tag && (
                <span className="text-[10px] font-mono px-1.5 py-0.5 rounded bg-indigo-500/10 text-indigo-300 border border-indigo-500/20">
                  {sprintTarget.tag}
                </span>
              )}
            </div>

            {sprintTarget ? (
              <div className="bg-[#0A0C14] border border-[#1A2035] rounded-lg p-3.5 space-y-2">
                <div className="flex items-center justify-between">
                  <p className="text-xs font-bold text-white">{sprintTarget.title}</p>
                  <span className="text-xs font-mono font-bold text-indigo-400">
                    {sprintTarget.stepCurrent}/{sprintTarget.stepTotal}
                  </span>
                </div>
                {sprintTarget.description && (
                  <p className="text-[11px] text-zinc-400">{sprintTarget.description}</p>
                )}
                <div className="w-full h-1.5 rounded-full bg-[#131827] overflow-hidden">
                  <div
                    className="h-full bg-indigo-500 rounded-full transition-all"
                    style={{
                      width: `${Math.min(100, (sprintTarget.stepCurrent / sprintTarget.stepTotal) * 100)}%`,
                    }}
                  />
                </div>
                <div className="flex justify-end pt-1">
                  <button
                    onClick={handleIncrementStep}
                    disabled={sprintTarget.stepCurrent >= sprintTarget.stepTotal}
                    className="px-2.5 py-1 rounded bg-indigo-600 hover:bg-indigo-500 disabled:opacity-40 text-white text-[11px] font-bold"
                  >
                    +1 Step Done
                  </button>
                </div>
              </div>
            ) : (
              <div className="bg-[#0A0C14] border border-[#1A2035] rounded-lg p-3 text-center text-xs text-zinc-400">
                Focus on session milestones with your squad.
              </div>
            )}
          </div>

          {/* Session Shared Checklist */}
          <div className="bg-[#101422] border border-[#1A2035] rounded-xl p-5">
            <div className="flex items-center justify-between mb-3">
              <h3 className="text-xs font-bold text-zinc-400 uppercase tracking-wider flex items-center gap-1.5">
                <CheckSquare className="w-4 h-4 text-emerald-400" />
                Session Checklist
              </h3>
              <span className="text-[10px] text-emerald-400 font-mono">+20 Karma each</span>
            </div>

            <div className="space-y-2 max-h-36 overflow-y-auto mb-3">
              {checklist.map((item) => (
                <div
                  key={item.id}
                  onClick={() => handleToggleChecklist(item.id)}
                  className="flex items-center gap-2 p-2 rounded-lg bg-[#0A0C14] border border-[#1A2035] hover:border-emerald-500/30 cursor-pointer transition-colors"
                >
                  <input
                    type="checkbox"
                    checked={item.completed}
                    onChange={() => {}}
                    className="rounded accent-emerald-500 cursor-pointer"
                  />
                  <span
                    className={`text-xs ${
                      item.completed ? 'line-through text-zinc-500' : 'text-zinc-200'
                    }`}
                  >
                    {item.content}
                  </span>
                </div>
              ))}
            </div>

            <form onSubmit={handleAddChecklist} className="flex gap-2">
              <input
                type="text"
                placeholder="Add session item..."
                value={newChecklistText}
                onChange={(e) => setNewChecklistText(e.target.value)}
                className="flex-1 bg-[#0A0C14] border border-[#1A2035] focus:border-emerald-500/50 rounded-lg px-2.5 py-1.5 text-xs text-white outline-none"
              />
              <button
                type="submit"
                disabled={!newChecklistText.trim()}
                className="px-3 py-1.5 rounded-lg bg-emerald-600 hover:bg-emerald-500 disabled:opacity-50 text-white text-xs font-bold"
              >
                Add
              </button>
            </form>
          </div>
        </div>
      </div>

      {/* Bottom Row: Real-time Study Chat */}
      <div className="bg-[#101422] border border-[#1A2035] rounded-xl p-5">
        <div className="flex items-center justify-between mb-3 border-b border-[#1A2035] pb-3">
          <h3 className="text-xs font-bold text-zinc-400 uppercase tracking-wider flex items-center gap-1.5">
            <MessageSquare className="w-4 h-4 text-emerald-400" />
            Squad Focus Chat
          </h3>
          <span className="text-[10px] text-zinc-500">Keep questions concise to minimize distraction</span>
        </div>

        <div className="space-y-2 max-h-48 overflow-y-auto p-1 mb-3">
          {chatMessages.length === 0 ? (
            <p className="text-xs text-zinc-500 text-center py-4">No messages yet. Send encouragement to your squad!</p>
          ) : (
            chatMessages.map((msg) => (
              <div key={msg.id} className="flex items-start gap-2 text-xs">
                <span className="font-bold text-emerald-400 shrink-0">{msg.displayName}:</span>
                <span className="text-zinc-300 break-words flex-1">{msg.content}</span>
                <span className="text-[10px] text-zinc-500 font-mono shrink-0">
                  {new Date(msg.sentAt).toLocaleTimeString([], { hour: '2-digit', minute: '2-digit' })}
                </span>
              </div>
            ))
          )}
        </div>

        <form onSubmit={handleSendChat} className="flex gap-2">
          <input
            type="text"
            placeholder="Type a message to the squad..."
            value={chatInput}
            onChange={(e) => setChatInput(e.target.value)}
            className="flex-1 bg-[#0A0C14] border border-[#1A2035] focus:border-emerald-500/50 rounded-lg px-3 py-2 text-xs text-white outline-none"
          />
          <button
            type="submit"
            disabled={!chatInput.trim()}
            className="px-4 py-2 rounded-lg bg-emerald-600 hover:bg-emerald-500 disabled:opacity-50 text-white text-xs font-bold flex items-center gap-1"
          >
            <Send className="w-3.5 h-3.5" />
            <span>Send</span>
          </button>
        </form>
      </div>
    </div>
  );
};
