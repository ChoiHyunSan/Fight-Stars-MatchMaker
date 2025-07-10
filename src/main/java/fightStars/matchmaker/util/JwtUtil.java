package fightStars.matchmaker.util;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;

import fightStars.matchmaker.config.MatchMakerConfig;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

public class JwtUtil {
	private static final Key secretKey = Keys.hmacShaKeyFor(MatchMakerConfig.JWT_SECRET.getBytes(StandardCharsets.UTF_8));
	private static final long expirationMillis = MatchMakerConfig.EXPIRE_MILL;

	/**
	 * 서버 간 통신용 JWT 토큰 생성
	 * @param serverName 서버 이름 (예: "MatchMakerServer")
	 * @return JWT 토큰 문자열
	 */
	public static String generateServerToken() {
		Instant now = Instant.now();

		return Jwts.builder()
			.setSubject(String.valueOf(1000))
			.claim("role", "Server")
			.setIssuedAt(Date.from(now))
			.setExpiration(Date.from(now.plus(expirationMillis, ChronoUnit.MINUTES)))
			.signWith(secretKey, SignatureAlgorithm.HS512)
			.compact();
	}
}
