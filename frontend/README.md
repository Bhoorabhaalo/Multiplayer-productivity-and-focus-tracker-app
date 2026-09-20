# FocusForge — Frontend

React 19 + TypeScript + Vite frontend for the FocusForge multiplayer productivity app.

## Stack

| Tool | Purpose |
|---|---|
| React 19 | UI framework |
| TypeScript 5 | Type safety |
| Vite 8 | Dev server & bundler |
| React Router v7 | Client-side routing |
| Zustand | Global state management |
| Axios | HTTP client |
| STOMP.js + SockJS | WebSocket real-time layer |
| Recharts | Analytics charts |

## Development

```bash
npm install
npm run dev        # http://localhost:5173
```

## Build

```bash
npm run build      # outputs to dist/
npm run preview    # preview the production build locally
```

## Folder Layout

```
src/
├── components/    # Reusable UI components
├── hooks/         # Custom hooks (useTimer, useWebSocket, useAuth)
├── lib/           # Axios instance & API helper functions
├── pages/         # Route pages (Auth, Dashboard, FocusRoom, …)
├── stores/        # Zustand global stores (auth, room, session)
└── types/         # Shared TypeScript interfaces
```

## Environment

The frontend proxies `/api` requests to the backend at `http://localhost:8080` via the Vite dev server config. No `.env` file is required for local development.

> See the root [README](../README.md) for full project documentation.
