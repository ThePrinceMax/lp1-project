package org.princelle.lp1project.Security;

public class SecurityConstants {
	public static final String SECRET = System.getenv("JWT_SECRET");
	public static final long EXPIRATION_TIME = 864_000_000; // 10 days
	public static final String TOKEN_PREFIX = "Bearer ";
	public static final String HEADER_STRING = "Authorization";
	public static final String SIGN_UP_URL = "/api/signup";
}