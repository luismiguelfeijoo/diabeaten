package com.diabeaten.notificationservice.util;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class JwtUtil {

    @Value("${secret.key}")
    private String SECRET_KEY;

    @Value("${spring.application.name}")
    private String name;

    public String generateToken(String appName) {
        return createToken(appName);
    }

    public String createToken(String subject) {
        return Jwts.builder().setSubject(subject).setIssuedAt(new Date(System.currentTimeMillis())).signWith(SignatureAlgorithm.HS256, SECRET_KEY).compact();
    }

}
