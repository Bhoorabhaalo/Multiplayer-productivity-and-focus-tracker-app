package com.focusforge.repository;

import com.focusforge.entity.Pod;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PodRepository extends JpaRepository<Pod, Long> {
    Optional<Pod> findByCode(String code);
    List<Pod> findAllByOrderByCodeAsc();
}
