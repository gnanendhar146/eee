package com.chp.BookStore.utils;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

@Component
public class JWTUtils {
	

	public String generateToken (UserDetails userDetails) {

		return generateToken(new HashMap<>(), userDetails);

		}

		private String generateToken(Map<String, Object> extraclaims, UserDetails userDetails) {

			return Jwts.builder().setClaims(extraclaims)
					.setSubject(userDetails.getUsername()).setIssuedAt(new Date(System.currentTimeMillis())) 
					.setExpiration(new Date(System.currentTimeMillis()+ 1000 * 60 * 24)) 
					.signWith(getSigningKey(), SignatureAlgorithm.HS256).compact();

		}

		private Key getSigningKey() {
			byte[] keyBytes = Decoders. BASE64.decode("413F4428472848625065368566D5970337336767924422645294840406351");
			return Keys.hmacShaKeyFor(keyBytes);
		}
		
		public String extractUserName(String token) {
			return extractClaim (token, Claims::getSubject);
		}
	
		
		public boolean isTokenValid(String token, UserDetails userDetails) {
				final String userName = extractUserName(token); 
				return (userName.equals(userDetails.getUsername())) && !isTokenExpired(token);
		}
		
		private boolean isTokenExpired(String token) {
			return extractExpiration(token).before(new Date());
		}
		
		private <T> T extractClaim(String token, Function <Claims, T> claimsResolvers) {
			final Claims claims = extractAllClaims(token);
				return claimsResolvers.apply(claims);
		}
		private Date extractExpiration(String token) {
			return extractClaim (token, Claims::getExpiration);
		}

		private Claims extractAllClaims(String token) {
				return Jwts.parserBuilder().setSigningKey(getSigningKey()).build().parseClaimsJws(token)
						.getBody();
		}
		


}
