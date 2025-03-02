package org.authservice.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import java.security.Key;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;
import org.authservice.entity.Employee;
import org.authservice.entity.EmployeeRole;

public class JwtUtil {
    private static final Key SECRET_KEY = Keys.secretKeyFor(SignatureAlgorithm.HS256);
    private static final long expirationTimeInMills = 1000 * 60 * 60; // 1hr

    public static String createUserToken(Employee employee){

        Date now = new Date();
        Date expireAt = new Date(now.getTime() + expirationTimeInMills);

        Map<String, Object> claims = new HashMap<>();
        claims.put("nickname", employee.getKakaoNickName());

        if(employee.getEmployeeRoles() != null && !employee.getEmployeeRoles().isEmpty()){
            claims.put("roles", employee.getEmployeeRoles().stream().map(EmployeeRole::getName)
                .collect(Collectors.toSet()));
        }else{
            claims.put("roles", Collections.emptySet());
        }

        return Jwts.builder()
            .setSubject(String.valueOf(employee.getId()))
            .claims(claims)
            .setIssuedAt(now)
            .setExpiration(expireAt)
            .signWith(SECRET_KEY)
            .compact();

    }

    public static Claims parseToken(String token){
        return Jwts.parser()
            .setSigningKey(SECRET_KEY)
            .build()
            .parseClaimsJws(token)
            .getBody();
    }


}