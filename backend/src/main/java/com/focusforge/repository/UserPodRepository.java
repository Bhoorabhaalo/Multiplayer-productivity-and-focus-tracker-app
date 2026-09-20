package com.focusforge.repository;

import com.focusforge.entity.UserPod;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserPodRepository extends JpaRepository<UserPod, Long> {
    List<UserPod> findByUserIdOrderBySubscribedAtDesc(Long userId);
    Optional<UserPod> findByUserIdAndPodId(Long userId, Long podId);
    void deleteByUserIdAndPodId(Long userId, Long podId);
}
