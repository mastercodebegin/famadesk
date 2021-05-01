package com.fama.famadesk.components.helper;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.UUID;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.interfaces.JWTVerifier;

public class JwtHelper {
	private static final String secretKey = "famadesk";
	private static final String issuer = "famadesk";
	private static final Algorithm algorithmHS = Algorithm.HMAC256(secretKey);
	private static final long expiredDurationDays = 30;

	private static final String EMAIL = "email";
	private static final String USER_ID = "userId";
	private static final String ROLE = "role";
//	private static final String PASSWORD_UPDATED_DATE = "passwordUpdatedDate";

	private static class LazyHolder {
		static final JwtHelper INSTANCE = new JwtHelper();
	}

	public static JwtHelper getInstance() {
		return LazyHolder.INSTANCE;
	}

	public static String createJwtToken(String email, String userId, String role) {
		UUID uuid = UUID.randomUUID();
		LocalDateTime currentTime = LocalDateTime.now();
		LocalDateTime expiredTime = currentTime.plusDays(expiredDurationDays);
		Date expiredDate = Date.from(expiredTime.atZone(ZoneId.systemDefault()).toInstant());
		Date issuedDate = Date.from(currentTime.atZone(ZoneId.systemDefault()).toInstant());
		String token = JWT.create().withIssuer(issuer).withIssuedAt(issuedDate).withClaim(EMAIL, email)
				.withClaim(USER_ID, userId).withClaim(ROLE, role).withJWTId(uuid.toString()).withExpiresAt(expiredDate)
				.sign(algorithmHS);
		return token;
	}

	public DecodedJWT verifyToken(String token) {
		JWTVerifier verifier = JWT.require(algorithmHS).withIssuer(issuer).build();
		DecodedJWT jwt = verifier.verify(token);
		return jwt;
	}

	public static void verifyRequestedToken(String jwtToken) {
		JwtHelper.getInstance().verifyToken(jwtToken);
	}

	public static String getEmail(String jwtToken) {
		return JwtHelper.getInstance().verifyToken(jwtToken).getClaim(EMAIL).asString();
	}

	public static String getUserId(String jwtToken) {
		return JwtHelper.getInstance().verifyToken(jwtToken).getClaim(USER_ID).asString();
	}

	public static String getUserRole(String jwtToken) {
		return JwtHelper.getInstance().verifyToken(jwtToken).getClaim(ROLE).asString();
	}

//	public static String getPasswordUpdatedDate(String jwtToken) {
//		return JwtHelper.getInstance().verifyToken(jwtToken).getClaim(PASSWORD_UPDATED_DATE).asString();
//	}
}
