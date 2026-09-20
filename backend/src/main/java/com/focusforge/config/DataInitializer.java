package com.focusforge.config;

import com.focusforge.entity.*;
import com.focusforge.repository.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

@Component
public class DataInitializer implements CommandLineRunner {
    private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(DataInitializer.class);

    public DataInitializer(UserRepository userRepository, PodRepository podRepository, UserPodRepository userPodRepository, RoomRepository roomRepository, RoomMemberRepository roomMemberRepository, SprintTargetRepository sprintTargetRepository, ChecklistItemRepository checklistItemRepository, TaskRepository taskRepository, AchievementRepository achievementRepository, UserAchievementRepository userAchievementRepository, StreakRepository streakRepository, DailyFocusStatRepository dailyFocusStatRepository, FocusSessionRepository focusSessionRepository, ChatMessageRepository chatMessageRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.podRepository = podRepository;
        this.userPodRepository = userPodRepository;
        this.roomRepository = roomRepository;
        this.roomMemberRepository = roomMemberRepository;
        this.sprintTargetRepository = sprintTargetRepository;
        this.checklistItemRepository = checklistItemRepository;
        this.taskRepository = taskRepository;
        this.achievementRepository = achievementRepository;
        this.userAchievementRepository = userAchievementRepository;
        this.streakRepository = streakRepository;
        this.dailyFocusStatRepository = dailyFocusStatRepository;
        this.focusSessionRepository = focusSessionRepository;
        this.chatMessageRepository = chatMessageRepository;
        this.passwordEncoder = passwordEncoder;
    }



    private final UserRepository userRepository;
    private final PodRepository podRepository;
    private final UserPodRepository userPodRepository;
    private final RoomRepository roomRepository;
    private final RoomMemberRepository roomMemberRepository;
    private final SprintTargetRepository sprintTargetRepository;
    private final ChecklistItemRepository checklistItemRepository;
    private final TaskRepository taskRepository;
    private final AchievementRepository achievementRepository;
    private final UserAchievementRepository userAchievementRepository;
    private final StreakRepository streakRepository;
    private final DailyFocusStatRepository dailyFocusStatRepository;
    private final FocusSessionRepository focusSessionRepository;
    private final ChatMessageRepository chatMessageRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    @Transactional
    public void run(String... args) {
        if (userRepository.count() > 0) {
            log.info("Database already initialized, skipping seed data.");
            return;
        }

        log.info("Seeding FocusForge database with initial reference demo data...");

        String encodedPassword = passwordEncoder.encode("Password@123");

        // 1. Pods
        Pod cs301 = podRepository.save(Pod.builder().code("CS301").label("CS 301").name("Algorithms").build());
        Pod cs350 = podRepository.save(Pod.builder().code("CS350").label("CS 350").name("Operating Systems").build());
        Pod cs401 = podRepository.save(Pod.builder().code("CS401").label("CS 401").name("Distributed Systems").build());
        Pod math240 = podRepository.save(Pod.builder().code("MATH240").label("MATH 240").name("Discrete Mathematics & Proofs").build());
        Pod phys210 = podRepository.save(Pod.builder().code("PHYS210").label("PHYS 210").name("Classical Mechanics").build());

        // 2. Demo Users
        User alex = userRepository.save(User.builder()
                .email("alex@focusforge.app")
                .username("alex")
                .displayName("Alex J. (You)")
                .passwordHash(encodedPassword)
                .university("Stanford University • School of Eng.")
                .major("B.S. Computer Science ('26)")
                .focusStatement("Systems & algorithms enthusiast. Currently preparing for distributed systems exams, kernel architecture projects, and weekly LeetCode contests.")
                .sprintStatus("CS 350 Midterm Grinder 🔥")
                .level(14)
                .totalXp(3420)
                .interfaceDensity(User.InterfaceDensity.BALANCED)
                .soundEffectsEnabled(true)
                .compactSidebar(false)
                .role(User.Role.USER)
                .build());

        User sophia = userRepository.save(User.builder()
                .email("sophia@focusforge.app")
                .username("sophia")
                .displayName("Sophia K.")
                .passwordHash(encodedPassword)
                .university("Stanford University")
                .major("M.S. Computer Science ('25)")
                .sprintStatus("Graph DFS and Topological Sort")
                .level(15)
                .totalXp(4120)
                .build());

        User marcus = userRepository.save(User.builder()
                .email("marcus@focusforge.app")
                .username("marcus")
                .displayName("Marcus T.")
                .passwordHash(encodedPassword)
                .university("UC Berkeley")
                .major("Electrical Engineering & CS")
                .sprintStatus("☕ Short Coffee Break")
                .level(13)
                .totalXp(2890)
                .build());

        User priya = userRepository.save(User.builder()
                .email("priya@focusforge.app")
                .username("priya")
                .displayName("Priya D.")
                .passwordHash(encodedPassword)
                .university("MIT")
                .major("Mathematics & Computation")
                .sprintStatus("Dijkstra Shortest Path Matrix")
                .level(12)
                .totalXp(2450)
                .build());

        User rohan = userRepository.save(User.builder()
                .email("rohan@focusforge.app")
                .username("rohan")
                .displayName("Rohan N.")
                .passwordHash(encodedPassword)
                .university("CMU")
                .major("Computer Science")
                .sprintStatus("B-Tree Balance Properties")
                .level(11)
                .totalXp(2100)
                .build());

        User elena = userRepository.save(User.builder()
                .email("elena@focusforge.app")
                .username("elena")
                .displayName("Elena L.")
                .passwordHash(encodedPassword)
                .university("Harvard University")
                .major("Applied Mathematics")
                .sprintStatus("Master Theorem Proofs")
                .level(10)
                .totalXp(1750)
                .build());

        // 3. User Pod Subscriptions
        userPodRepository.save(UserPod.builder().user(alex).pod(cs301).build());
        userPodRepository.save(UserPod.builder().user(alex).pod(math240).build());
        userPodRepository.save(UserPod.builder().user(alex).pod(cs401).build());

        // 4. Streaks
        streakRepository.save(Streak.builder().userId(alex.getId()).user(alex).currentStreak(5).longestStreak(14).lastFocusDate(LocalDate.now()).build());
        streakRepository.save(Streak.builder().userId(sophia.getId()).user(sophia).currentStreak(7).longestStreak(21).lastFocusDate(LocalDate.now()).build());
        streakRepository.save(Streak.builder().userId(marcus.getId()).user(marcus).currentStreak(4).longestStreak(10).lastFocusDate(LocalDate.now()).build());
        streakRepository.save(Streak.builder().userId(priya.getId()).user(priya).currentStreak(3).longestStreak(8).lastFocusDate(LocalDate.now()).build());
        streakRepository.save(Streak.builder().userId(rohan.getId()).user(rohan).currentStreak(2).longestStreak(6).lastFocusDate(LocalDate.now()).build());
        streakRepository.save(Streak.builder().userId(elena.getId()).user(elena).currentStreak(1).longestStreak(5).lastFocusDate(LocalDate.now()).build());

        // 5. Achievements
        Achievement a1 = achievementRepository.save(Achievement.builder()
                .code("CENTURION_FOCUS")
                .name("Centurion Focus")
                .tier(Achievement.Tier.SILVER)
                .metric(Achievement.Metric.TOTAL_FOCUS_HOURS)
                .targetValue(100)
                .xpReward(250)
                .description("Log 100 deep focus hours during the term")
                .icon("clock")
                .build());

        Achievement a2 = achievementRepository.save(Achievement.builder()
                .code("SPRINT_STREAK_MASTER")
                .name("Sprint Streak Master")
                .tier(Achievement.Tier.GOLD)
                .metric(Achievement.Metric.STREAK_DAYS)
                .targetValue(7)
                .xpReward(200)
                .description("Maintain a 7-day uninterrupted focus streak")
                .icon("flame")
                .build());

        Achievement a3 = achievementRepository.save(Achievement.builder()
                .code("NIGHT_OWL_SCHOLAR")
                .name("Night Owl Scholar")
                .tier(Achievement.Tier.NONE)
                .metric(Achievement.Metric.MIDNIGHT_SESSIONS)
                .targetValue(10)
                .xpReward(150)
                .description("Complete 10 midnight focus sprints (10 / 10 completed)")
                .icon("moon")
                .build());

        Achievement a4 = achievementRepository.save(Achievement.builder()
                .code("SQUAD_PILLAR")
                .name("Squad Pillar")
                .tier(Achievement.Tier.BRONZE)
                .metric(Achievement.Metric.ROOMS_HOSTED)
                .targetValue(15)
                .xpReward(300)
                .description("Host 15 group study rooms for fellow scholars")
                .icon("users")
                .build());

        Achievement a5 = achievementRepository.save(Achievement.builder()
                .code("DEEP_WORK_FLOW")
                .name("Deep Work Flow")
                .tier(Achievement.Tier.NONE)
                .metric(Achievement.Metric.LONGEST_SESSION_MINUTES)
                .targetValue(120)
                .xpReward(180)
                .description("Log a single uninterrupted 2-hour session")
                .icon("zap")
                .build());

        // Alex's Achievements
        userAchievementRepository.save(UserAchievement.builder().user(alex).achievement(a1).progressValue(84).build());
        userAchievementRepository.save(UserAchievement.builder().user(alex).achievement(a2).progressValue(5).build());
        userAchievementRepository.save(UserAchievement.builder().user(alex).achievement(a3).progressValue(10).unlockedAt(LocalDateTime.now().minusDays(2)).build());
        userAchievementRepository.save(UserAchievement.builder().user(alex).achievement(a4).progressValue(12).build());
        userAchievementRepository.save(UserAchievement.builder().user(alex).achievement(a5).progressValue(120).unlockedAt(LocalDateTime.now().minusDays(5)).build());

        // 6. Tasks (Matching Screenshot 5)
        LocalDateTime today = LocalDateTime.now();
        taskRepository.save(Task.builder()
                .user(alex)
                .title("Implement Dynamic Programming LeetCode #72 (Edit Distance)")
                .pod(cs301)
                .priority(Task.Priority.HIGH)
                .startAt(today.withHour(9).withMinute(30))
                .deadlineAt(today.withHour(12).withMinute(0))
                .pomodoroCycles(3)
                .status(Task.TaskStatus.ACTIVE)
                .build());

        taskRepository.save(Task.builder()
                .user(alex)
                .title("B-Tree Balance Property & Red-Black Invariant Proof")
                .pod(math240)
                .priority(Task.Priority.MEDIUM)
                .note("Assignment #6")
                .startAt(today.withHour(13).withMinute(30))
                .deadlineAt(today.withHour(18).withMinute(0))
                .status(Task.TaskStatus.ACTIVE)
                .build());

        taskRepository.save(Task.builder()
                .user(alex)
                .title("Thread Synchronization Mutex Benchmark")
                .pod(cs350)
                .priority(Task.Priority.MEDIUM)
                .note("High contention profiling")
                .startAt(today.withHour(15).withMinute(15))
                .deadlineAt(today.withHour(19).withMinute(30))
                .status(Task.TaskStatus.ACTIVE)
                .build());

        taskRepository.save(Task.builder()
                .user(alex)
                .title("Graph Theory Euler Path Code Verification")
                .pod(math240)
                .priority(Task.Priority.MEDIUM)
                .note("Hierholzer's Algorithm")
                .startAt(today.withHour(17).withMinute(0))
                .deadlineAt(today.plusDays(1).withHour(11).withMinute(59))
                .status(Task.TaskStatus.ACTIVE)
                .build());

        taskRepository.save(Task.builder()
                .user(alex)
                .title("Prepare CS 350 Midterm Cheat Sheet")
                .pod(cs350)
                .priority(Task.Priority.MEDIUM)
                .note("Virtual memory & TLB paging")
                .startAt(today.plusDays(1).withHour(9).withMinute(0))
                .deadlineAt(today.plusDays(3).withHour(23).withMinute(59))
                .status(Task.TaskStatus.ACTIVE)
                .build());

        taskRepository.save(Task.builder()
                .user(alex)
                .title("Solve LeetCode #198 House Robber (Memoization)")
                .pod(cs301)
                .priority(Task.Priority.MEDIUM)
                .startAt(today.withHour(9).withMinute(15))
                .deadlineAt(today.withHour(11).withMinute(0))
                .status(Task.TaskStatus.COMPLETED)
                .completedAt(today.withHour(10).withMinute(45))
                .xpAwarded(25)
                .build());

        taskRepository.save(Task.builder()
                .user(alex)
                .title("Read Chapter 4 Silberschatz Operating Systems")
                .pod(cs350)
                .priority(Task.Priority.MEDIUM)
                .startAt(today.withHour(8).withMinute(30))
                .deadlineAt(today.withHour(9).withMinute(30))
                .status(Task.TaskStatus.COMPLETED)
                .completedAt(today.withHour(9).withMinute(15))
                .xpAwarded(50)
                .build());

        // 7. Active Room (Matching Screenshot 3)
        Room room = roomRepository.save(Room.builder()
                .code("AB12XY")
                .name("CS 301 - Final Exam Sprint 🚀")
                .description("Algorithms & Data Structures • Room Goal: 3 Focus Cycles • Synchronized P2P Mesh")
                .host(alex)
                .pod(cs301)
                .focusMinutes(25)
                .breakMinutes(5)
                .targetCycles(4)
                .currentCycle(2)
                .phase(Room.Phase.FOCUS)
                .phaseStartedAt(today.minusMinutes(11).minusSeconds(11))
                .phaseEndsAt(today.plusMinutes(13).plusSeconds(49)) // 13:49 remaining
                .maxMembers(10)
                .status(Room.Status.ACTIVE)
                .paused(false)
                .build());

        // Members in Room
        roomMemberRepository.save(RoomMember.builder().room(room).user(alex).role(RoomMember.MemberRole.HOST)
                .state(RoomMember.MemberState.FOCUS).currentActivity("Dynamic Programming #72").focusSeconds(48 * 60)
                .karmaPoints(180).joinedAt(today.minusMinutes(50)).lastHeartbeatAt(today).build());

        roomMemberRepository.save(RoomMember.builder().room(room).user(sophia).role(RoomMember.MemberRole.MEMBER)
                .state(RoomMember.MemberState.FOCUS).currentActivity("Graph DFS and Topological Sort").focusSeconds(42 * 60)
                .karmaPoints(160).joinedAt(today.minusMinutes(45)).lastHeartbeatAt(today).build());

        roomMemberRepository.save(RoomMember.builder().room(room).user(marcus).role(RoomMember.MemberRole.MEMBER)
                .state(RoomMember.MemberState.BREAK).currentActivity("☕ Short Coffee Break").focusSeconds(25 * 60)
                .karmaPoints(110).joinedAt(today.minusMinutes(35)).lastHeartbeatAt(today).build());

        roomMemberRepository.save(RoomMember.builder().room(room).user(priya).role(RoomMember.MemberRole.MEMBER)
                .state(RoomMember.MemberState.FOCUS).currentActivity("Dijkstra Shortest Path Matrix").focusSeconds(15 * 60)
                .karmaPoints(80).joinedAt(today.minusMinutes(20)).lastHeartbeatAt(today).build());

        roomMemberRepository.save(RoomMember.builder().room(room).user(rohan).role(RoomMember.MemberRole.MEMBER)
                .state(RoomMember.MemberState.FOCUS).currentActivity("B-Tree Balance Properties").focusSeconds(10 * 60)
                .karmaPoints(50).joinedAt(today.minusMinutes(15)).lastHeartbeatAt(today).build());

        roomMemberRepository.save(RoomMember.builder().room(room).user(elena).role(RoomMember.MemberRole.MEMBER)
                .state(RoomMember.MemberState.FOCUS).currentActivity("Master Theorem Proofs").focusSeconds(8 * 60)
                .karmaPoints(40).joinedAt(today.minusMinutes(10)).lastHeartbeatAt(today).build());

        // Sprint Target
        sprintTargetRepository.save(SprintTarget.builder()
                .room(room)
                .title("Implement Dynamic Programming LeetCode #72")
                .description("Formulate recurrence relation for Edit Distance, build 2D memoization matrix, and analyze space complexity optimization to O(min(m, n)).")
                .stepCurrent(2)
                .stepTotal(3)
                .tag("#Coding")
                .createdBy(alex)
                .createdAt(today.minusMinutes(14))
                .build());

        // Checklist
        checklistItemRepository.save(ChecklistItem.builder().room(room).text("Review Graph Traversal & Dijkstra proofs")
                .tag("#Theory").done(true).createdBy(alex).completedBy(alex).build());
        checklistItemRepository.save(ChecklistItem.builder().room(room).text("Implement Dynamic Programming LeetCode #72")
                .tag("#Coding").done(false).createdBy(alex).build());
        checklistItemRepository.save(ChecklistItem.builder().room(room).text("Write JUnit test cases for WebSocket controller")
                .tag("#Coding").done(false).createdBy(alex).build());

        // Initial chat messages
        chatMessageRepository.save(ChatMessage.builder().room(room).user(alex)
                .content("Hey squad, let's crush Cycle 2. Edit Distance DP recurrence is up on the sprint target!")
                .sentAt(today.minusMinutes(15)).build());
        chatMessageRepository.save(ChatMessage.builder().room(room).user(sophia)
                .content("Topological sort proofs ready too. Locking in now! 🔒")
                .sentAt(today.minusMinutes(12)).build());

        // 8. Historical Focus Sessions & Daily Stats (2 weeks)
        LocalDate curDate = LocalDate.now();
        double[] weeklyHours = { 3.8, 5.4, 4.2, 8.5, 6.0, 5.8, 8.4 };
        for (int i = 0; i < 7; i++) {
            LocalDate statDate = curDate.minusDays(6 - i);
            int focusSecs = (int) (weeklyHours[i] * 3600);
            dailyFocusStatRepository.save(DailyFocusStat.builder()
                    .user(alex)
                    .statDate(statDate)
                    .focusSeconds(focusSecs)
                    .sessionsCompleted((int) Math.round(weeklyHours[i] * 1.5))
                    .build());
        }

        // Recent past sessions
        focusSessionRepository.save(FocusSession.builder()
                .user(alex).room(room).mode(FocusSession.Mode.SQUAD_SPRINT)
                .startedAt(today.minusHours(2).minusMinutes(50)).endedAt(today.minusHours(2))
                .plannedSeconds(50 * 60).actualFocusSeconds(50 * 60).cyclesCompleted(2).completed(true).xpEarned(100)
                .build());

        focusSessionRepository.save(FocusSession.builder()
                .user(alex).room(null).mode(FocusSession.Mode.DEEP_FOCUS)
                .startedAt(today.minusDays(1).withHour(16).withMinute(15)).endedAt(today.minusDays(1).withHour(17).withMinute(30))
                .plannedSeconds(75 * 60).actualFocusSeconds(75 * 60).cyclesCompleted(3).completed(true).xpEarned(150)
                .build());

        focusSessionRepository.save(FocusSession.builder()
                .user(alex).room(room).mode(FocusSession.Mode.SQUAD_SPRINT)
                .startedAt(today.minusDays(2).withHour(14).withMinute(0)).endedAt(today.minusDays(2).withHour(14).withMinute(45))
                .plannedSeconds(45 * 60).actualFocusSeconds(45 * 60).cyclesCompleted(1).completed(true).xpEarned(90)
                .build());

        log.info("FocusForge initial seed data created successfully!");
    }
}
