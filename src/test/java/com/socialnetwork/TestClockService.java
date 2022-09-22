package com.socialnetwork;

import java.time.LocalDateTime;

public class TestClockService extends ClockService {

    private LocalDateTime currentTestingTime;

    public void setTime(LocalDateTime time) {
        this.currentTestingTime = time;
    }

    @Override
    public LocalDateTime getCurrentTime() {
        return currentTestingTime;
    }
}
