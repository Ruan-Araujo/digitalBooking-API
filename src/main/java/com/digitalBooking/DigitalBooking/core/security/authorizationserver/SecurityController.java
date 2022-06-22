package com.digitalBooking.DigitalBooking.core.security.authorizationserver;

import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Controller
public class SecurityController {

    @GetMapping("/login")
    public String login() {
        return "pages/login";
    }

    @GetMapping("/oauth/confirm_access")
    public String confirmAcess() {
        return "pages/aprovar";
    }
}
