package com.project.bro_grammers.service;

import java.util.Date;

public interface TokenBlackListService {
     void blacklistToken(String token, Date expiration);

     boolean isTokenBlacklisted(String token);

     void cleanupExpiredTokens();
}
