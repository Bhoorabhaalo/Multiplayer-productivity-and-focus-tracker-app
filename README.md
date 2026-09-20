<h1 align="center">
  <br>
  🎯 FocusForge
  <br>
</h1>

<h4 align="center">A multiplayer productivity & focus-tracking web app for students — study together, stay accountable.</h4>

<p align="center">
  <img alt="Java" src="https://img.shields.io/badge/Java-17-ED8B00?style=for-the-badge&logo=openjdk&logoColor=white"/>
  <img alt="Spring Boot" src="https://img.shields.io/badge/Spring_Boot-3.x-6DB33F?style=for-the-badge&logo=spring&logoColor=white"/>
  <img alt="React" src="https://img.shields.io/badge/React-19-61DAFB?style=for-the-badge&logo=react&logoColor=black"/>
  <img alt="TypeScript" src="https://img.shields.io/badge/TypeScript-5.x-3178C6?style=for-the-badge&logo=typescript&logoColor=white"/>
  <img alt="WebSocket" src="https://img.shields.io/badge/WebSocket-STOMP-8BC34A?style=for-the-badge"/>
</p>

<p align="center">
  <a href="#-features">Features</a> •
  <a href="#%EF%B8%8F-tech-stack">Tech Stack</a> •
  <a href="#-project-structure">Structure</a> •
  <a href="#-getting-started">Getting Started</a> •
  <a href="#-api-reference">API</a> •
  <a href="#-demo-accounts">Demo</a>
</p>

---

## ✨ Features

| Feature | Description |
|---|---|
| 🔐 **JWT Authentication** | Secure register / login with stateless JWT tokens |
| 🏠 **Focus Rooms** | Create or join shared study rooms via a 6-char invite code |
| ⏱ **Pomodoro Timer** | 25-min focus / 5-min break cycles tracked per session |
| 👥 **Live Presence** | See teammates' real-time focus status via WebSockets (STOMP) |
| ✅ **Task Management** | Create, complete, and track personal tasks inside each room |
| 📊 **Analytics** | Personal charts: daily focus minutes, session counts, streaks |
| 🏆 **Leaderboard** | Weekly and all-time rankings across all users |
| 🎖 **Achievements** | Unlock badges for streaks, session milestones, and room activity |
| 🔥 **Streaks** | Daily study-streak tracking with automatic scheduler resets |
| ⚙️ **Settings** | Update display name, email, password, and notification preferences |

---

## 🛠️ Tech Stack

### Backend
| Layer | Technology |
|---|---|
| Language | Java 17 |
| Framework | Spring Boot 3.x |
| Security | Spring Security + JWT (JJWT) |
| Real-time | Spring WebSocket + STOMP |
| ORM | Spring Data JPA (Hibernate 6) |
| Database (dev) | H2 in-memory |
| Database (prod) | MySQL 8 |
| Build | Maven 3.9 |
| Utilities | Lombok, MapStruct |

### Frontend
| Layer | Technology |
|---|---|
| Language | TypeScript 5 |
| Framework | React 19 |
| Build Tool | Vite 8 |
| Routing | React Router v7 |
| State | Zustand |
| WebSocket | STOMP.js + SockJS |
| Charts | Recharts |
| Styling | Vanilla CSS (custom design system) |
| HTTP | Axios |

---

## 📁 Project Structure

```
focusforge/
├── backend/                        # Spring Boot application
│   └── src/main/java/com/focusforge/
│       ├── config/                 # Security, WebSocket, CORS, seeding
│       ├── controller/             # REST controllers (Auth, Room, Task, …)
│       ├── dto/                    # Request / Response data transfer objects
│       ├── entity/                 # JPA entities (User, Room, Session, …)
│       ├── exception/              # Global exception handler
│       ├── mapper/                 # MapStruct mappers
│       ├── repository/             # Spring Data repositories
│       ├── scheduler/              # Streak reset cron job
│       ├── security/               # JWT filter, provider, UserDetailsService
│       ├── service/                # Business logic
│       └── websocket/              # STOMP message handlers
│
├── frontend/                       # React + Vite application
│   └── src/
│       ├── components/             # Reusable UI components
│       ├── hooks/                  # Custom React hooks (useTimer, useWebSocket, …)
│       ├── lib/                    # Axios instance, API helpers
│       ├── pages/                  # Route-level page components
│       │   ├── Auth.tsx            # Login / Register
│       │   ├── Dashboard.tsx       # Home overview
│       │   ├── FocusRoom.tsx       # Live study room
│       │   ├── Tasks.tsx           # Task manager
│       │   ├── Analytics.tsx       # Charts & stats
│       │   ├── Achievements.tsx    # Badges & streaks
│       │   └── Settings.tsx        # User preferences
│       ├── stores/                 # Zustand global stores
│       └── types/                  # TypeScript interfaces
│
└── docs/                           # Architecture notes & ERD
```

---

## 🚀 Getting Started

### Prerequisites

| Tool | Version |
|---|---|
| Java JDK | 17+ |
| Apache Maven | 3.9+ |
| Node.js | 20+ |
| npm | 10+ |

> **Database:** The default development profile uses an **H2 in-memory database** — no external database setup required. For MySQL (production), see [Production Setup](#production-mysql-setup).

---

### 1 — Clone the Repository

```bash
git clone https://github.com/your-username/focusforge.git
cd focusforge
```

---

### 2 — Start the Backend

```bash
cd backend

# Run with the dev profile (H2 in-memory, auto-seeds demo data)
mvn spring-boot:run -Dspring-boot.run.profiles=dev
```

The API server starts at **http://localhost:8080**.

On first run, the `DataInitializer` bean seeds three demo users, two focus rooms, sample tasks, sessions, achievements, and streaks automatically.

#### Backend Environment Variables (optional overrides)

| Variable | Default | Description |
|---|---|---|
| `JWT_SECRET` | (hardcoded dev key) | 256-bit HMAC secret for JWT signing |
| `SPRING_DATASOURCE_URL` | H2 in-memory | Override for MySQL in prod |
| `SPRING_DATASOURCE_USERNAME` | `sa` | DB username |
| `SPRING_DATASOURCE_PASSWORD` | *(empty)* | DB password |

---

### 3 — Start the Frontend

```bash
cd frontend
npm install
npm run dev
```

The app opens at **http://localhost:5173**.

---

### 4 — Open in Browser

Navigate to [http://localhost:5173](http://localhost:5173) and log in with one of the [demo accounts](#-demo-accounts).

---

## 🔑 Demo Accounts

Three users are seeded automatically in `dev` mode:

| Name | Email | Password | Role |
|---|---|---|---|
| Alex Chen | `alex@focusforge.app` | `Password@123` | Member of Room "CS Study Squad" |
| Sam Rivera | `sam@focusforge.app` | `Password@123` | Member of Room "CS Study Squad" |
| Jordan Park | `jordan@focusforge.app` | `Password@123` | Member of Room "Math Grind" |

---

## 🌐 API Reference

All endpoints are prefixed with `/api/v1`.

### Authentication

| Method | Endpoint | Auth | Description |
|---|---|---|---|
| `POST` | `/auth/register` | ❌ | Create a new account |
| `POST` | `/auth/login` | ❌ | Login; returns JWT |

### Users / Dashboard

| Method | Endpoint | Auth | Description |
|---|---|---|---|
| `GET` | `/dashboard` | ✅ | Current user overview stats |
| `GET` | `/users/me` | ✅ | Full user profile |
| `PUT` | `/users/me` | ✅ | Update profile |

### Focus Rooms

| Method | Endpoint | Auth | Description |
|---|---|---|---|
| `POST` | `/rooms` | ✅ | Create a new room |
| `GET` | `/rooms` | ✅ | List rooms the user belongs to |
| `GET` | `/rooms/{id}` | ✅ | Room detail |
| `POST` | `/rooms/join` | ✅ | Join via invite code |
| `DELETE` | `/rooms/{id}/leave` | ✅ | Leave a room |

### Sessions (Pomodoro)

| Method | Endpoint | Auth | Description |
|---|---|---|---|
| `POST` | `/sessions/start` | ✅ | Start a focus session |
| `POST` | `/sessions/{id}/end` | ✅ | End a session |
| `GET` | `/sessions/history` | ✅ | Past sessions |

### Tasks

| Method | Endpoint | Auth | Description |
|---|---|---|---|
| `GET` | `/tasks` | ✅ | List user's tasks |
| `POST` | `/tasks` | ✅ | Create a task |
| `PUT` | `/tasks/{id}` | ✅ | Update a task |
| `DELETE` | `/tasks/{id}` | ✅ | Delete a task |

### Leaderboard & Achievements

| Method | Endpoint | Auth | Description |
|---|---|---|---|
| `GET` | `/leaderboard` | ✅ | Global leaderboard |
| `GET` | `/achievements` | ✅ | User's unlocked achievements |

### WebSocket

Connect to `ws://localhost:8080/ws` using STOMP over SockJS.

| Destination | Direction | Description |
|---|---|---|
| `/topic/room/{roomId}` | Subscribe | Room-wide events (join/leave/focus) |
| `/app/room/{roomId}/status` | Publish | Broadcast your current focus status |
| `/app/room/{roomId}/timer` | Publish | Sync timer state to teammates |

---

## 🗄️ Database Schema (Key Entities)

```
User ──< Session >── Room
 │                    │
 ├──< Task            └──< RoomMember >── User
 ├──< Streak
 └──< UserAchievement >── Achievement
```

---

## 🏭 Production MySQL Setup

1. Create a MySQL database:
   ```sql
   CREATE DATABASE focusforge CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
   CREATE USER 'focusforge'@'localhost' IDENTIFIED BY 'your_password';
   GRANT ALL PRIVILEGES ON focusforge.* TO 'focusforge'@'localhost';
   ```

2. Set environment variables:
   ```bash
   export SPRING_DATASOURCE_URL=jdbc:mysql://localhost:3306/focusforge
   export SPRING_DATASOURCE_USERNAME=focusforge
   export SPRING_DATASOURCE_PASSWORD=your_password
   export JWT_SECRET=your-256-bit-secret-key-here
   ```

3. Run **without** the `dev` profile:
   ```bash
   mvn spring-boot:run
   ```

---

## 🎓 Academic Context

FocusForge was built as a college-level full-stack project to demonstrate:

- **Java OOP** — Entities, Services, Mappers with clean separation of concerns
- **Spring Boot** — Auto-configuration, dependency injection, AOP
- **REST API design** — Resource-based URLs, HTTP semantics, global error handling
- **JWT Security** — Stateless authentication, filter chain, role-based access
- **WebSockets** — STOMP protocol, real-time bidirectional messaging
- **Relational DB design** — Normalized schema, JPA relationships, transactions
- **React + TypeScript** — Component architecture, hooks, typed API contracts
- **State management** — Zustand stores, optimistic UI updates

---

## 📄 License

This project is for educational purposes. Feel free to use it as a reference or starting point.

---

<p align="center">Made with ❤️ and too many Pomodoros 🍅</p>
