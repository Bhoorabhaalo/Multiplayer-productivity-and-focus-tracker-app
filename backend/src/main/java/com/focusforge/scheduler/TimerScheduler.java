package com.focusforge.scheduler;

import com.focusforge.service.TimerService;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class TimerScheduler {
    public TimerScheduler(TimerService timerService) {
        this.timerService = timerService;
    }



    private final TimerService timerService;

    @Scheduled(fixedRate = 1000)
    public void runTimerTick() {
        timerService.tickActiveRooms();
    }
}
