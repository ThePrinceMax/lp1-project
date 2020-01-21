package org.princelle.lp1project.Security;

public final class SecurityConstants {

	public static final String AUTH_SIGNIN_URL = "/api/signin";
	public static final String AUTH_SIGNUP_URL = "/api/signin";

	// Signing key for HS512 algorithm
	// You can use the page http://www.allkeysgenerator.com/ to generate all kinds of keys
	public static final String JWT_SECRET = System.getenv("JWT_SECRET");

	// JWT token defaults
	public static final String TOKEN_HEADER = "Authorization";
	public static final String TOKEN_PREFIX = "Bearer ";
	public static final String TOKEN_TYPE = "JWT";
	public static final String TOKEN_ISSUER = "secure-api";
	public static final String TOKEN_AUDIENCE = "secure-app";

	private SecurityConstants() {
		throw new IllegalStateException("Cannot create instance of static util class");
	}
}