import React, { useEffect } from 'react';
import { Outlet, useLocation } from 'react-router-dom';
import { Sidebar } from './Sidebar';
import { Topbar } from './Topbar';
import { useAuthStore } from '../../stores/authStore';
import { useRoomStore } from '../../stores/roomStore';
import api from '../../lib/api';
import type { Room } from '../../types';

export const AppLayout: React.FC = () => {
  const { loadUser } = useAuthStore();
  const { setRoom } = useRoomStore();
  const location = useLocation();

  useEffect(() => {
    loadUser();

    // Check if user has an active room
    api.get<Room>('/rooms/my-active')
      .then((res) => {
        if (res.data) {
          setRoom(res.data);
        }
      })
      .catch(() => {});
  }, []);

  const getPageMeta = () => {
    const p = location.pathname;
    if (p.startsWith('/rooms')) return { title: 'Focus Room', subtitle: 'Live collaborative Pomodoro session' };
    if (p.startsWith('/tasks')) return { title: 'Task Backlog & Sprints', subtitle: 'Organize priorities and focus velocity' };
    if (p.startsWith('/analytics')) return { title: 'Productivity Analytics', subtitle: 'Weekly focus velocity & session breakdowns' };
    if (p.startsWith('/achievements')) return { title: 'Achievements & Squad Leaderboard', subtitle: 'Milestones, XP, and weekly rankings' };
    if (p.startsWith('/settings')) return { title: 'Preferences & Academic Profile', subtitle: 'Interface density, audio, and pod affiliations' };
    return { title: 'Overview Dashboard', subtitle: 'Personal focus analytics and study squad' };
  };

  const { title, subtitle } = getPageMeta();

  return (
    <div className="min-h-screen bg-[#0A0C14] text-zinc-100 flex font-sans antialiased selection:bg-emerald-500/30 selection:text-emerald-200">
      <Sidebar />
      <div className="flex-1 flex flex-col min-w-0">
        <Topbar title={title} subtitle={subtitle} />
        <main className="flex-1 overflow-y-auto p-6 md:p-8">
          <Outlet />
        </main>
      </div>
    </div>
  );
};
