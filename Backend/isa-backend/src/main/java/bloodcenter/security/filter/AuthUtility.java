package bloodcenter.security.filter;

import bloodcenter.person.model.Person;
import bloodcenter.person.model.User;
import bloodcenter.person.service.BCAdminService;
import bloodcenter.person.service.PersonService;
import bloodcenter.person.service.UserService;
import bloodcenter.role.Role;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;

public class AuthUtility {

    private final PersonService personService;

    public AuthUtility(PersonService personService) {
        this.personService = personService;
    }

    public String createJWTFromRequest(HttpServletRequest req) {
        String refreshToken = getRefreshToken(req);
        if (refreshToken == null) return null;
        DecodedJWT decodedJWT = getVerifier().verify(refreshToken);
        String username = decodedJWT.getSubject();
        Person person = personService.getPerson(username);
        return JWT.create()
                .withSubject(person.getEmail())
                .withExpiresAt(new Date(System.currentTimeMillis() + 30 * 1000))    // 30 seconds
                .withIssuer(req.getRequestURL().toString())
                .withClaim("roles", person.getRoles().stream().
                        map(Role::getName).collect(Collectors.toList()))
                .sign(getAlgorithm());
    }

    public String getEmailFromRequest(HttpServletRequest req) {
        String authorizationHeader = req.getHeader(AUTHORIZATION);
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            try {
                String token = authorizationHeader.substring("Bearer ".length());
                DecodedJWT decodedJWT = getVerifier().verify(token);
                return decodedJWT.getSubject();
            } catch (Exception e) {
                return null;
            }
        }
        return null;
    }

    private String getRefreshToken(HttpServletRequest req) {
        try {
            for (Cookie c : req.getCookies()) {
                if (c.getName().equals("refreshToken"))
                    return c.getValue();
            }
        } catch (Exception e) {
            return null;
        }
        return null;
    }

    public static JWTVerifier getVerifier() {
        return JWT.require(getAlgorithm()).build();
    }

    public static Algorithm getAlgorithm() {
        return Algorithm.HMAC512("secret".getBytes());
    }

    public static void setResponseMessage(HttpServletResponse response, String messageName, String messageText)
            throws IOException {
        Map<String, String> responseObject = new HashMap<>();
        responseObject.put(messageName, messageText);
        new ObjectMapper().writeValue(response.getOutputStream(), responseObject);
    }
}
