import React from 'react';
import { NavLink, useNavigate } from 'react-router-dom';
import {
  LayoutDashboard,
  Timer,
  CheckSquare,
  BarChart3,
  Trophy,
  Settings,
  Flame,
  LogOut,
  Radio,
} from 'lucide-react';
import { useAuthStore } from '../../stores/authStore';
import { useRoomStore } from '../../stores/roomStore';

export const Sidebar: React.FC = () => {
  const { user, logout } = useAuthStore();
  const { room } = useRoomStore();
  const navigate = useNavigate();

  const handleLogout = async () => {
    await logout();
    navigate('/login');
  };

  const navItems = [
    { to: '/dashboard', label: 'Dashboard', icon: LayoutDashboard },
    { to: room ? `/rooms/${room.code}` : '/rooms', label: 'Focus Room', icon: Timer, badge: room ? 'LIVE' : undefined },
    { to: '/tasks', label: 'Tasks', icon: CheckSquare },
    { to: '/analytics', label: 'Analytics', icon: BarChart3 },
    { to: '/achievements', label: 'Achievements', icon: Trophy },
    { to: '/settings', label: 'Settings', icon: Settings },
  ];

  return (
    <aside className="w-64 bg-[#101422] border-r border-[#1A2035] flex flex-col h-screen select-none sticky top-0 shrink-0">
      {/* Brand */}
      <div className="h-16 flex items-center px-6 border-b border-[#1A2035] gap-3">
        <div className="w-8 h-8 rounded-lg bg-emerald-500/10 border border-emerald-500/30 flex items-center justify-center text-emerald-400 font-black">
          ⚡
        </div>
        <div className="flex flex-col">
          <span className="font-bold text-white tracking-wide text-base">FocusForge</span>
          <span className="text-[10px] text-zinc-400 font-mono tracking-wider uppercase">Multiplayer Study</span>
        </div>
      </div>

      {/* Nav Links */}
      <nav className="flex-1 px-3 py-4 space-y-1.5 overflow-y-auto">
        {navItems.map((item) => {
          const Icon = item.icon;
          return (
            <NavLink
              key={item.to}
              to={item.to}
              className={({ isActive }) =>
                `flex items-center gap-3 px-3.5 py-2.5 rounded-lg text-sm font-medium transition-all ${
                  isActive
                    ? 'bg-emerald-500/10 text-emerald-400 border border-emerald-500/20 shadow-sm shadow-emerald-950/40'
                    : 'text-zinc-400 hover:text-zinc-200 hover:bg-[#131827]'
                }`
              }
            >
              <Icon className="w-4 h-4" />
              <span className="flex-1">{item.label}</span>
              {item.badge && (
                <span className="inline-flex items-center px-1.5 py-0.5 rounded text-[10px] font-bold bg-emerald-500/20 text-emerald-300 border border-emerald-500/30 animate-pulse">
                  {item.badge}
                </span>
              )}
            </NavLink>
          );
        })}
      </nav>

      {/* Active Room Mini-widget (if in room) */}
      {room && (
        <div className="p-3 mx-3 mb-3 rounded-lg bg-[#131827] border border-emerald-500/30">
          <div className="flex items-center justify-between text-xs text-emerald-400 mb-1 font-semibold">
            <span className="flex items-center gap-1.5">
              <Radio className="w-3 h-3 text-emerald-400 animate-pulse" />
              Room Active
            </span>
            <span className="font-mono text-[11px] bg-emerald-500/10 px-1.5 py-0.5 rounded border border-emerald-500/20">
              {room.code}
            </span>
          </div>
          <p className="text-xs text-zinc-300 font-medium truncate">{room.name}</p>
          <div className="mt-2 flex items-center justify-between text-[11px] text-zinc-400 font-mono">
            <span>Phase: <strong className="text-white">{room.phase}</strong></span>
            <span>{room.memberCount || room.members?.length || 1} online</span>
          </div>
        </div>
      )}

      {/* User Section & Logout */}
      <div className="p-3 border-t border-[#1A2035] bg-[#0E121E]">
        <div className="flex items-center gap-3 px-2 py-2">
          <div className="relative">
            <div className="w-9 h-9 rounded-full bg-emerald-500/20 border border-emerald-500/40 flex items-center justify-center text-sm font-bold text-emerald-300">
              {user?.displayName ? user.displayName.slice(0, 2).toUpperCase() : 'U'}
            </div>
            <div className="absolute -bottom-0.5 -right-0.5 w-3.5 h-3.5 rounded-full bg-emerald-500 border-2 border-[#101422]" />
          </div>
          <div className="flex-1 min-w-0">
            <div className="flex items-center gap-1.5">
              <p className="text-xs font-semibold text-white truncate">{user?.displayName || 'Student'}</p>
              <span className="text-[10px] font-mono px-1 py-0.2 rounded bg-indigo-500/20 text-indigo-300 border border-indigo-500/30">
                Lv.{user?.level || 1}
              </span>
            </div>
            <p className="text-[11px] text-zinc-400 truncate flex items-center gap-1 mt-0.5 font-mono">
              <Flame className="w-3 h-3 text-amber-500" />
              {user?.streakDays || 0}d streak
            </p>
          </div>
          <button
            onClick={handleLogout}
            title="Log Out"
            className="p-1.5 text-zinc-400 hover:text-rose-400 hover:bg-rose-500/10 rounded-md transition-colors"
          >
            <LogOut className="w-4 h-4" />
          </button>
        </div>
      </div>
    </aside>
  );
};
