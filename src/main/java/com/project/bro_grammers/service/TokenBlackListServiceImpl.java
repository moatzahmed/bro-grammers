package com.project.bro_grammers.service;

import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
@Service
public class TokenBlackListServiceImpl implements TokenBlackListService {
    private final Map<String, Date> blacklist = new ConcurrentHashMap<>();

    public void blacklistToken(String token, Date expiration) {
        blacklist.put(token, expiration);
    }

    public boolean isTokenBlacklisted(String token) {
        Date expiration = blacklist.get(token);
        return expiration != null && expiration.after(new Date());
    }

    public void cleanupExpiredTokens() {
        Date now = new Date();
        blacklist.entrySet().removeIf(entry -> entry.getValue().before(now));
    }
}
