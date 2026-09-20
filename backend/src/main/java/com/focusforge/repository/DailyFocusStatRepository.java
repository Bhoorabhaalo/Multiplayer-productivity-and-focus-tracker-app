package com.focusforge.repository;

import com.focusforge.entity.DailyFocusStat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface DailyFocusStatRepository extends JpaRepository<DailyFocusStat, Long> {
    Optional<DailyFocusStat> findByUserIdAndStatDate(Long userId, LocalDate statDate);
    List<DailyFocusStat> findByUserIdAndStatDateBetweenOrderByStatDateAsc(Long userId, LocalDate startDate, LocalDate endDate);
}
