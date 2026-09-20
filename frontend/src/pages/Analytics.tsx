import React, { useEffect, useState } from 'react';
import {
  BarChart3,
  Clock,
  CheckCircle2,
  Sparkles,
  TrendingUp,
} from 'lucide-react';
import { ResponsiveContainer, BarChart, Bar, XAxis, YAxis, Tooltip, CartesianGrid } from 'recharts';
import api from '../lib/api';
import type { AnalyticsStat, FocusSession } from '../types';

interface AnalyticsPayload {
  period: string;
  totalFocusMinutes: number;
  totalSessions: number;
  totalCyclesCompleted: number;
  averageSessionMinutes: number;
  totalXpEarned: number;
  dailyStats: AnalyticsStat[];
  recentSessions: FocusSession[];
}

export const Analytics: React.FC = () => {
  const [period, setPeriod] = useState<'THIS_WEEK' | 'LAST_WEEK'>('THIS_WEEK');
  const [data, setData] = useState<AnalyticsPayload | null>(null);
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    fetchAnalytics(period);
  }, [period]);

  const fetchAnalytics = async (selectedPeriod: string) => {
    setLoading(true);
    try {
      const res = await api.get<AnalyticsPayload>('/analytics/me', {
        params: { period: selectedPeriod },
      });
      setData(res.data);
    } catch (err) {
      console.error('Failed to load analytics', err);
    } finally {
      setLoading(false);
    }
  };

  const chartData = data?.dailyStats?.map((s) => ({
    day: s.dayLabel || s.date,
    minutes: s.focusMinutes,
    sessions: s.sessionsCount,
    xp: s.xpEarned,
  })) || [];

  return (
    <div className="max-w-6xl mx-auto space-y-6">
      {/* Header & Period Toggle */}
      <div className="flex flex-col sm:flex-row sm:items-center justify-between gap-4">
        <div>
          <h2 className="text-base font-bold text-white flex items-center gap-2">
            <BarChart3 className="w-5 h-5 text-emerald-400" />
            Productivity & Focus Analytics
          </h2>
          <p className="text-xs text-zinc-400 mt-0.5">
            Deep dive into your weekly study volume, Pomodoro cadence, and focus velocity.
          </p>
        </div>

        <div className="flex items-center gap-1 bg-[#101422] border border-[#1A2035] p-1 rounded-lg self-start sm:self-auto">
          <button
            onClick={() => setPeriod('THIS_WEEK')}
            className={`px-3 py-1.5 rounded-md text-xs font-semibold transition-colors ${
              period === 'THIS_WEEK'
                ? 'bg-emerald-500/10 text-emerald-400 border border-emerald-500/20'
                : 'text-zinc-400 hover:text-zinc-200'
            }`}
          >
            This Week
          </button>
          <button
            onClick={() => setPeriod('LAST_WEEK')}
            className={`px-3 py-1.5 rounded-md text-xs font-semibold transition-colors ${
              period === 'LAST_WEEK'
                ? 'bg-emerald-500/10 text-emerald-400 border border-emerald-500/20'
                : 'text-zinc-400 hover:text-zinc-200'
            }`}
          >
            Last Week
          </button>
        </div>
      </div>

      {/* Hero 4 Metrics */}
      <div className="grid grid-cols-1 sm:grid-cols-2 lg:grid-cols-4 gap-4">
        <div className="bg-[#101422] border border-[#1A2035] rounded-xl p-4">
          <div className="flex items-center justify-between">
            <span className="text-xs font-semibold text-zinc-400">Total Focus Time</span>
            <div className="p-2 rounded-lg bg-emerald-500/10 border border-emerald-500/20">
              <Clock className="w-4 h-4 text-emerald-400" />
            </div>
          </div>
          <p className="text-2xl font-black text-white font-mono mt-3">
            {data?.totalFocusMinutes || 0}m
          </p>
          <p className="text-[11px] text-zinc-400 mt-0.5">
            ≈ {((data?.totalFocusMinutes || 0) / 60).toFixed(1)} hours study
          </p>
        </div>

        <div className="bg-[#101422] border border-[#1A2035] rounded-xl p-4">
          <div className="flex items-center justify-between">
            <span className="text-xs font-semibold text-zinc-400">Pomodoro Cycles</span>
            <div className="p-2 rounded-lg bg-indigo-500/10 border border-indigo-500/20">
              <CheckCircle2 className="w-4 h-4 text-indigo-400" />
            </div>
          </div>
          <p className="text-2xl font-black text-white font-mono mt-3">
            {data?.totalCyclesCompleted || 0}
          </p>
          <p className="text-[11px] text-zinc-400 mt-0.5">Across {data?.totalSessions || 0} sessions</p>
        </div>

        <div className="bg-[#101422] border border-[#1A2035] rounded-xl p-4">
          <div className="flex items-center justify-between">
            <span className="text-xs font-semibold text-zinc-400">Avg. Session Duration</span>
            <div className="p-2 rounded-lg bg-sky-500/10 border border-sky-500/20">
              <TrendingUp className="w-4 h-4 text-sky-400" />
            </div>
          </div>
          <p className="text-2xl font-black text-white font-mono mt-3">
            {data?.averageSessionMinutes ? Math.round(data.averageSessionMinutes) : 0}m
          </p>
          <p className="text-[11px] text-zinc-400 mt-0.5">Optimal deep focus span</p>
        </div>

        <div className="bg-[#101422] border border-[#1A2035] rounded-xl p-4">
          <div className="flex items-center justify-between">
            <span className="text-xs font-semibold text-zinc-400">Total XP Earned</span>
            <div className="p-2 rounded-lg bg-amber-500/10 border border-amber-500/20">
              <Sparkles className="w-4 h-4 text-amber-400" />
            </div>
          </div>
          <p className="text-2xl font-black text-white font-mono mt-3">
            +{data?.totalXpEarned || 0}
          </p>
          <p className="text-[11px] text-zinc-400 mt-0.5">Account level progression</p>
        </div>
      </div>

      {/* Main Chart Card */}
      <div className="bg-[#101422] border border-[#1A2035] rounded-xl p-6">
        <h3 className="text-xs font-bold text-zinc-400 uppercase tracking-wider mb-4">
          Daily Focus Distribution ({period === 'THIS_WEEK' ? 'Current Week' : 'Previous Week'})
        </h3>

        <div className="h-64 w-full">
          <ResponsiveContainer width="100%" height="100%">
            <BarChart data={chartData} margin={{ top: 10, right: 10, left: -20, bottom: 0 }}>
              <CartesianGrid stroke="#1A2035" strokeDasharray="3 3" vertical={false} />
              <XAxis dataKey="day" stroke="#52525b" fontSize={11} tickLine={false} />
              <YAxis stroke="#52525b" fontSize={11} tickLine={false} />
              <Tooltip
                contentStyle={{
                  backgroundColor: '#101422',
                  borderColor: '#1A2035',
                  borderRadius: '8px',
                  fontSize: '12px',
                }}
                labelStyle={{ color: '#e4e4e7', fontWeight: 600 }}
              />
              <Bar dataKey="minutes" fill="#10B981" radius={[4, 4, 0, 0]} name="Focus Minutes" />
            </BarChart>
          </ResponsiveContainer>
        </div>
      </div>

      {/* Recent Sessions Table */}
      <div className="bg-[#101422] border border-[#1A2035] rounded-xl p-6">
        <h3 className="text-xs font-bold text-zinc-400 uppercase tracking-wider mb-4">
          Recent Focus Sessions Log
        </h3>

        {loading ? (
          <div className="flex justify-center py-6">
            <div className="w-6 h-6 border-2 border-emerald-500 border-t-transparent rounded-full animate-spin" />
          </div>
        ) : !data?.recentSessions || data.recentSessions.length === 0 ? (
          <p className="text-xs text-zinc-400 text-center py-4">No sessions recorded for this period yet.</p>
        ) : (
          <div className="overflow-x-auto">
            <table className="w-full text-left text-xs">
              <thead className="text-[11px] font-mono uppercase text-zinc-500 border-b border-[#1A2035]">
                <tr>
                  <th className="pb-3">Date</th>
                  <th className="pb-3">Session / Room</th>
                  <th className="pb-3">Duration</th>
                  <th className="pb-3">Cycles</th>
                  <th className="pb-3 text-right">XP Earned</th>
                </tr>
              </thead>
              <tbody className="divide-y divide-[#1A2035]">
                {data.recentSessions.map((s) => (
                  <tr key={s.id} className="hover:bg-[#131827] transition-colors">
                    <td className="py-3 font-mono text-zinc-400">{s.sessionDate}</td>
                    <td className="py-3 font-semibold text-white">
                      {s.roomName || 'Solo Focus Session'}
                      {s.roomCode && (
                        <span className="ml-2 font-mono text-[10px] px-1.5 py-0.2 rounded bg-[#0A0C14] text-emerald-400 border border-[#1A2035]">
                          {s.roomCode}
                        </span>
                      )}
                    </td>
                    <td className="py-3 font-mono text-emerald-400 font-bold">
                      {s.focusMinutesCompleted} mins
                    </td>
                    <td className="py-3 font-mono text-zinc-300">{s.cyclesCompleted}</td>
                    <td className="py-3 font-mono text-amber-400 font-bold text-right">
                      +{s.xpEarned} XP
                    </td>
                  </tr>
                ))}
              </tbody>
            </table>
          </div>
        )}
      </div>
    </div>
  );
};
