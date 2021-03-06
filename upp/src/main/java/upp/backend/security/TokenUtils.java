package upp.backend.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.impl.DefaultClaims;
import upp.backend.dto.UserDTO;
import upp.backend.model.User;
import upp.backend.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

@Component
public class TokenUtils {

    @Value("myXAuthSecret")
    private String secret;

    @Value("18000")
    private Long expiration;
    @Value("Authorization")
    private String AUTH_HEADER;
    @Autowired
    private UserService userService;

    public String getUsernameFromToken(String token) {
        String username;
        System.out.println("getUsernameFromToken");
        try {
            Claims claims = this.getClaimsFromToken(token);
            System.out.println(claims);
            username = claims.getSubject();
        } catch (Exception ex) {
            username = null;
        }
        return username;

    }
    
    public String getAuthHeaderFromHeader(HttpServletRequest request) {
        return request.getHeader(AUTH_HEADER);
    }
    
    public String getProcessIdFromToken(String token) {
		final Claims claims = this.getClaimsFromToken(token);
		return (String) claims.getOrDefault("processId", -1);
	}
    
    public String getToken(HttpServletRequest request) {
        String authHeader = getAuthHeaderFromHeader(request);

        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            return authHeader.substring(7);
        }

        return null;
    }

    private Claims getClaimsFromToken(String token) {
        Claims claims;
        try {
            claims = Jwts.parser().setSigningKey(this.secret).parseClaimsJws(token).getBody();
        } catch (Exception ex) {
            claims = null;
        }
        return claims;
    }

    private Date getExpirationDateFromToken(String token) {
        Date expiration;
        try {
            final Claims claims = this.getClaimsFromToken(token);
            expiration = claims.getExpiration();
        } catch (Exception ex) {
            expiration = null;
        }
        return expiration;
    }

    private boolean isTokenExpired(String token) {
        final Date expiration = this.getExpirationDateFromToken(token);
        return expiration.before(new Date(System.currentTimeMillis()));
    }

    public boolean validateToken(String token, UserDetails userDetails){
        final String username = getUsernameFromToken(token);
        User u = userService.findUserByEmail(userDetails.getUsername());
        return username.equals(u.getUsername()) && !isTokenExpired(token);
    }
    public String generateToken(UserDetails userDetails) {
        Map<String, Object> claims = new HashMap<String, Object>();
        User u = userService.findUserByEmail(userDetails.getUsername());
        UserDTO user = userService.convertToDTO(u);
        claims.put("sub", user.getUsername());
        claims.put("created", new Date(System.currentTimeMillis()));
        claims.put("roles", userDetails.getAuthorities());
        claims.put("user", user);
        return Jwts.builder().setClaims(claims).setExpiration(new Date(System.currentTimeMillis() + expiration * 1000)).signWith(SignatureAlgorithm.HS512, secret).compact();
    }
    
	public String generateTokenRegistration(UserDetails userDetails, String processId) {
		Map<String, Object> claims = new HashMap<String, Object>();
		claims.put("processId", processId);
        User u = userService.findUserByEmail(userDetails.getUsername());
        UserDTO user = userService.convertToDTO(u);
        claims.put("sub", user);
        claims.put("created", new Date(System.currentTimeMillis()));
        
        claims.put("roles", userDetails.getAuthorities());
        return Jwts.builder().setClaims(claims).setExpiration(new Date(System.currentTimeMillis() + expiration * 1000)).signWith(SignatureAlgorithm.HS512, secret).compact();
	}
    
	

}


