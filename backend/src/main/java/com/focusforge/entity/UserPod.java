package com.focusforge.entity;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "user_pods", uniqueConstraints = {
    @UniqueConstraint(name = "uk_user_pod", columnNames = {"user_id", "pod_id"})
})
public class UserPod {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "pod_id", nullable = false)
    private Pod pod;

    @CreationTimestamp
    @Column(name = "subscribed_at", nullable = false, updatable = false)
    private LocalDateTime subscribedAt;

    public UserPod() {}

    public UserPod(Long id, User user, Pod pod, LocalDateTime subscribedAt) {
        this.id = id;
        this.user = user;
        this.pod = pod;
        this.subscribedAt = subscribedAt;
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getUser() {
        return this.user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Pod getPod() {
        return this.pod;
    }

    public void setPod(Pod pod) {
        this.pod = pod;
    }

    public LocalDateTime getSubscribedAt() {
        return this.subscribedAt;
    }

    public void setSubscribedAt(LocalDateTime subscribedAt) {
        this.subscribedAt = subscribedAt;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private Long id;
        private User user;
        private Pod pod;
        private LocalDateTime subscribedAt;

        public Builder id(Long id) {
            this.id = id;
            return this;
        }

        public Builder user(User user) {
            this.user = user;
            return this;
        }

        public Builder pod(Pod pod) {
            this.pod = pod;
            return this;
        }

        public Builder subscribedAt(LocalDateTime subscribedAt) {
            this.subscribedAt = subscribedAt;
            return this;
        }

        public UserPod build() {
            UserPod instance = new UserPod();
            instance.id = this.id;
            instance.user = this.user;
            instance.pod = this.pod;
            instance.subscribedAt = this.subscribedAt;
            return instance;
        }
    }
}
