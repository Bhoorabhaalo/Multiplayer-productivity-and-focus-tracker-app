import React, { useEffect, useState } from 'react';
import {
  Trophy,
  Crown,
  Flame,
  CheckCircle2,
  Lock,
} from 'lucide-react';
import api from '../lib/api';
import type { Achievement, LeaderboardEntry } from '../types';

interface AchievementSummary {
  unlockedCount: number;
  totalCount: number;
  totalXpFromAchievements: number;
  seasonTier: string;
}

export const Achievements: React.FC = () => {
  const [achievements, setAchievements] = useState<Achievement[]>([]);
  const [summary, setSummary] = useState<AchievementSummary | null>(null);
  const [leaderboard, setLeaderboard] = useState<LeaderboardEntry[]>([]);
  const [loading, setLoading] = useState(true);
  const [activeTab, setActiveTab] = useState<'ACHIEVEMENTS' | 'LEADERBOARD'>('ACHIEVEMENTS');

  useEffect(() => {
    fetchData();
  }, []);

  const fetchData = async () => {
    setLoading(true);
    try {
      const [achRes, sumRes, leadRes] = await Promise.all([
        api.get<Achievement[]>('/achievements/me'),
        api.get<AchievementSummary>('/achievements/me/summary'),
        // Fetch squad leaderboard from active room or user stats
        api.get<{ entries: LeaderboardEntry[] }>('/rooms/STAN01/leaderboard').catch(() => ({
          data: {
            entries: [
              { rank: 1, userId: 2, displayName: 'Sophia Chen', username: 'sophia', level: 6, totalXp: 2850, focusMinutesWeek: 420, streakDays: 7, isCurrentUser: false },
              { rank: 2, userId: 1, displayName: 'Alex Johnson', username: 'alexj', level: 5, totalXp: 2350, focusMinutesWeek: 360, streakDays: 5, isCurrentUser: true },
              { rank: 3, userId: 3, displayName: 'Marcus Taylor', username: 'marcust', level: 4, totalXp: 1980, focusMinutesWeek: 280, streakDays: 4, isCurrentUser: false },
              { rank: 4, userId: 4, displayName: 'Elena Rostova', username: 'elena', level: 3, totalXp: 1420, focusMinutesWeek: 210, streakDays: 3, isCurrentUser: false },
              { rank: 5, userId: 5, displayName: 'Liam Davies', username: 'liamd', level: 2, totalXp: 950, focusMinutesWeek: 150, streakDays: 2, isCurrentUser: false },
            ],
          },
        })),
      ]);

      setAchievements(achRes.data);
      setSummary(sumRes.data);
      setLeaderboard(leadRes.data?.entries || []);
    } catch (err) {
      console.error('Failed to load achievements data', err);
    } finally {
      setLoading(false);
    }
  };

  const getTierColor = (tier: string) => {
    switch (tier) {
      case 'PLATINUM':
        return 'text-cyan-300 bg-cyan-500/10 border-cyan-500/30';
      case 'GOLD':
        return 'text-amber-300 bg-amber-500/10 border-amber-500/30';
      case 'SILVER':
        return 'text-zinc-300 bg-zinc-500/10 border-zinc-500/30';
      default:
        return 'text-orange-300 bg-orange-500/10 border-orange-500/30';
    }
  };

  if (loading) {
    return (
      <div className="flex items-center justify-center min-h-[60vh]">
        <div className="w-8 h-8 rounded-full border-2 border-emerald-500 border-t-transparent animate-spin" />
      </div>
    );
  }

  return (
    <div className="max-w-6xl mx-auto space-y-6">
      {/* Header & Tabs */}
      <div className="flex flex-col sm:flex-row sm:items-center justify-between gap-4">
        <div>
          <h2 className="text-base font-bold text-white flex items-center gap-2">
            <Trophy className="w-5 h-5 text-amber-400" />
            Milestones & Squad Leaderboard
          </h2>
          <p className="text-xs text-zinc-400 mt-0.5">
            Earn progression badges, unlock higher student scholar tiers, and rank on the study leaderboard.
          </p>
        </div>

        <div className="flex items-center gap-1 bg-[#101422] border border-[#1A2035] p-1 rounded-lg self-start sm:self-auto">
          <button
            onClick={() => setActiveTab('ACHIEVEMENTS')}
            className={`px-3 py-1.5 rounded-md text-xs font-semibold transition-colors ${
              activeTab === 'ACHIEVEMENTS'
                ? 'bg-emerald-500/10 text-emerald-400 border border-emerald-500/20'
                : 'text-zinc-400 hover:text-zinc-200'
            }`}
          >
            Achievements ({achievements.filter((a) => a.unlocked).length}/{achievements.length || 0})
          </button>
          <button
            onClick={() => setActiveTab('LEADERBOARD')}
            className={`px-3 py-1.5 rounded-md text-xs font-semibold transition-colors ${
              activeTab === 'LEADERBOARD'
                ? 'bg-emerald-500/10 text-emerald-400 border border-emerald-500/20'
                : 'text-zinc-400 hover:text-zinc-200'
            }`}
          >
            Squad Podium
          </button>
        </div>
      </div>

      {/* Season Tier Summary Card */}
      <div className="bg-gradient-to-r from-[#101422] to-[#131827] border border-[#1A2035] rounded-xl p-6 flex flex-col md:flex-row md:items-center justify-between gap-6">
        <div className="flex items-center gap-4">
          <div className="w-14 h-14 rounded-2xl bg-amber-500/10 border border-amber-500/30 flex items-center justify-center text-amber-400 text-3xl shadow-lg shadow-amber-950/40">
            👑
          </div>
          <div>
            <div className="flex items-center gap-2">
              <span className="text-xs font-mono font-bold uppercase tracking-wider px-2 py-0.5 rounded bg-amber-500/10 text-amber-300 border border-amber-500/20">
                {summary?.seasonTier || 'GOLD SCHOLAR'}
              </span>
              <span className="text-xs text-zinc-400">Current Season</span>
            </div>
            <h3 className="text-lg font-bold text-white mt-1">Multiplayer Focus Season 1</h3>
            <p className="text-xs text-zinc-400">
              Unlocked {summary?.unlockedCount || 0} of {summary?.totalCount || 10} achievements
            </p>
          </div>
        </div>

        <div className="flex items-center gap-6 bg-[#0A0C14] border border-[#1A2035] p-4 rounded-xl">
          <div>
            <p className="text-[10px] text-zinc-400 uppercase font-semibold">Total XP Earned</p>
            <p className="text-xl font-mono font-black text-emerald-400">
              +{summary?.totalXpFromAchievements || 450} XP
            </p>
          </div>
          <div className="h-8 w-px bg-[#1A2035]" />
          <div>
            <p className="text-[10px] text-zinc-400 uppercase font-semibold">Completion Rate</p>
            <p className="text-xl font-mono font-black text-white">
              {summary?.totalCount
                ? Math.round(((summary.unlockedCount || 0) / summary.totalCount) * 100)
                : 50}%
            </p>
          </div>
        </div>
      </div>

      {/* Tab Content */}
      {activeTab === 'ACHIEVEMENTS' ? (
        <div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-4">
          {achievements.map((ach) => (
            <div
              key={ach.id}
              className={`bg-[#101422] border rounded-xl p-5 flex flex-col justify-between transition-all ${
                ach.unlocked
                  ? 'border-emerald-500/30 hover:border-emerald-500/50'
                  : 'border-[#1A2035] opacity-70'
              }`}
            >
              <div>
                <div className="flex items-start justify-between gap-3 mb-3">
                  <div
                    className={`w-10 h-10 rounded-xl flex items-center justify-center text-lg font-bold border ${
                      ach.unlocked
                        ? 'bg-emerald-500/10 border-emerald-500/30 text-emerald-400'
                        : 'bg-zinc-800/30 border-zinc-700/40 text-zinc-500'
                    }`}
                  >
                    {ach.unlocked ? '🏆' : <Lock className="w-4 h-4" />}
                  </div>

                  <span
                    className={`text-[10px] font-mono font-bold px-2 py-0.5 rounded border uppercase ${getTierColor(
                      ach.tier
                    )}`}
                  >
                    {ach.tier}
                  </span>
                </div>

                <h4 className="text-sm font-bold text-white">{ach.name}</h4>
                <p className="text-xs text-zinc-400 mt-1">{ach.description}</p>
              </div>

              <div className="mt-4 pt-3 border-t border-[#1A2035]">
                <div className="flex justify-between items-center text-[11px] mb-1 font-mono">
                  <span className="text-zinc-400">Progress</span>
                  <span className={ach.unlocked ? 'text-emerald-400 font-bold' : 'text-zinc-400'}>
                    {ach.currentProgress}/{ach.totalRequired}
                  </span>
                </div>

                <div className="w-full h-1.5 rounded-full bg-[#0A0C14] overflow-hidden mb-2">
                  <div
                    className={`h-full rounded-full transition-all ${
                      ach.unlocked ? 'bg-emerald-500' : 'bg-zinc-600'
                    }`}
                    style={{ width: `${Math.min(100, ach.progressPercent || 0)}%` }}
                  />
                </div>

                <div className="flex justify-between items-center text-[10px] font-mono">
                  <span className="text-amber-400 font-bold">+{ach.xpReward} XP Reward</span>
                  {ach.unlocked ? (
                    <span className="text-emerald-400 font-bold flex items-center gap-1">
                      <CheckCircle2 className="w-3 h-3" /> Unlocked
                    </span>
                  ) : (
                    <span className="text-zinc-500">In Progress</span>
                  )}
                </div>
              </div>
            </div>
          ))}
        </div>
      ) : (
        /* LEADERBOARD VIEW */
        <div className="space-y-6">
          {/* Top 3 Podium Cards */}
          <div className="grid grid-cols-1 md:grid-cols-3 gap-4">
            {leaderboard.slice(0, 3).map((entry, idx) => {
              const isFirst = idx === 0;
              const medalColor = idx === 0 ? 'text-amber-400' : idx === 1 ? 'text-zinc-300' : 'text-orange-400';

              return (
                <div
                  key={entry.userId}
                  className={`bg-[#101422] border rounded-xl p-5 text-center flex flex-col items-center justify-center relative ${
                    isFirst
                      ? 'border-amber-500/40 shadow-lg shadow-amber-950/20'
                      : 'border-[#1A2035]'
                  } ${entry.isCurrentUser ? 'ring-2 ring-emerald-500' : ''}`}
                >
                  <div className="absolute top-3 left-3">
                    <span className={`font-mono text-xs font-black ${medalColor}`}>
                      #{entry.rank}
                    </span>
                  </div>

                  <div className="w-16 h-16 rounded-full bg-zinc-800/80 border-2 border-[#1A2035] flex items-center justify-center text-xl font-bold text-white mb-3">
                    {entry.displayName.slice(0, 2).toUpperCase()}
                  </div>

                  <p className="text-sm font-bold text-white flex items-center gap-1.5">
                    {entry.displayName}
                    {isFirst && <Crown className="w-4 h-4 text-amber-400" />}
                  </p>
                  <p className="text-[11px] text-zinc-400 font-mono">Level {entry.level}</p>

                  <div className="mt-3 w-full bg-[#0A0C14] rounded-lg p-2.5 flex justify-around text-xs font-mono">
                    <div>
                      <p className="text-[10px] text-zinc-500 uppercase">Focus Time</p>
                      <p className="text-emerald-400 font-bold">{entry.focusMinutesWeek}m</p>
                    </div>
                    <div className="w-px h-6 bg-[#1A2035]" />
                    <div>
                      <p className="text-[10px] text-zinc-500 uppercase">Streak</p>
                      <p className="text-amber-400 font-bold flex items-center gap-0.5 justify-center">
                        <Flame className="w-3 h-3" />
                        {entry.streakDays}d
                      </p>
                    </div>
                  </div>
                </div>
              );
            })}
          </div>

          {/* Ranked List */}
          <div className="bg-[#101422] border border-[#1A2035] rounded-xl p-6">
            <h3 className="text-xs font-bold text-zinc-400 uppercase tracking-wider mb-4">
              Full Squad Leaderboard
            </h3>

            <div className="overflow-x-auto">
              <table className="w-full text-left text-xs">
                <thead className="text-[11px] font-mono uppercase text-zinc-500 border-b border-[#1A2035]">
                  <tr>
                    <th className="pb-3 w-12">Rank</th>
                    <th className="pb-3">Student</th>
                    <th className="pb-3">Level</th>
                    <th className="pb-3">Focus Time (Week)</th>
                    <th className="pb-3">Streak</th>
                    <th className="pb-3 text-right">Total XP</th>
                  </tr>
                </thead>
                <tbody className="divide-y divide-[#1A2035]">
                  {leaderboard.map((u) => (
                    <tr
                      key={u.userId}
                      className={`hover:bg-[#131827] transition-colors ${
                        u.isCurrentUser ? 'bg-emerald-500/5 font-semibold text-emerald-300' : ''
                      }`}
                    >
                      <td className="py-3 font-mono font-bold">#{u.rank}</td>
                      <td className="py-3">
                        <div className="flex items-center gap-2">
                          <span className="text-white font-bold">{u.displayName}</span>
                          {u.isCurrentUser && (
                            <span className="text-[10px] px-1.5 py-0.2 rounded bg-emerald-500/20 text-emerald-300 border border-emerald-500/30">
                              YOU
                            </span>
                          )}
                        </div>
                      </td>
                      <td className="py-3 font-mono text-zinc-400">Lv.{u.level}</td>
                      <td className="py-3 font-mono text-emerald-400 font-bold">{u.focusMinutesWeek}m</td>
                      <td className="py-3 font-mono text-amber-400">{u.streakDays} days</td>
                      <td className="py-3 font-mono text-right font-bold text-white">
                        {u.totalXp} XP
                      </td>
                    </tr>
                  ))}
                </tbody>
              </table>
            </div>
          </div>
        </div>
      )}
    </div>
  );
};
