package com.cj.security.auth.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @Author: CJ
 * @Data: 2020/6/11 9:17
 */
@Data
@ConfigurationProperties(prefix = "jwt")
@Component
public class JwtTokenUtil {

    private String secret;
    private Long expiration;
    private String header;

    /**
     * 生成jwt令牌
     * @param userDetails
     * @return
     */
    public String generateToken(UserDetails userDetails) {
        HashMap<String, Object> claims = new HashMap<>(2);
        claims.put("sub", userDetails.getUsername());
        claims.put("created", new Date());
        return generateToken(claims);
    }

    /**
     * 令牌的过期时间，加密算法和秘钥
     * @param claims
     * @return
     */
    private String generateToken(Map<String, Object> claims) {
        Date date = new Date(System.currentTimeMillis() + expiration);
        return Jwts.builder().setClaims(claims)
                .setExpiration(date)
                .signWith(SignatureAlgorithm.ES512,secret)
                .compact();
    }

    /**
     * 获取token中的用户名
     * @param token
     * @return
     */
    public String getUsernameFromToken(String token) {
        String username = null;
        try {
            Claims claims = getClaimsFromToken(token);
            username = claims.getSubject();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return username;
    }

    /**
     * 获取token中的claims
     * @param token
     * @return
     */
    private Claims getClaimsFromToken(String token) {
        Claims claims = null;
        try {
            claims = Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return claims;
    }

    /**
     * 判断token是否过期
     * @param token
     * @return
     */
    public Boolean isTokenExpired(String token) {
        Claims claims = getClaimsFromToken(token);
        Date expiration = claims.getExpiration();
        return expiration.before(new Date());
    }

    /**
     * 刷新token令牌，将新的生成时间放入claims覆盖原时间并和从新生成token
     * @param token
     * @return
     */
    public String refreshToken(String token) {
        String refreshedToken = null;
        try {
            Claims claims = getClaimsFromToken(token);
            claims.put("created", new Date());
            refreshedToken = generateToken(claims);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return refreshedToken;
    }

    /**
     * 校验token是否合法和过期
     * @param token
     * @param userDetails
     * @return
     */
    public Boolean validateToken(String token, UserDetails userDetails) {
        String username = getUsernameFromToken(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }

}
