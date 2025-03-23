package net.AIChatbotBackend.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.io.Decoders;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.security.Key;
import java.util.Date;
import java.util.function.Function;

@Component
public class JwtUtil {

    // Use a secure key (make sure it's at least 256 bits for HMAC)
    private final Key SECRET_KEY = Keys.hmacShaKeyFor(
            Decoders.BASE64.decode("80a6493b801ae1d0bbf8cfff0f41bbb6aa5545e14d47ecdead5b7c11c2176715c6f21a5f349d065537585b4e2a45dec1571b891278f516f9cbc4c6f244a77aef59062b40f0a3bc2572651f57d797b9b94ee8493749353acff466ed8f21802abbe6b29b9e0a261f73e290d936e3fd150255b76d1c20b38e3f8a9d1d760df6c075")
    );

    public String generateToken(String email) {
        return Jwts.builder()
                .subject(email) // ✅ Updated method
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60)) // 1 hour expiry
                .signWith(SECRET_KEY) // ✅ No algorithm needed, inferred from key
                .compact();
    }

    public String extractEmail(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    public Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    private Claims extractAllClaims(String token) {
        return Jwts.parser() // ✅ No more `parserBuilder()`
                .verifyWith((SecretKey) SECRET_KEY) // ✅ Replaces `setSigningKey`
                .build()
                .parseSignedClaims(token) // ✅ Replaces `parseClaimsJws`
                .getPayload(); // ✅ Replaces `getBody()`
    }

    public boolean isTokenValid(String token, UserDetails userDetails) {
        final String email = extractEmail(token);
        return (email.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }

    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }
}
