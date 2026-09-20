import React, { useEffect, useState } from 'react';
import {
  User,
  Sliders,
  Check,
  Building2,
  BookOpen,
  Volume2,
  Layers,
  Sparkles,
  Plus,
  Trash2,
} from 'lucide-react';
import api from '../lib/api';
import { useAuthStore } from '../stores/authStore';
import type { User as UserType } from '../types';

interface Pod {
  id: number;
  name: string;
  code: string;
  courseCode?: string;
  description?: string;
  memberCount: number;
}

export const Settings: React.FC = () => {
  const { user, setUser } = useAuthStore();

  const [displayName, setDisplayName] = useState('');
  const [university, setUniversity] = useState('');
  const [major, setMajor] = useState('');
  const [focusStatement, setFocusStatement] = useState('');
  const [density, setDensity] = useState<'COMPACT' | 'BALANCED' | 'SPACIOUS'>('BALANCED');
  const [soundEffects, setSoundEffects] = useState(true);

  const [pods, setPods] = useState<Pod[]>([]);
  const [newPodCode, setNewPodCode] = useState('');
  const [savedSuccess, setSavedSuccess] = useState(false);
  const [loading, setLoading] = useState(false);

  useEffect(() => {
    if (user) {
      setDisplayName(user.displayName || '');
      setUniversity(user.university || '');
      setMajor(user.major || '');
      setFocusStatement(user.focusStatement || '');
      setDensity(user.interfaceDensity || 'BALANCED');
      setSoundEffects(user.soundEffectsEnabled ?? true);
    }
    fetchPods();
  }, [user]);

  const fetchPods = async () => {
    try {
      const res = await api.get<Pod[]>('/users/me/pods');
      setPods(res.data);
    } catch (err) {
      console.error('Failed to load pods', err);
    }
  };

  const handleSaveProfile = async (e: React.FormEvent) => {
    e.preventDefault();
    setLoading(true);
    setSavedSuccess(false);
    try {
      const [profileRes] = await Promise.all([
        api.put<UserType>('/users/me', {
          displayName,
          university,
          major,
          focusStatement,
        }),
        api.put<UserType>('/users/me/preferences', {
          interfaceDensity: density,
          soundEffectsEnabled: soundEffects,
        }),
      ]);

      setUser(profileRes.data);
      setSavedSuccess(true);
      setTimeout(() => setSavedSuccess(false), 3000);
    } catch (err) {
      console.error('Failed to save settings', err);
    } finally {
      setLoading(false);
    }
  };

  const handleAddPod = async (e: React.FormEvent) => {
    e.preventDefault();
    if (!newPodCode.trim()) return;
    try {
      const res = await api.post<Pod>('/users/me/pods', { podCode: newPodCode.trim() });
      setPods([...pods, res.data]);
      setNewPodCode('');
    } catch (err: any) {
      alert(err.response?.data?.message || 'Failed to join pod');
    }
  };

  const handleRemovePod = async (podId: number) => {
    try {
      await api.delete(`/users/me/pods/${podId}`);
      setPods(pods.filter((p) => p.id !== podId));
    } catch (err) {
      console.error('Failed to remove pod', err);
    }
  };

  return (
    <div className="max-w-4xl mx-auto space-y-6">
      <div>
        <h2 className="text-base font-bold text-white flex items-center gap-2">
          <Sliders className="w-5 h-5 text-emerald-400" />
          Settings & Academic Profile
        </h2>
        <p className="text-xs text-zinc-400 mt-0.5">
          Manage your university profile, pod subscriptions, and study interface preferences.
        </p>
      </div>

      {savedSuccess && (
        <div className="p-3 bg-emerald-500/10 border border-emerald-500/30 rounded-lg text-emerald-300 text-xs font-semibold flex items-center gap-2">
          <Check className="w-4 h-4 text-emerald-400" />
          <span>Preferences and academic profile successfully saved!</span>
        </div>
      )}

      {/* Main Settings Form */}
      <form onSubmit={handleSaveProfile} className="space-y-6">
        {/* Academic Profile Card */}
        <div className="bg-[#101422] border border-[#1A2035] rounded-xl p-6 space-y-4">
          <h3 className="text-xs font-bold text-zinc-400 uppercase tracking-wider flex items-center gap-2">
            <User className="w-4 h-4 text-emerald-400" />
            Academic Profile
          </h3>

          <div className="grid grid-cols-1 sm:grid-cols-2 gap-4">
            <div>
              <label className="block text-xs font-semibold text-zinc-300 mb-1.5">
                Display Name
              </label>
              <input
                type="text"
                value={displayName}
                onChange={(e) => setDisplayName(e.target.value)}
                className="w-full text-sm bg-[#0A0C14] border border-[#1A2035] focus:border-emerald-500/60 rounded-lg px-3.5 py-2 text-white outline-none"
              />
            </div>

            <div>
              <label className="block text-xs font-semibold text-zinc-300 mb-1.5 flex items-center gap-1.5">
                <Building2 className="w-3.5 h-3.5 text-zinc-400" />
                University / Institution
              </label>
              <input
                type="text"
                placeholder="e.g. Stanford University"
                value={university}
                onChange={(e) => setUniversity(e.target.value)}
                className="w-full text-sm bg-[#0A0C14] border border-[#1A2035] focus:border-emerald-500/60 rounded-lg px-3.5 py-2 text-white outline-none"
              />
            </div>

            <div>
              <label className="block text-xs font-semibold text-zinc-300 mb-1.5 flex items-center gap-1.5">
                <BookOpen className="w-3.5 h-3.5 text-zinc-400" />
                Degree & Major
              </label>
              <input
                type="text"
                placeholder="e.g. B.S. Computer Science"
                value={major}
                onChange={(e) => setMajor(e.target.value)}
                className="w-full text-sm bg-[#0A0C14] border border-[#1A2035] focus:border-emerald-500/60 rounded-lg px-3.5 py-2 text-white outline-none"
              />
            </div>

            <div>
              <label className="block text-xs font-semibold text-zinc-300 mb-1.5 flex items-center gap-1.5">
                <Sparkles className="w-3.5 h-3.5 text-zinc-400" />
                Focus Statement / Motto
              </label>
              <input
                type="text"
                placeholder="e.g. Deep work on distributed systems & ML"
                value={focusStatement}
                onChange={(e) => setFocusStatement(e.target.value)}
                className="w-full text-sm bg-[#0A0C14] border border-[#1A2035] focus:border-emerald-500/60 rounded-lg px-3.5 py-2 text-white outline-none"
              />
            </div>
          </div>
        </div>

        {/* Study Pods Card */}
        <div className="bg-[#101422] border border-[#1A2035] rounded-xl p-6 space-y-4">
          <div className="flex items-center justify-between">
            <h3 className="text-xs font-bold text-zinc-400 uppercase tracking-wider flex items-center gap-2">
              <Layers className="w-4 h-4 text-indigo-400" />
              Subscribed Course & Subject Pods
            </h3>
            <span className="text-xs text-zinc-400 font-mono">{pods.length} active pods</span>
          </div>

          <div className="flex flex-wrap gap-2">
            {pods.length === 0 ? (
              <p className="text-xs text-zinc-400">Not subscribed to any course pods yet.</p>
            ) : (
              pods.map((p) => (
                <div
                  key={p.id}
                  className="flex items-center gap-2 px-3 py-1.5 rounded-lg bg-[#0A0C14] border border-[#1A2035] text-xs text-zinc-200 font-medium"
                >
                  <span className="font-bold text-emerald-400 font-mono">{p.courseCode || p.code}</span>
                  <span>{p.name}</span>
                  <button
                    type="button"
                    onClick={() => handleRemovePod(p.id)}
                    className="text-zinc-500 hover:text-rose-400 transition-colors ml-1"
                  >
                    <Trash2 className="w-3 h-3" />
                  </button>
                </div>
              ))
            )}
          </div>

          <div className="flex gap-2 pt-2 border-t border-[#1A2035]">
            <input
              type="text"
              placeholder="Join Pod by Code (e.g. CS106B)..."
              value={newPodCode}
              onChange={(e) => setNewPodCode(e.target.value.toUpperCase())}
              className="w-64 uppercase text-xs font-mono bg-[#0A0C14] border border-[#1A2035] focus:border-emerald-500/60 rounded-lg px-3 py-1.5 text-white outline-none"
            />
            <button
              type="button"
              onClick={handleAddPod}
              disabled={!newPodCode.trim()}
              className="px-3 py-1.5 rounded-lg bg-[#131827] hover:bg-[#182035] text-zinc-200 border border-[#1A2035] text-xs font-semibold flex items-center gap-1"
            >
              <Plus className="w-3 h-3" />
              <span>Join Pod</span>
            </button>
          </div>
        </div>

        {/* Interface & Sound Preferences */}
        <div className="bg-[#101422] border border-[#1A2035] rounded-xl p-6 space-y-4">
          <h3 className="text-xs font-bold text-zinc-400 uppercase tracking-wider flex items-center gap-2">
            <Sliders className="w-4 h-4 text-emerald-400" />
            Display & Focus Preferences
          </h3>

          <div className="grid grid-cols-1 sm:grid-cols-2 gap-6">
            <div>
              <label className="block text-xs font-semibold text-zinc-300 mb-2">
                Interface Density
              </label>
              <div className="grid grid-cols-3 gap-2">
                {(['COMPACT', 'BALANCED', 'SPACIOUS'] as const).map((d) => (
                  <button
                    key={d}
                    type="button"
                    onClick={() => setDensity(d)}
                    className={`py-2 text-xs font-bold rounded-lg border transition-colors ${
                      density === d
                        ? 'bg-emerald-500/10 border-emerald-500 text-emerald-400'
                        : 'bg-[#0A0C14] border-[#1A2035] text-zinc-400 hover:text-zinc-200'
                    }`}
                  >
                    {d}
                  </button>
                ))}
              </div>
            </div>

            <div>
              <label className="block text-xs font-semibold text-zinc-300 mb-2 flex items-center gap-1.5">
                <Volume2 className="w-3.5 h-3.5 text-zinc-400" />
                Pomodoro Sound Effects
              </label>
              <div className="flex items-center gap-3 mt-1">
                <button
                  type="button"
                  onClick={() => setSoundEffects(!soundEffects)}
                  className={`relative inline-flex h-6 w-11 items-center rounded-full transition-colors ${
                    soundEffects ? 'bg-emerald-600' : 'bg-zinc-700'
                  }`}
                >
                  <span
                    className={`inline-block h-4 w-4 transform rounded-full bg-white transition-transform ${
                      soundEffects ? 'translate-x-6' : 'translate-x-1'
                    }`}
                  />
                </button>
                <span className="text-xs text-zinc-300">
                  {soundEffects ? 'Enabled (Soft chime on phase change)' : 'Muted'}
                </span>
              </div>
            </div>
          </div>
        </div>

        {/* Submit */}
        <div className="flex justify-end">
          <button
            type="submit"
            disabled={loading}
            className="px-6 py-2.5 rounded-lg bg-emerald-600 hover:bg-emerald-500 disabled:opacity-50 text-white text-xs font-bold shadow-md shadow-emerald-950/40 transition-colors"
          >
            {loading ? 'Saving...' : 'Save Changes'}
          </button>
        </div>
      </form>
    </div>
  );
};
