package frame.base.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
@Slf4j
public class JwtTokenUtil {
	private static final String CLAIM_KEY_USERNAME = "sub";
	private static final String CLAIM_KEY_CREATED = "created";
	@Value("${jwt.secret}")
	private String secret;
	@Value("${jwt.expiration}")
	private Long expiration;

	/**
	 * ���ݸ��ݵ�¼�û���Ϣ��������JWT��token
	 */
	private String generateToken(Map<String, Object> claims) {
		return Jwts.builder().setClaims(claims).setExpiration(generateExpirationDate())
				.signWith(SignatureAlgorithm.HS512, secret).compact();
	}

	/**
	 * ��token�л�ȡJWT�еĸ���
	 */
	private Claims getClaimsFromToken(String token) {
		Claims claims = null;
		try {
			claims = Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
		} catch (Exception e) {
			log.info("JWT��ʽ��֤ʧ��:{}", token);
		}
		return claims;
	}

	/**
	 * ����token�Ĺ���ʱ��
	 */
	private Date generateExpirationDate() {
		return new Date(System.currentTimeMillis() + expiration * 1000);
	}

	/**
	 * ��token�л�ȡ��¼�û���
	 */
	public String getUserNameFromToken(String token) {
		String username;
		try {
			Claims claims = getClaimsFromToken(token);
			username = claims.getSubject();
		} catch (Exception e) {
			username = null;
		}
		return username;
	}

	/**
	 * ��֤token�Ƿ���Ч
	 *
	 * @param token �ͻ��˴����token
	 * @param userDetails �����ݿ��в�ѯ�������û���Ϣ
	 */
	public boolean validateToken(String token, UserDetails userDetails) {
		String username = getUserNameFromToken(token);
		return username.equals(userDetails.getUsername()) && !isTokenExpired(token);
	}

	/**
	 * �ж�token�Ƿ��Ѿ�ʧЧ
	 */
	private boolean isTokenExpired(String token) {
		Date expiredDate = getExpiredDateFromToken(token);
		return expiredDate.before(new Date());
	}

	/**
	 * ��token�л�ȡ����ʱ��
	 */
	private Date getExpiredDateFromToken(String token) {
		Claims claims = getClaimsFromToken(token);
		return claims.getExpiration();
	}

	/**
	 * �����û���Ϣ����token
	 */
	public String generateToken(UserDetails userDetails) {
		Map<String, Object> claims = new HashMap<>();
		claims.put(CLAIM_KEY_USERNAME, userDetails.getUsername());
		claims.put(CLAIM_KEY_CREATED, new Date());
		return generateToken(claims);
	}

	/**
	 * �ж�token�Ƿ���Ա�ˢ��
	 */
	public boolean canRefresh(String token) {
		return !isTokenExpired(token);
	}

	/**
	 * ˢ��token
	 */
	public String refreshToken(String token) {
		Claims claims = getClaimsFromToken(token);
		claims.put(CLAIM_KEY_CREATED, new Date());
		return generateToken(claims);
	}
}
