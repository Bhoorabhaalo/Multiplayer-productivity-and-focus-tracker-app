import React, { useEffect, useState } from 'react';
import { useNavigate } from 'react-router-dom';
import {
  Flame,
  Clock,
  CheckCircle2,
  Calendar,
  Radio,
  ArrowUpRight,
  Trophy,
  CheckSquare,
  Sparkles,
} from 'lucide-react';
import { ResponsiveContainer, BarChart, Bar, XAxis, YAxis, Tooltip } from 'recharts';
import api from '../lib/api';
import { useAuthStore } from '../stores/authStore';
import { useRoomStore } from '../stores/roomStore';
import type { DashboardData } from '../types';

export const Dashboard: React.FC = () => {
  const { user } = useAuthStore();
  const { setRoom } = useRoomStore();
  const navigate = useNavigate();

  const [data, setData] = useState<DashboardData | null>(null);
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    fetchDashboard();
  }, []);

  const fetchDashboard = async () => {
    try {
      const res = await api.get<DashboardData>('/dashboard');
      setData(res.data);
      if (res.data.activeRoom) {
        setRoom(res.data.activeRoom);
      }
    } catch (err) {
      console.error('Failed to load dashboard', err);
    } finally {
      setLoading(false);
    }
  };

  const handleCompleteTask = async (taskId: number) => {
    try {
      await api.patch(`/tasks/${taskId}/complete`);
      fetchDashboard();
    } catch (err) {
      console.error('Failed to complete task', err);
    }
  };

  if (loading) {
    return (
      <div className="flex items-center justify-center min-h-[60vh]">
        <div className="w-8 h-8 rounded-full border-2 border-emerald-500 border-t-transparent animate-spin" />
      </div>
    );
  }

  const stats = [
    {
      label: "Today's Focus",
      value: `${data?.todayFocusMinutes || 0}m`,
      sub: 'Target: 180m',
      icon: Clock,
      color: 'text-emerald-400',
      bg: 'bg-emerald-500/10',
      border: 'border-emerald-500/20',
    },
    {
      label: 'Week Total',
      value: `${data?.weekFocusMinutes || 0}m`,
      sub: 'Across all squads',
      icon: Calendar,
      color: 'text-indigo-400',
      bg: 'bg-indigo-500/10',
      border: 'border-indigo-500/20',
    },
    {
      label: 'Sessions Finished',
      value: `${data?.totalSessions || 0}`,
      sub: 'Pomodoro cycles',
      icon: CheckCircle2,
      color: 'text-sky-400',
      bg: 'bg-sky-500/10',
      border: 'border-sky-500/20',
    },
    {
      label: 'Current Streak',
      value: `${user?.streakDays || 0} Days`,
      sub: 'Next reward at 7d',
      icon: Flame,
      color: 'text-amber-400',
      bg: 'bg-amber-500/10',
      border: 'border-amber-500/20',
    },
  ];

  // Prepare chart data
  const chartData = data?.weekStats?.map((s) => ({
    day: s.dayLabel || s.date.slice(5),
    minutes: s.focusMinutes,
  })) || [
    { day: 'Mon', minutes: 120 },
    { day: 'Tue', minutes: 90 },
    { day: 'Wed', minutes: 150 },
    { day: 'Thu', minutes: 110 },
    { day: 'Fri', minutes: 180 },
    { day: 'Sat', minutes: 60 },
    { day: 'Sun', minutes: 45 },
  ];

  return (
    <div className="space-y-6 max-w-7xl mx-auto">
      {/* Welcome & Level Bar */}
      <div className="bg-[#101422] border border-[#1A2035] rounded-xl p-6 relative overflow-hidden">
        <div className="flex flex-col md:flex-row md:items-center justify-between gap-4">
          <div>
            <h2 className="text-xl font-bold text-white tracking-tight">
              Welcome back, {user?.displayName || 'Student'}! ⚡
            </h2>
            <p className="text-xs text-zinc-400 mt-1">
              {user?.university ? `${user.university} • ` : ''}
              {user?.major ? `${user.major} • ` : ''}
              Level {user?.level || 1} Focus Scholar
            </p>
          </div>

          {/* Level Progress */}
          <div className="w-full md:w-64 bg-[#0A0C14] border border-[#1A2035] p-3 rounded-lg">
            <div className="flex justify-between items-center text-xs mb-1.5 font-semibold">
              <span className="text-emerald-400 flex items-center gap-1">
                <Sparkles className="w-3.5 h-3.5" />
                Level {user?.level || 1}
              </span>
              <span className="text-zinc-400 font-mono text-[11px]">
                {user?.totalXp || 0} XP
              </span>
            </div>
            <div className="w-full h-2 rounded-full bg-[#131827] overflow-hidden">
              <div
                className="h-full bg-gradient-to-r from-emerald-500 to-indigo-500 rounded-full transition-all"
                style={{
                  width: `${Math.min(
                    100,
                    user?.xpForNextLevel
                      ? Math.round(((user?.xpProgressInLevel || 0) / user.xpForNextLevel) * 100)
                      : 65
                  )}%`,
                }}
              />
            </div>
          </div>
        </div>
      </div>

      {/* 4 Stat Cards */}
      <div className="grid grid-cols-1 sm:grid-cols-2 lg:grid-cols-4 gap-4">
        {stats.map((s, idx) => {
          const Icon = s.icon;
          return (
            <div key={idx} className="bg-[#101422] border border-[#1A2035] rounded-xl p-4 flex flex-col justify-between">
              <div className="flex items-center justify-between">
                <span className="text-xs font-semibold text-zinc-400">{s.label}</span>
                <div className={`p-2 rounded-lg ${s.bg} border ${s.border}`}>
                  <Icon className={`w-4 h-4 ${s.color}`} />
                </div>
              </div>
              <div className="mt-3">
                <p className="text-2xl font-black text-white font-mono tracking-tight">{s.value}</p>
                <p className="text-[11px] text-zinc-400 mt-0.5">{s.sub}</p>
              </div>
            </div>
          );
        })}
      </div>

      {/* Main Grid: Active Room & Next Task */}
      <div className="grid grid-cols-1 lg:grid-cols-3 gap-6">
        {/* Left: Active Room Card or Quick Join */}
        <div className="lg:col-span-2 bg-[#101422] border border-[#1A2035] rounded-xl p-6">
          <div className="flex items-center justify-between mb-4">
            <h3 className="text-sm font-bold text-white flex items-center gap-2">
              <Radio className="w-4 h-4 text-emerald-400 animate-pulse" />
              Live Study Squad
            </h3>
            {data?.activeRoom && (
              <span className="text-xs font-mono px-2 py-0.5 rounded bg-emerald-500/10 text-emerald-300 border border-emerald-500/20">
                Code: {data.activeRoom.code}
              </span>
            )}
          </div>

          {data?.activeRoom ? (
            <div className="bg-[#0A0C14] border border-emerald-500/20 rounded-xl p-5">
              <div className="flex flex-col sm:flex-row sm:items-center justify-between gap-4">
                <div>
                  <span className="inline-block px-2 py-0.5 rounded text-[10px] font-bold uppercase tracking-wider bg-emerald-500/20 text-emerald-300 border border-emerald-500/30 mb-2">
                    Phase: {data.activeRoom.phase}
                  </span>
                  <h4 className="text-base font-bold text-white">{data.activeRoom.name}</h4>
                  <p className="text-xs text-zinc-400 mt-1">
                    Host: {data.activeRoom.hostName} • {data.activeRoom.memberCount || data.activeRoom.members?.length || 1} members active
                  </p>
                </div>
                <button
                  onClick={() => navigate(`/rooms/${data.activeRoom?.code}`)}
                  className="px-4 py-2 rounded-lg bg-emerald-600 hover:bg-emerald-500 text-white text-xs font-bold flex items-center gap-1.5 shadow-md shadow-emerald-950/50 transition-colors self-start sm:self-auto"
                >
                  <span>Resume Session</span>
                  <ArrowUpRight className="w-4 h-4" />
                </button>
              </div>
            </div>
          ) : (
            <div className="bg-[#0A0C14] border border-[#1A2035] rounded-xl p-6 text-center">
              <p className="text-sm font-semibold text-zinc-300">No active focus room right now</p>
              <p className="text-xs text-zinc-400 mt-1 max-w-sm mx-auto">
                Create a synchronized study room or join your peers with a 6-character code.
              </p>
              <div className="mt-4 flex justify-center gap-3">
                <button
                  onClick={() => navigate('/rooms')}
                  className="px-4 py-2 rounded-lg bg-[#131827] hover:bg-[#182035] text-zinc-200 text-xs font-semibold border border-[#1A2035]"
                >
                  Explore Rooms
                </button>
              </div>
            </div>
          )}

          {/* Weekly Velocity Chart */}
          <div className="mt-6 pt-6 border-t border-[#1A2035]">
            <h4 className="text-xs font-bold text-zinc-400 uppercase tracking-wider mb-4">
              Focus Minutes This Week
            </h4>
            <div className="h-44 w-full">
              <ResponsiveContainer width="100%" height="100%">
                <BarChart data={chartData} margin={{ top: 5, right: 10, left: -20, bottom: 0 }}>
                  <XAxis dataKey="day" stroke="#52525b" fontSize={11} tickLine={false} />
                  <YAxis stroke="#52525b" fontSize={11} tickLine={false} />
                  <Tooltip
                    contentStyle={{ backgroundColor: '#101422', borderColor: '#1A2035', borderRadius: '8px', fontSize: '12px' }}
                    labelStyle={{ color: '#e4e4e7', fontWeight: 600 }}
                  />
                  <Bar dataKey="minutes" fill="#10B981" radius={[4, 4, 0, 0]} />
                </BarChart>
              </ResponsiveContainer>
            </div>
          </div>
        </div>

        {/* Right: Next Up Task & Recent Badges */}
        <div className="space-y-6">
          {/* Priority Task */}
          <div className="bg-[#101422] border border-[#1A2035] rounded-xl p-5">
            <div className="flex items-center justify-between mb-3">
              <h3 className="text-xs font-bold text-zinc-400 uppercase tracking-wider flex items-center gap-1.5">
                <CheckSquare className="w-4 h-4 text-indigo-400" />
                Priority Target
              </h3>
              <button
                onClick={() => navigate('/tasks')}
                className="text-xs text-emerald-400 hover:underline font-semibold"
              >
                View all
              </button>
            </div>

            {data?.nextTask ? (
              <div className="bg-[#0A0C14] border border-[#1A2035] rounded-lg p-3.5">
                <div className="flex items-start gap-3">
                  <button
                    onClick={() => handleCompleteTask(data.nextTask!.id)}
                    className="mt-0.5 w-5 h-5 rounded border border-zinc-500 hover:border-emerald-400 hover:bg-emerald-500/20 flex items-center justify-center transition-colors"
                  >
                    <CheckCircle2 className="w-3.5 h-3.5 text-transparent hover:text-emerald-400" />
                  </button>
                  <div className="flex-1 min-w-0">
                    <p className="text-xs font-bold text-white truncate">{data.nextTask.title}</p>
                    <p className="text-[11px] text-zinc-400 mt-0.5 line-clamp-2">
                      {data.nextTask.description || 'High focus priority task'}
                    </p>
                    <div className="mt-2 flex items-center gap-2">
                      <span className="text-[10px] font-mono px-1.5 py-0.5 rounded bg-amber-500/10 text-amber-300 border border-amber-500/20">
                        {data.nextTask.priority}
                      </span>
                      {data.nextTask.estimatedMinutes && (
                        <span className="text-[10px] text-zinc-400 font-mono">
                          ~{data.nextTask.estimatedMinutes}m
                        </span>
                      )}
                    </div>
                  </div>
                </div>
              </div>
            ) : (
              <div className="bg-[#0A0C14] border border-[#1A2035] rounded-lg p-4 text-center">
                <p className="text-xs text-zinc-400">All caught up! Add a new task in Tasks.</p>
              </div>
            )}
          </div>

          {/* Recent Achievements */}
          <div className="bg-[#101422] border border-[#1A2035] rounded-xl p-5">
            <div className="flex items-center justify-between mb-3">
              <h3 className="text-xs font-bold text-zinc-400 uppercase tracking-wider flex items-center gap-1.5">
                <Trophy className="w-4 h-4 text-amber-400" />
                Unlocked Milestones
              </h3>
              <button
                onClick={() => navigate('/achievements')}
                className="text-xs text-emerald-400 hover:underline font-semibold"
              >
                Leaderboard
              </button>
            </div>

            <div className="space-y-2">
              {data?.recentAchievements && data.recentAchievements.length > 0 ? (
                data.recentAchievements.slice(0, 3).map((ach) => (
                  <div
                    key={ach.id}
                    className="flex items-center gap-3 p-2.5 rounded-lg bg-[#0A0C14] border border-[#1A2035]"
                  >
                    <div className="w-8 h-8 rounded-lg bg-amber-500/10 border border-amber-500/30 flex items-center justify-center text-sm font-bold text-amber-300">
                      🏆
                    </div>
                    <div className="flex-1 min-w-0">
                      <p className="text-xs font-bold text-white truncate">{ach.name}</p>
                      <p className="text-[10px] text-zinc-400 truncate">{ach.description}</p>
                    </div>
                    <span className="text-[10px] font-mono font-bold text-emerald-400">
                      +{ach.xpReward} XP
                    </span>
                  </div>
                ))
              ) : (
                <div className="p-3 bg-[#0A0C14] rounded-lg border border-[#1A2035] text-center text-xs text-zinc-400">
                  Complete your first focus session to unlock achievements!
                </div>
              )}
            </div>
          </div>
        </div>
      </div>
    </div>
  );
};
