package com.rafu.sistrab.services;

import com.rafu.sistrab.rest.dto.TokenEnum;
import com.rafu.sistrab.rest.dto.TokenResponse;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.Objects;

@Component
public class JWTUtil {
  @Value("${jwt.secret}")
  private String secret;

  @Value("${jwt.expiration}")
  private Long expiration;

  public TokenResponse getToken(final String email) {
    return TokenResponse.builder()
        .type(TokenEnum.BEARER.getValue())
        .token(generateToken(email))
        .duration(expiration.toString())
        .build();
  }

  private String generateToken(final String email) {
    return Jwts.builder()
        .setSubject(email)
        .setExpiration(new Date(System.currentTimeMillis() + expiration))
        .signWith(SignatureAlgorithm.HS512, secret.getBytes())
        .compact();
  }

  public boolean isValid(final String token) {
    final var claims = getClaims(token);
    if (claims == null) {
      return Boolean.FALSE;
    }
    return Objects.equals(claims.getSubject(), getUsername(token));
  }

  private Claims getClaims(final String token) {
    try {
      return Jwts.parser().setSigningKey(secret.getBytes()).parseClaimsJws(token).getBody();
    } catch (Exception ex) {
      return null;
    }
  }

  public String getUsername(final String token) {
    Claims claims = getClaims(token);
    if (claims != null) {
      return claims.getSubject();
    }
    return null;
  }
}
