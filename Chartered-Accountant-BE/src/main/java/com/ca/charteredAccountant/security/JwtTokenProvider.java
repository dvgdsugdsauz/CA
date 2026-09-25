package com.ca.charteredAccountant.security;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;

import javax.crypto.SecretKey;

import org.springframework.stereotype.Component;

import com.ca.charteredAccountant.common.Constants;
import com.ca.charteredAccountant.common.enums.AdminRole;
import com.ca.charteredAccountant.config.AppProperties;
import com.ca.charteredAccountant.dao.model.AdminUserEntity;
import com.ca.charteredAccountant.dao.model.LoggedInUserDetails;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class JwtTokenProvider {

	private final SecretKey key;
	private final long expirationMinutes;

	public JwtTokenProvider(AppProperties appProperties) {
		this.key = Keys.hmacShaKeyFor(Decoders.BASE64.decode(appProperties.getJwt().getSecret()));
		this.expirationMinutes = appProperties.getJwt().getExpirationMinutes();
	}

	public String generateToken(AdminUserEntity user) {
		Instant now = Instant.now();
		return Jwts.builder()
				.subject(user.getUsername())
				.claim(Constants.CLAIM_USER_ID, user.getId())
				.claim(Constants.CLAIM_ROLE, user.getRole().name())
				.claim(Constants.CLAIM_FULL_NAME, user.getFullName())
				.issuedAt(Date.from(now))
				.expiration(Date.from(now.plus(expirationMinutes, ChronoUnit.MINUTES)))
				.signWith(key)
				.compact();
	}

	public long getExpirationSeconds() {
		return expirationMinutes * 60;
	}

	/** Returns the user in the token, or null when the token is invalid or expired. */
	public LoggedInUserDetails parseToken(String token) {
		try {
			Claims claims = Jwts.parser().verifyWith(key).build().parseSignedClaims(token).getPayload();
			return LoggedInUserDetails.builder()
					.userId(claims.get(Constants.CLAIM_USER_ID, Long.class))
					.username(claims.getSubject())
					.fullName(claims.get(Constants.CLAIM_FULL_NAME, String.class))
					.role(AdminRole.valueOf(claims.get(Constants.CLAIM_ROLE, String.class)))
					.build();
		} catch (JwtException | IllegalArgumentException ex) {
			log.debug("Rejected JWT: {}", ex.getMessage());
			return null;
		}
	}

}
