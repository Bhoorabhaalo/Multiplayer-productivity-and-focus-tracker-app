/** @type {import('tailwindcss').Config} */
export default {
  content: [
    "./index.html",
    "./src/**/*.{js,ts,jsx,tsx}",
  ],
  theme: {
    extend: {
      colors: {
        // Obsidian Focus design system
        canvas:    '#0A0C14',
        card:      '#101422',
        surface:   '#131827',
        border:    'rgba(255,255,255,0.07)',
        emerald:   { DEFAULT: '#10B981', light: '#34D399', dark: '#059669' },
        amber:     { DEFAULT: '#F59E0B', light: '#FCD34D', dark: '#D97706' },
        indigo:    { DEFAULT: '#5B63E6', light: '#818CF8', dark: '#4338CA' },
        muted:     '#6B7280',
        subtle:    '#374151',
        text:      { primary: '#F9FAFB', secondary: '#9CA3AF', tertiary: '#6B7280' },
      },
      fontFamily: {
        sans:  ['"Plus Jakarta Sans"', '"Inter"', 'sans-serif'],
        mono:  ['"JetBrains Mono"', 'monospace'],
      },
      fontVariantNumeric: {
        tabular: 'tabular-nums',
      },
      borderRadius: {
        '2xl': '1rem',
        '3xl': '1.5rem',
      },
      boxShadow: {
        card: '0 0 0 1px rgba(255,255,255,0.07)',
        glow: '0 0 20px rgba(16,185,129,0.25)',
        'glow-amber': '0 0 20px rgba(245,158,11,0.25)',
      },
      animation: {
        'pulse-slow': 'pulse 3s cubic-bezier(0.4, 0, 0.6, 1) infinite',
        'fade-in': 'fadeIn 0.2s ease-out',
        'slide-up': 'slideUp 0.3s ease-out',
      },
      keyframes: {
        fadeIn: { from: { opacity: '0' }, to: { opacity: '1' } },
        slideUp: { from: { opacity: '0', transform: 'translateY(8px)' }, to: { opacity: '1', transform: 'translateY(0)' } },
      },
    },
  },
  plugins: [],
}
