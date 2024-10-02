package ecproducts.com.products.services;

import java.sql.Date;
import org.springframework.stereotype.Service;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import ecproducts.com.products.entities.User;

@Service
public class TokenService {
	private final String secret = "sua_chave_secreta";

	public String generateToken(User user) {
		Date expirationDate = new Date(System.currentTimeMillis() + 900000);

		return JWT.create().withIssuer("http://localhost:8081/api").withSubject(user.getEmail())
				.withClaim("id", user.getId()).withExpiresAt(expirationDate).sign(Algorithm.HMAC256(secret));
	}

	public String getSubject(String token) {
		try {
			return JWT.require(Algorithm.HMAC256(secret)).withIssuer("http://localhost:8081/api").build().verify(token)
					.getSubject();
		} catch (Exception e) {
			return null;
		}
	}
}