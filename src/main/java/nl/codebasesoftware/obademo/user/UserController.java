package nl.codebasesoftware.obademo.user;

import com.obaccelerator.sdk.Oba;
import com.obaccelerator.sdk.exception.NotFoundException;
import com.obaccelerator.sdk.user.User;
import nl.codebasesoftware.obademo.ObaDemoProperties;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.util.UUID;


@RestController
public class UserController {

    private final Oba oba;
    private final ObaDemoProperties obaDemoProperties;

    public UserController(Oba oba, ObaDemoProperties obaDemoProperties) {
        this.oba = oba;
        this.obaDemoProperties = obaDemoProperties;
    }

    @PostMapping("/users")
    public ResponseEntity<User> createUser(HttpServletResponse response) {
        User user = oba.createUser();
        setUserCookie(user.getId(), response);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @GetMapping("/users")
    public ResponseEntity<User> findUser(@CookieValue(value = "demo-user", required = false) UUID userId) {
        if (userId == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        User user = null;

        try {
            user = oba.findUser(userId);
        } catch (NotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    private void setUserCookie(UUID userId, HttpServletResponse response) {
        Cookie cookie = new Cookie(obaDemoProperties.getCookieName(), userId.toString());
        cookie.setDomain(obaDemoProperties.getApplicationDomain());
        cookie.setPath(obaDemoProperties.getApplicationContextPath());
        cookie.setHttpOnly(true);
        cookie.setSecure(true);
        response.addCookie(cookie);
    }
}
