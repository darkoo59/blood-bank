package bloodcenter.person.controller;

import bloodcenter.role.Role;
import bloodcenter.person.dto.RegisterDTO;
import bloodcenter.person.model.User;
import bloodcenter.person.service.UserService;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.http.HttpStatus.FORBIDDEN;
import static org.springframework.util.MimeTypeUtils.APPLICATION_JSON_VALUE;

@Controller
@RequestMapping("/api/user")
public class UserController {
    private final UserService userService;

    public UserController(@Autowired UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public ResponseEntity<List<User>> getAll(){
        return new ResponseEntity<>(userService.getAll(), HttpStatus.OK);
    }

    @PostMapping("/register")
    public ResponseEntity<?> Register(@RequestBody RegisterDTO registerDTO) {
        if (userService.registerUser(registerDTO)) {
            return new ResponseEntity<>(HttpStatus.OK);
        } else return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @PostMapping("/token/refresh")
    public void refreshToken(HttpServletRequest request, HttpServletResponse response,
                             @CookieValue(name = "refreshToken") String refreshToken) throws IOException {
        try {
            Algorithm algorithm = Algorithm.HMAC512("secret".getBytes());
            JWTVerifier verifier = JWT.require(algorithm).build();
            DecodedJWT decodedJWT = verifier.verify(refreshToken);
            String username = decodedJWT.getSubject();
            User user = userService.getUser(username);
            String accessToken = JWT.create()
                    .withSubject(user.getEmail())
                    .withExpiresAt(new Date(System.currentTimeMillis() + 30 * 1000))    // 30 seconds
                    .withIssuer(request.getRequestURL().toString())
                    .withClaim("roles", user.getRoles().stream().
                            map(Role::getName).collect(Collectors.toList()))
                    .sign(algorithm);
            Map<String, String> tokens = new HashMap<>();
            tokens.put("accessToken", accessToken);
            Cookie jwtCookie = new Cookie("refreshToken", refreshToken);
            jwtCookie.setMaxAge(14 * 24 * 60 * 60); // 14 days
//              jwtCookie.setSecure(true);     not using https
            jwtCookie.setHttpOnly(true);
            jwtCookie.setDomain("localhost");
            jwtCookie.setPath("/");
            response.addCookie(jwtCookie);
            response.setContentType(APPLICATION_JSON_VALUE);
            new ObjectMapper().writeValue(response.getOutputStream(), tokens);
        } catch (Exception e) {
            response.setStatus(FORBIDDEN.value());
            Map<String, String> error = new HashMap<>();
            error.put("errorMessage", e.getMessage());
            response.setContentType(APPLICATION_JSON_VALUE);
            new ObjectMapper().writeValue(response.getOutputStream(), error);
        }
    }

//    SOOOOOON
//    @GetMapping("/current")
//    public ResponseEntity<?> getCurrentUser(HttpServletRequest request, HttpServletResponse response) {
//        String authorizationHeader = request.getHeader(AUTHORIZATION);
//        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
//            try {
//                String token = authorizationHeader.substring("Bearer ".length());
//                Algorithm algorithm = Algorithm.HMAC512("secret".getBytes());
//                JWTVerifier verifier = JWT.require(algorithm).build();
//                DecodedJWT decodedJWT = verifier.verify(token);
//                String username = decodedJWT.getSubject();
//            } catch (Exception e) {
//                return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
//            }
//        }
//        return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
//    }
}
