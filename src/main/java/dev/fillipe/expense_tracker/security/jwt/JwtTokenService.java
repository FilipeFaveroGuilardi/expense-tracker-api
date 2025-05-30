package dev.fillipe.expense_tracker.security.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import dev.fillipe.expense_tracker.security.userdetails.UserDetailsImpl;
import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;

@Service
public class JwtTokenService {
    @Autowired
    private Dotenv dotenv;

    public String generateToken(UserDetailsImpl userDetails) {
        String issuer = dotenv.get("JWT_ISSUER");
        String secret = dotenv.get("JWT_SECRET_KEY");

        try {
            Algorithm algorithm = Algorithm.HMAC256(secret);

            return JWT.create()
                    .withIssuer(issuer)
                    .withIssuedAt(getCreationTime())
                    .withExpiresAt(getExpiringTime())
                    .withSubject(userDetails.getUsername())
                    .sign(algorithm);
        } catch (JWTCreationException e) {
            throw new JWTCreationException("Error generating token", e);
        }
    }

    public String retrieveSubject(String token) {
        String secret = dotenv.get("JWT_SECRET_KEY");
        String issuer = dotenv.get("JWT_ISSUER");

        try {
            Algorithm algorithm = Algorithm.HMAC256(secret);

            return JWT.require(algorithm)
                    .withIssuer(issuer)
                    .build()
                    .verify(token)
                    .getSubject();
        } catch (JWTVerificationException e) {
            throw new JWTVerificationException("Without permission", e);
        }
    }

    private Instant getCreationTime() {
        return ZonedDateTime.now(ZoneId.of("America/Sao_Paulo")).toInstant();
    }

    private Instant getExpiringTime() {
        return ZonedDateTime.now(ZoneId.of("America/Sao_Paulo")).plusHours(4).toInstant();
    }
}
