package service.util;

import java.util.Base64;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;
import service.exception.JwtTokenMalformedException;
import service.exception.JwtTokenMissingException;

@Component
public class JwtUtil {

	@Value("${jwt.secret}")
	private String jwtSecret ;

	public Claims getClaims(final String token) {
		try {
			Claims body = Jwts.parser().setSigningKey(Base64.getEncoder().encodeToString(jwtSecret.getBytes())).parseClaimsJws(BearerCheck(token)).getBody();
			return body;
		} catch (Exception e) {
			System.out.println(e.getMessage() + " => " + e);
		}
		return null;
	}

	public void validateToken(final String headertoken) throws JwtTokenMalformedException, JwtTokenMissingException {
		
		
		try {
			
			Jwts.parser().setSigningKey(new String(Base64.getEncoder().encodeToString(jwtSecret.getBytes()))).parseClaimsJws(BearerCheck(headertoken));
		} catch (SignatureException ex) {
			throw new JwtTokenMalformedException("Invalid JWT signature");
			
		} catch (MalformedJwtException ex) {
			throw new JwtTokenMalformedException("Invalid JWT token");
		} catch (ExpiredJwtException ex) {
			throw new JwtTokenMalformedException("Expired JWT token");
		} catch (UnsupportedJwtException ex) {
			throw new JwtTokenMalformedException("Unsupported JWT token");
		} catch (IllegalArgumentException ex) {
			throw new JwtTokenMissingException("JWT claims string is empty.");
		}
	}
	
	private String BearerCheck(String token) {
		if (token != null && token.startsWith("Bearer ")) {
	        return token.substring(7);
	  }
	  return token ;
	}

}