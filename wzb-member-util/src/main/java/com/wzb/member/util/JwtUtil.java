package com.wzb.member.util;

import io.jsonwebtoken.*;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * @author Zebin Wang
 * @Title:
 * @Package
 * @Description: 生成和验证Jwt的工具类
 */
@Component
@ConfigurationProperties(prefix = "wzb.jwt.config")
public class JwtUtil {
    // 密钥
    private String secretKey;

    //单位秒，默认7天
    private long expires = 60 * 60 * 24 * 7;

    public String getSecretKey() {
        return secretKey;
    }

    public void setSecretKey(String secretKey) {
        this.secretKey = secretKey;
    }

    public long getExpires() {
        return expires;
    }

    public void setExpires(long expires) {
        this.expires = expires;
    }

    /**
     * 生成JWT
     *
     * @param id
     */

    public String createJWT(String id, String subject, Boolean isLogin) {
        long nowMillis = System.currentTimeMillis();
        Date now = new Date(nowMillis);
        JwtBuilder builder = Jwts.builder().setId(id)
                .setSubject(subject)
                .setIssuedAt(now)
                .signWith(SignatureAlgorithm.HS256, secretKey)
                .claim("isLogin", isLogin);
        if (expires > 0) {
            // expires乘以1000是毫秒转秒
            builder.setExpiration(new Date(nowMillis + expires * 1000));
        }
        return builder.compact();
    }

    /**
     * 解析JWT
     *
     * @param jwtToken
     */
    public Claims parseJWT(String jwtToken) {
        return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(jwtToken).getBody();
    }
}
