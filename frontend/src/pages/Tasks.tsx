import React, { useEffect, useState } from 'react';
import {
  Plus,
  CheckCircle2,
  Circle,
  Trash2,
  Clock,
  Sparkles,
  Filter,
} from 'lucide-react';
import api from '../lib/api';
import type { Task } from '../types';

interface TodaySummary {
  activeCount: number;
  completedCount: number;
  totalXpEarnedToday: number;
}

export const Tasks: React.FC = () => {
  const [tasks, setTasks] = useState<Task[]>([]);
  const [summary, setSummary] = useState<TodaySummary | null>(null);
  const [loading, setLoading] = useState(true);

  // New task form state
  const [title, setTitle] = useState('');
  const [priority, setPriority] = useState<'LOW' | 'MEDIUM' | 'HIGH'>('MEDIUM');
  const [estimatedMinutes, setEstimatedMinutes] = useState<number>(30);
  const [filter, setFilter] = useState<'ALL' | 'TODO' | 'DONE'>('ALL');
  const [submitting, setSubmitting] = useState(false);
  const [justCompletedId, setJustCompletedId] = useState<number | null>(null);

  useEffect(() => {
    fetchTasks();
    fetchSummary();
  }, []);

  const fetchTasks = async () => {
    try {
      const res = await api.get<Task[]>('/tasks');
      setTasks(res.data);
    } catch (err) {
      console.error('Failed to load tasks', err);
    } finally {
      setLoading(false);
    }
  };

  const fetchSummary = async () => {
    try {
      const res = await api.get<TodaySummary>('/tasks/summary/today');
      setSummary(res.data);
    } catch (err) {
      console.error('Failed to load summary', err);
    }
  };

  const handleCreateTask = async (e: React.FormEvent) => {
    e.preventDefault();
    if (!title.trim()) return;
    setSubmitting(true);
    try {
      const res = await api.post<Task>('/tasks', {
        title: title.trim(),
        priority,
        estimatedMinutes: Number(estimatedMinutes),
      });
      setTasks([res.data, ...tasks]);
      setTitle('');
      fetchSummary();
    } catch (err) {
      console.error('Failed to create task', err);
    } finally {
      setSubmitting(false);
    }
  };

  const handleToggleComplete = async (taskId: number, currentStatus: string) => {
    try {
      if (currentStatus === 'DONE') {
        const res = await api.put<Task>(`/tasks/${taskId}`, { status: 'TODO' });
        setTasks(tasks.map((t) => (t.id === taskId ? res.data : t)));
      } else {
        setJustCompletedId(taskId);
        const res = await api.patch<Task>(`/tasks/${taskId}/complete`);
        setTasks(tasks.map((t) => (t.id === taskId ? res.data : t)));
        setTimeout(() => setJustCompletedId(null), 2500);
      }
      fetchSummary();
    } catch (err) {
      console.error('Failed to toggle task', err);
    }
  };

  const handleDeleteTask = async (taskId: number) => {
    try {
      await api.delete(`/tasks/${taskId}`);
      setTasks(tasks.filter((t) => t.id !== taskId));
      fetchSummary();
    } catch (err) {
      console.error('Failed to delete task', err);
    }
  };

  const filteredTasks = tasks.filter((t) => {
    if (filter === 'TODO') return t.status !== 'DONE';
    if (filter === 'DONE') return t.status === 'DONE';
    return true;
  });

  return (
    <div className="max-w-5xl mx-auto space-y-6">
      {/* Today's Focus Velocity Summary Banner */}
      <div className="bg-gradient-to-r from-[#101422] to-[#131827] border border-[#1A2035] rounded-xl p-5 flex flex-col sm:flex-row sm:items-center justify-between gap-4">
        <div>
          <h2 className="text-base font-bold text-white flex items-center gap-2">
            <Sparkles className="w-4 h-4 text-emerald-400" />
            Task Sprint & Focus Velocity
          </h2>
          <p className="text-xs text-zinc-400 mt-1">
            Complete high-priority academic tasks during focus cycles to earn streak points & level progression.
          </p>
        </div>

        <div className="flex items-center gap-4 bg-[#0A0C14] border border-[#1A2035] px-4 py-2.5 rounded-lg">
          <div>
            <p className="text-[10px] text-zinc-400 font-semibold uppercase">Active</p>
            <p className="text-sm font-bold text-white font-mono">{summary?.activeCount || 0}</p>
          </div>
          <div className="h-6 w-px bg-[#1A2035]" />
          <div>
            <p className="text-[10px] text-zinc-400 font-semibold uppercase">Done Today</p>
            <p className="text-sm font-bold text-emerald-400 font-mono">{summary?.completedCount || 0}</p>
          </div>
          <div className="h-6 w-px bg-[#1A2035]" />
          <div>
            <p className="text-[10px] text-zinc-400 font-semibold uppercase">XP Earned</p>
            <p className="text-sm font-bold text-amber-400 font-mono">+{summary?.totalXpEarnedToday || 0} XP</p>
          </div>
        </div>
      </div>

      {/* Quick Add Bar */}
      <form
        onSubmit={handleCreateTask}
        className="bg-[#101422] border border-[#1A2035] rounded-xl p-3 flex flex-col sm:flex-row items-center gap-3 shadow-lg"
      >
        <input
          type="text"
          placeholder="Add a new priority task (e.g. Read Operating Systems Ch. 4)..."
          value={title}
          onChange={(e) => setTitle(e.target.value)}
          className="flex-1 bg-transparent px-3 py-2 text-sm text-white placeholder-zinc-500 outline-none w-full"
        />

        <div className="flex items-center gap-2 w-full sm:w-auto justify-end">
          <select
            value={priority}
            onChange={(e: any) => setPriority(e.target.value)}
            className="bg-[#0A0C14] border border-[#1A2035] text-xs font-semibold rounded-lg px-2.5 py-2 text-zinc-200 outline-none"
          >
            <option value="LOW">Low Priority</option>
            <option value="MEDIUM">Medium Priority</option>
            <option value="HIGH">High Priority</option>
          </select>

          <div className="flex items-center gap-1 bg-[#0A0C14] border border-[#1A2035] rounded-lg px-2 py-1.5 text-xs text-zinc-400">
            <Clock className="w-3.5 h-3.5" />
            <input
              type="number"
              min={5}
              max={240}
              step={5}
              value={estimatedMinutes}
              onChange={(e) => setEstimatedMinutes(Number(e.target.value))}
              className="w-12 bg-transparent text-white font-mono text-center outline-none"
            />
            <span className="text-[10px]">m</span>
          </div>

          <button
            type="submit"
            disabled={submitting || !title.trim()}
            className="px-4 py-2 rounded-lg bg-emerald-600 hover:bg-emerald-500 disabled:opacity-50 text-white text-xs font-bold flex items-center gap-1.5 shadow-md shadow-emerald-950/40 transition-colors"
          >
            <Plus className="w-4 h-4" />
            <span>Add</span>
          </button>
        </div>
      </form>

      {/* Filter Tabs */}
      <div className="flex items-center justify-between border-b border-[#1A2035] pb-2">
        <div className="flex items-center gap-2">
          <Filter className="w-3.5 h-3.5 text-zinc-400" />
          <span className="text-xs font-bold text-zinc-400 uppercase tracking-wider">Filter:</span>
          {(['ALL', 'TODO', 'DONE'] as const).map((f) => (
            <button
              key={f}
              onClick={() => setFilter(f)}
              className={`px-3 py-1 rounded-md text-xs font-semibold transition-colors ${
                filter === f
                  ? 'bg-emerald-500/10 text-emerald-400 border border-emerald-500/20'
                  : 'text-zinc-400 hover:text-zinc-200'
              }`}
            >
              {f === 'ALL' ? 'All Tasks' : f === 'TODO' ? 'Active' : 'Completed'}
            </button>
          ))}
        </div>
        <span className="text-xs text-zinc-400 font-mono">
          Showing {filteredTasks.length} task{filteredTasks.length === 1 ? '' : 's'}
        </span>
      </div>

      {/* Tasks List */}
      {loading ? (
        <div className="flex justify-center p-8">
          <div className="w-6 h-6 border-2 border-emerald-500 border-t-transparent rounded-full animate-spin" />
        </div>
      ) : filteredTasks.length === 0 ? (
        <div className="bg-[#101422] border border-[#1A2035] rounded-xl p-8 text-center">
          <p className="text-sm font-semibold text-zinc-300">No tasks found</p>
          <p className="text-xs text-zinc-400 mt-1">
            {filter === 'DONE' ? 'No completed tasks yet.' : 'All tasks completed or none created.'}
          </p>
        </div>
      ) : (
        <div className="space-y-2.5">
          {filteredTasks.map((t) => {
            const isDone = t.status === 'DONE';
            const isJustDone = justCompletedId === t.id;

            return (
              <div
                key={t.id}
                className={`bg-[#101422] border rounded-xl p-3.5 flex items-center justify-between gap-3 transition-all ${
                  isDone
                    ? 'border-[#1A2035] opacity-60'
                    : 'border-[#1A2035] hover:border-emerald-500/30'
                } ${isJustDone ? 'ring-2 ring-emerald-500 bg-emerald-500/10' : ''}`}
              >
                <div className="flex items-center gap-3.5 flex-1 min-w-0">
                  <button
                    onClick={() => handleToggleComplete(t.id, t.status)}
                    className="text-zinc-500 hover:text-emerald-400 transition-colors shrink-0"
                  >
                    {isDone ? (
                      <CheckCircle2 className="w-5 h-5 text-emerald-400 fill-emerald-400/20" />
                    ) : (
                      <Circle className="w-5 h-5" />
                    )}
                  </button>

                  <div className="flex-1 min-w-0">
                    <p
                      className={`text-sm font-semibold truncate ${
                        isDone ? 'line-through text-zinc-400' : 'text-zinc-100'
                      }`}
                    >
                      {t.title}
                    </p>
                    {t.description && (
                      <p className="text-xs text-zinc-400 mt-0.5 truncate">{t.description}</p>
                    )}
                  </div>
                </div>

                <div className="flex items-center gap-3 shrink-0">
                  {isJustDone && (
                    <span className="text-xs font-bold text-emerald-400 animate-bounce flex items-center gap-1 font-mono">
                      <Sparkles className="w-3.5 h-3.5" />
                      +25 XP!
                    </span>
                  )}

                  {t.estimatedMinutes && (
                    <span className="text-[11px] text-zinc-400 font-mono flex items-center gap-1">
                      <Clock className="w-3 h-3" />
                      {t.estimatedMinutes}m
                    </span>
                  )}

                  <span
                    className={`text-[10px] font-mono font-bold px-2 py-0.5 rounded border uppercase ${
                      t.priority === 'HIGH'
                        ? 'bg-rose-500/10 text-rose-300 border-rose-500/20'
                        : t.priority === 'MEDIUM'
                        ? 'bg-amber-500/10 text-amber-300 border-amber-500/20'
                        : 'bg-zinc-500/10 text-zinc-300 border-zinc-500/20'
                    }`}
                  >
                    {t.priority}
                  </span>

                  <button
                    onClick={() => handleDeleteTask(t.id)}
                    title="Delete task"
                    className="p-1.5 text-zinc-400 hover:text-rose-400 hover:bg-rose-500/10 rounded-md transition-colors"
                  >
                    <Trash2 className="w-3.5 h-3.5" />
                  </button>
                </div>
              </div>
            );
          })}
        </div>
      )}
    </div>
  );
};
