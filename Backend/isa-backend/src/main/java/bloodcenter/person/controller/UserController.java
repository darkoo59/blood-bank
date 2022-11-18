package bloodcenter.person.controller;

import bloodcenter.person.dto.PersonDTO;
import bloodcenter.person.dto.RegisterDTO;
import bloodcenter.person.model.User;
import bloodcenter.person.service.PersonService;
import bloodcenter.person.service.UserService;
import bloodcenter.security.filter.AuthUtility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.*;

import static bloodcenter.utils.ObjectsMapper.convertUserListToDTO;
import static org.springframework.http.HttpStatus.UNAUTHORIZED;
import static org.springframework.util.MimeTypeUtils.APPLICATION_JSON_VALUE;

@Controller
@RequestMapping("/api/user")
public class UserController {
    private final UserService userService;
    private final AuthUtility authUtility;

    @Autowired
    public UserController(UserService userService, PersonService personService) {
        this.userService = userService;
        this.authUtility = new AuthUtility(personService);
    }

    @GetMapping
    public ResponseEntity<List<PersonDTO>> getAll(){
        return new ResponseEntity<>(convertUserListToDTO(userService.getAll()), HttpStatus.OK);
    }

    @GetMapping("/search")
    @Secured({"ROLE_ADMIN", "ROLE_BCADMIN"})
    public ResponseEntity<List<User>> searchUsers(@RequestParam String searchInput) {
        return new ResponseEntity<>(userService.searchUsers(searchInput), HttpStatus.OK);
    }

    @PostMapping("/register")
    public ResponseEntity<?> Register(@RequestBody RegisterDTO registerDTO) {
        if (userService.registerUser(registerDTO)) {
            return new ResponseEntity<>(HttpStatus.OK);
        } else return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @PostMapping("/token/refresh")
    public void refreshToken(HttpServletRequest request, HttpServletResponse response) throws IOException {
        try {
            String accessToken = authUtility.createJWTFromRequest(request);
            if (accessToken != null) {
                response.setContentType(APPLICATION_JSON_VALUE);
                AuthUtility.setResponseMessage(response, "accessToken", accessToken);
            }
            else {
                response.setStatus(UNAUTHORIZED.value());
                AuthUtility.setResponseMessage(response, "errorMessage", "Refresh token is missing");
            }
        } catch (Exception e) {
            response.setStatus(UNAUTHORIZED.value());
            AuthUtility.setResponseMessage(response, "errorMessage", e.getMessage());
        }
    }

    @PostMapping("/logout")
    public void logout(HttpServletResponse response) throws IOException {
        Cookie jwtCookie = new Cookie("refreshToken", "");
        jwtCookie.setMaxAge(0);
        jwtCookie.setHttpOnly(true);
        jwtCookie.setDomain("localhost");
        jwtCookie.setPath("/");
        response.addCookie(jwtCookie);
        AuthUtility.setResponseMessage(response, "Success", "Cookie removed");
    }
}
