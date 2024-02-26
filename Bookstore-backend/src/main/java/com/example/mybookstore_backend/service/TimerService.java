package com.example.mybookstore_backend.service;

import java.time.Duration;

public interface TimerService {
    void startTimer(String username);
    Duration stopTimer(String username);
}
