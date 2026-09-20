import React, { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import { ArrowRight, ShieldCheck, Zap } from 'lucide-react';
import { useAuthStore } from '../stores/authStore';

export const Auth: React.FC = () => {
  const [isRegister, setIsRegister] = useState(false);
  const [email, setEmail] = useState('');
  const [password, setPassword] = useState('');
  const [username, setUsername] = useState('');
  const [displayName, setDisplayName] = useState('');
  const [error, setError] = useState('');
  const [loading, setLoading] = useState(false);

  const { login, register } = useAuthStore();
  const navigate = useNavigate();

  const handleSubmit = async (e: React.FormEvent) => {
    e.preventDefault();
    setError('');
    setLoading(true);
    try {
      if (isRegister) {
        await register(username, email, password, displayName || username);
      } else {
        await login(email, password);
      }
      navigate('/dashboard');
    } catch (err: any) {
      setError(err.response?.data?.message || (isRegister ? 'Registration failed' : 'Invalid email or password'));
    } finally {
      setLoading(false);
    }
  };

  const handleDemoLogin = async (demoEmail: string) => {
    setError('');
    setLoading(true);
    try {
      await login(demoEmail, 'Password@123');
      navigate('/dashboard');
    } catch (err: any) {
      setError(err.response?.data?.message || 'Demo login failed');
    } finally {
      setLoading(false);
    }
  };

  return (
    <div className="min-h-screen bg-[#0A0C14] flex flex-col justify-center items-center p-4 selection:bg-emerald-500/30 selection:text-emerald-200">
      {/* Background glowing gradient orb */}
      <div className="absolute top-1/4 w-96 h-96 bg-emerald-500/10 rounded-full blur-3xl pointer-events-none" />
      <div className="absolute bottom-1/4 w-96 h-96 bg-indigo-500/10 rounded-full blur-3xl pointer-events-none" />

      <div className="relative w-full max-w-md">
        {/* Logo header */}
        <div className="text-center mb-8">
          <div className="inline-flex items-center justify-center w-12 h-12 rounded-xl bg-emerald-500/10 border border-emerald-500/30 text-emerald-400 font-black text-2xl shadow-lg shadow-emerald-950/50 mb-3">
            ⚡
          </div>
          <h1 className="text-2xl font-black text-white tracking-tight">FocusForge</h1>
          <p className="text-xs text-zinc-400 mt-1">Multiplayer Focus Rooms & Productivity Tracking for Students</p>
        </div>

        {/* Card */}
        <div className="bg-[#101422] border border-[#1A2035] rounded-2xl p-6 sm:p-8 shadow-2xl shadow-black/60">
          <div className="flex border-b border-[#1A2035] mb-6">
            <button
              onClick={() => { setIsRegister(false); setError(''); }}
              className={`flex-1 pb-3 text-sm font-bold transition-colors text-center border-b-2 ${
                !isRegister ? 'border-emerald-500 text-white' : 'border-transparent text-zinc-400 hover:text-zinc-200'
              }`}
            >
              Log In
            </button>
            <button
              onClick={() => { setIsRegister(true); setError(''); }}
              className={`flex-1 pb-3 text-sm font-bold transition-colors text-center border-b-2 ${
                isRegister ? 'border-emerald-500 text-white' : 'border-transparent text-zinc-400 hover:text-zinc-200'
              }`}
            >
              Sign Up
            </button>
          </div>

          {error && (
            <div className="mb-4 p-3 rounded-lg bg-rose-500/10 border border-rose-500/30 text-rose-300 text-xs font-medium">
              {error}
            </div>
          )}

          <form onSubmit={handleSubmit} className="space-y-4">
            {isRegister && (
              <>
                <div>
                  <label className="block text-xs font-semibold text-zinc-300 mb-1.5">Username</label>
                  <input
                    type="text"
                    required
                    placeholder="e.g. alexj"
                    value={username}
                    onChange={(e) => setUsername(e.target.value)}
                    className="w-full text-sm bg-[#0A0C14] border border-[#1A2035] focus:border-emerald-500/60 rounded-lg px-3.5 py-2.5 text-white outline-none transition-colors"
                  />
                </div>
                <div>
                  <label className="block text-xs font-semibold text-zinc-300 mb-1.5">Display Name</label>
                  <input
                    type="text"
                    placeholder="e.g. Alex Johnson"
                    value={displayName}
                    onChange={(e) => setDisplayName(e.target.value)}
                    className="w-full text-sm bg-[#0A0C14] border border-[#1A2035] focus:border-emerald-500/60 rounded-lg px-3.5 py-2.5 text-white outline-none transition-colors"
                  />
                </div>
              </>
            )}

            <div>
              <label className="block text-xs font-semibold text-zinc-300 mb-1.5">University Email</label>
              <input
                type="email"
                required
                placeholder="name@university.edu"
                value={email}
                onChange={(e) => setEmail(e.target.value)}
                className="w-full text-sm bg-[#0A0C14] border border-[#1A2035] focus:border-emerald-500/60 rounded-lg px-3.5 py-2.5 text-white outline-none transition-colors"
              />
            </div>

            <div>
              <label className="block text-xs font-semibold text-zinc-300 mb-1.5">Password</label>
              <input
                type="password"
                required
                placeholder="••••••••••••"
                value={password}
                onChange={(e) => setPassword(e.target.value)}
                className="w-full text-sm bg-[#0A0C14] border border-[#1A2035] focus:border-emerald-500/60 rounded-lg px-3.5 py-2.5 text-white outline-none transition-colors"
              />
            </div>

            <button
              type="submit"
              disabled={loading}
              className="w-full mt-2 py-3 rounded-lg bg-emerald-600 hover:bg-emerald-500 disabled:opacity-50 text-white font-bold text-sm shadow-md shadow-emerald-950/40 flex items-center justify-center gap-2 transition-all"
            >
              <span>{loading ? 'Processing...' : isRegister ? 'Create Account' : 'Sign In'}</span>
              <ArrowRight className="w-4 h-4" />
            </button>
          </form>

          {/* Quick 1-Click Demo Logins */}
          <div className="mt-6 pt-5 border-t border-[#1A2035]">
            <div className="flex items-center justify-between mb-3">
              <span className="text-[11px] font-bold text-zinc-400 uppercase tracking-wider flex items-center gap-1">
                <Zap className="w-3 h-3 text-amber-400" />
                1-Click Demo Profiles
              </span>
              <span className="text-[10px] text-zinc-500 font-mono">Password@123</span>
            </div>

            <div className="grid grid-cols-3 gap-2">
              <button
                type="button"
                onClick={() => handleDemoLogin('alex@focusforge.app')}
                disabled={loading}
                className="p-2 rounded-lg bg-[#131827] hover:bg-[#1A2238] border border-[#1A2035] hover:border-emerald-500/30 text-left transition-colors"
              >
                <p className="text-xs font-bold text-zinc-200">Alex J.</p>
                <p className="text-[10px] text-emerald-400 font-mono">Stanford</p>
              </button>

              <button
                type="button"
                onClick={() => handleDemoLogin('sophia@focusforge.app')}
                disabled={loading}
                className="p-2 rounded-lg bg-[#131827] hover:bg-[#1A2238] border border-[#1A2035] hover:border-emerald-500/30 text-left transition-colors"
              >
                <p className="text-xs font-bold text-zinc-200">Sophia K.</p>
                <p className="text-[10px] text-indigo-400 font-mono">MIT</p>
              </button>

              <button
                type="button"
                onClick={() => handleDemoLogin('marcus@focusforge.app')}
                disabled={loading}
                className="p-2 rounded-lg bg-[#131827] hover:bg-[#1A2238] border border-[#1A2035] hover:border-emerald-500/30 text-left transition-colors"
              >
                <p className="text-xs font-bold text-zinc-200">Marcus T.</p>
                <p className="text-[10px] text-amber-400 font-mono">Harvard</p>
              </button>
            </div>
          </div>
        </div>

        {/* Security / feature footnote */}
        <div className="mt-6 flex items-center justify-center gap-2 text-xs text-zinc-400">
          <ShieldCheck className="w-4 h-4 text-emerald-500" />
          <span>Stateless JWT Authentication • Synchronized WebSocket Clock</span>
        </div>
      </div>
    </div>
  );
};
