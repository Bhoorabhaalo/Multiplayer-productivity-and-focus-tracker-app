package com.focusforge.repository;

import com.focusforge.entity.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {

    List<Task> findByUserIdOrderByStartAtAsc(Long userId);

    List<Task> findByUserIdAndStatusOrderByStartAtAsc(Long userId, Task.TaskStatus status);

    @Query("SELECT t FROM Task t WHERE t.user.id = :userId AND (:status IS NULL OR t.status = :status) " +
           "AND (:startDate IS NULL OR t.startAt >= :startDate) AND (:endDate IS NULL OR t.startAt < :endDate) " +
           "ORDER BY t.startAt ASC")
    List<Task> findTasksWithFilter(@Param("userId") Long userId,
                                   @Param("status") Task.TaskStatus status,
                                   @Param("startDate") LocalDateTime startDate,
                                   @Param("endDate") LocalDateTime endDate);

    long countByUserIdAndStatus(Long userId, Task.TaskStatus status);

    @Query("SELECT COALESCE(SUM(t.xpAwarded), 0) FROM Task t WHERE t.user.id = :userId " +
           "AND t.status = 'COMPLETED' AND t.completedAt >= :startOfDay AND t.completedAt < :endOfDay")
    int sumXpEarnedToday(@Param("userId") Long userId,
                         @Param("startOfDay") LocalDateTime startOfDay,
                         @Param("endOfDay") LocalDateTime endOfDay);
}
