import React, { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import { Flame, Plus, LogIn, Bell, Sparkles } from 'lucide-react';
import { useAuthStore } from '../../stores/authStore';
import { useRoomStore } from '../../stores/roomStore';
import api from '../../lib/api';
import type { Room } from '../../types';

interface TopbarProps {
  title?: string;
  subtitle?: string;
}

export const Topbar: React.FC<TopbarProps> = ({ title = 'Dashboard', subtitle }) => {
  const { user } = useAuthStore();
  const { setRoom } = useRoomStore();
  const navigate = useNavigate();

  const [isJoinOpen, setIsJoinOpen] = useState(false);
  const [isCreateOpen, setIsCreateOpen] = useState(false);
  const [roomCodeInput, setRoomCodeInput] = useState('');
  const [newRoomName, setNewRoomName] = useState('');
  const [newRoomDesc, setNewRoomDesc] = useState('');
  const [focusMins, setFocusMins] = useState(25);
  const [breakMins, setBreakMins] = useState(5);
  const [joinError, setJoinError] = useState('');
  const [createError, setCreateError] = useState('');
  const [loading, setLoading] = useState(false);

  const handleJoin = async (e: React.FormEvent) => {
    e.preventDefault();
    if (!roomCodeInput.trim()) return;
    setLoading(true);
    setJoinError('');
    try {
      const res = await api.post<Room>('/rooms/join', { code: roomCodeInput.trim().toUpperCase() });
      setRoom(res.data);
      setIsJoinOpen(false);
      setRoomCodeInput('');
      navigate(`/rooms/${res.data.code}`);
    } catch (err: any) {
      setJoinError(err.response?.data?.message || 'Room not found or could not join');
    } finally {
      setLoading(false);
    }
  };

  const handleCreate = async (e: React.FormEvent) => {
    e.preventDefault();
    if (!newRoomName.trim()) return;
    setLoading(true);
    setCreateError('');
    try {
      const res = await api.post<Room>('/rooms', {
        name: newRoomName.trim(),
        description: newRoomDesc.trim(),
        focusMinutes: focusMins,
        breakMinutes: breakMins,
        targetCycles: 4,
        maxMembers: 12,
      });
      setRoom(res.data);
      setIsCreateOpen(false);
      setNewRoomName('');
      setNewRoomDesc('');
      navigate(`/rooms/${res.data.code}`);
    } catch (err: any) {
      setCreateError(err.response?.data?.message || 'Failed to create room');
    } finally {
      setLoading(false);
    }
  };

  return (
    <>
      <header className="h-16 bg-[#101422] border-b border-[#1A2035] px-6 flex items-center justify-between sticky top-0 z-20">
        {/* Left: Section titles */}
        <div>
          <h1 className="text-base font-bold text-white tracking-wide">{title}</h1>
          {subtitle && <p className="text-xs text-zinc-400 font-normal">{subtitle}</p>}
        </div>

        {/* Right: Actions */}
        <div className="flex items-center gap-3">
          {/* Streak pill */}
          <div className="flex items-center gap-1.5 px-3 py-1.5 rounded-full bg-amber-500/10 border border-amber-500/20 text-amber-300 text-xs font-semibold">
            <Flame className="w-3.5 h-3.5 text-amber-400 fill-amber-400" />
            <span>{user?.streakDays || 0}-Day Streak</span>
          </div>

          {/* XP & Level Pill */}
          <div className="hidden sm:flex items-center gap-1.5 px-3 py-1.5 rounded-full bg-indigo-500/10 border border-indigo-500/20 text-indigo-300 text-xs font-semibold">
            <Sparkles className="w-3.5 h-3.5 text-indigo-400" />
            <span>{user?.totalXp || 0} XP</span>
          </div>

          {/* Join Room Button */}
          <button
            onClick={() => setIsJoinOpen(true)}
            className="flex items-center gap-1.5 px-3 py-1.5 rounded-lg bg-[#131827] hover:bg-[#182035] text-zinc-200 text-xs font-semibold border border-[#1A2035] transition-colors"
          >
            <LogIn className="w-3.5 h-3.5 text-zinc-400" />
            <span>Join Room</span>
          </button>

          {/* Create Room Button */}
          <button
            onClick={() => setIsCreateOpen(true)}
            className="flex items-center gap-1.5 px-3.5 py-1.5 rounded-lg bg-emerald-600 hover:bg-emerald-500 text-white text-xs font-bold shadow-sm shadow-emerald-950/50 transition-colors"
          >
            <Plus className="w-3.5 h-3.5" />
            <span>Create Room</span>
          </button>

          {/* Notifications */}
          <button
            title="Notifications"
            className="p-2 text-zinc-400 hover:text-zinc-200 hover:bg-[#131827] rounded-lg transition-colors relative"
          >
            <Bell className="w-4 h-4" />
            <span className="absolute top-1.5 right-1.5 w-1.5 h-1.5 rounded-full bg-emerald-500" />
          </button>
        </div>
      </header>

      {/* Join Room Modal */}
      {isJoinOpen && (
        <div className="fixed inset-0 z-50 flex items-center justify-center bg-black/70 backdrop-blur-sm p-4">
          <div className="bg-[#101422] border border-[#1A2035] rounded-xl p-6 w-full max-w-sm shadow-2xl">
            <h3 className="text-base font-bold text-white">Join Focus Room</h3>
            <p className="text-xs text-zinc-400 mt-1">Enter the 6-character room invite code from your squad.</p>

            <form onSubmit={handleJoin} className="mt-4 space-y-3">
              <div>
                <label className="block text-xs font-medium text-zinc-300 mb-1">Room Code</label>
                <input
                  type="text"
                  maxLength={6}
                  placeholder="e.g. STAN01"
                  value={roomCodeInput}
                  onChange={(e) => setRoomCodeInput(e.target.value.toUpperCase())}
                  className="w-full uppercase tracking-widest text-center text-lg font-mono font-bold bg-[#0A0C14] border border-[#1A2035] focus:border-emerald-500/60 rounded-lg px-3 py-2 text-white outline-none"
                  autoFocus
                />
              </div>

              {joinError && <p className="text-xs text-rose-400 font-medium">{joinError}</p>}

              <div className="flex gap-2 pt-2">
                <button
                  type="button"
                  onClick={() => {
                    setIsJoinOpen(false);
                    setJoinError('');
                  }}
                  className="flex-1 px-3 py-2 text-xs font-semibold rounded-lg bg-[#131827] hover:bg-[#182035] text-zinc-300 border border-[#1A2035]"
                >
                  Cancel
                </button>
                <button
                  type="submit"
                  disabled={loading || !roomCodeInput.trim()}
                  className="flex-1 px-3 py-2 text-xs font-bold rounded-lg bg-emerald-600 hover:bg-emerald-500 disabled:opacity-50 text-white"
                >
                  {loading ? 'Joining...' : 'Enter Room'}
                </button>
              </div>
            </form>
          </div>
        </div>
      )}

      {/* Create Room Modal */}
      {isCreateOpen && (
        <div className="fixed inset-0 z-50 flex items-center justify-center bg-black/70 backdrop-blur-sm p-4">
          <div className="bg-[#101422] border border-[#1A2035] rounded-xl p-6 w-full max-w-md shadow-2xl">
            <h3 className="text-base font-bold text-white">Create Focus Room</h3>
            <p className="text-xs text-zinc-400 mt-1">Spin up a synchronized multiplayer room with customized timer intervals.</p>

            <form onSubmit={handleCreate} className="mt-4 space-y-3">
              <div>
                <label className="block text-xs font-medium text-zinc-300 mb-1">Room Name</label>
                <input
                  type="text"
                  placeholder="e.g. Algorithms & Systems Sprint"
                  value={newRoomName}
                  onChange={(e) => setNewRoomName(e.target.value)}
                  className="w-full text-sm bg-[#0A0C14] border border-[#1A2035] focus:border-emerald-500/60 rounded-lg px-3 py-2 text-white outline-none"
                  required
                  autoFocus
                />
              </div>

              <div>
                <label className="block text-xs font-medium text-zinc-300 mb-1">Description (Optional)</label>
                <input
                  type="text"
                  placeholder="e.g. Preparing for midterm exam, cameras/mics off, silent focus"
                  value={newRoomDesc}
                  onChange={(e) => setNewRoomDesc(e.target.value)}
                  className="w-full text-xs bg-[#0A0C14] border border-[#1A2035] focus:border-emerald-500/60 rounded-lg px-3 py-2 text-white outline-none"
                />
              </div>

              <div className="grid grid-cols-2 gap-3">
                <div>
                  <label className="block text-xs font-medium text-zinc-300 mb-1">Focus Time (mins)</label>
                  <input
                    type="number"
                    min={5}
                    max={120}
                    value={focusMins}
                    onChange={(e) => setFocusMins(Number(e.target.value))}
                    className="w-full text-sm font-mono bg-[#0A0C14] border border-[#1A2035] focus:border-emerald-500/60 rounded-lg px-3 py-2 text-white outline-none"
                  />
                </div>
                <div>
                  <label className="block text-xs font-medium text-zinc-300 mb-1">Break Time (mins)</label>
                  <input
                    type="number"
                    min={1}
                    max={30}
                    value={breakMins}
                    onChange={(e) => setBreakMins(Number(e.target.value))}
                    className="w-full text-sm font-mono bg-[#0A0C14] border border-[#1A2035] focus:border-emerald-500/60 rounded-lg px-3 py-2 text-white outline-none"
                  />
                </div>
              </div>

              {createError && <p className="text-xs text-rose-400 font-medium">{createError}</p>}

              <div className="flex gap-2 pt-2">
                <button
                  type="button"
                  onClick={() => {
                    setIsCreateOpen(false);
                    setCreateError('');
                  }}
                  className="flex-1 px-3 py-2 text-xs font-semibold rounded-lg bg-[#131827] hover:bg-[#182035] text-zinc-300 border border-[#1A2035]"
                >
                  Cancel
                </button>
                <button
                  type="submit"
                  disabled={loading || !newRoomName.trim()}
                  className="flex-1 px-3 py-2 text-xs font-bold rounded-lg bg-emerald-600 hover:bg-emerald-500 disabled:opacity-50 text-white"
                >
                  {loading ? 'Creating...' : 'Create & Enter'}
                </button>
              </div>
            </form>
          </div>
        </div>
      )}
    </>
  );
};
