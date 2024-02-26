package com.example.mybookstore_backend.serviceimpl;

import com.example.mybookstore_backend.service.TimerService;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
@Scope("session")
public class TimerServiceImpl implements TimerService {
    private Map<String, LocalDateTime> loginTimes = new ConcurrentHashMap<>();
    @Override
    public void startTimer(String username) {
        loginTimes.put(username, LocalDateTime.now());
    }

    @Override
    public Duration stopTimer(String username) {
        LocalDateTime loginTime = loginTimes.get(username);
        if (loginTime != null) {
            Duration sessionDuration = Duration.between(loginTime, LocalDateTime.now());
            loginTimes.remove(username);
            return sessionDuration;
        }
        return Duration.ZERO;
    }

}
