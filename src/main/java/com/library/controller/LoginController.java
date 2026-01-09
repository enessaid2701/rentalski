package com.library.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping({ "/login"})
public class LoginController
{
    @Autowired
    private AuthenticationManager authenticationManager;

    @GetMapping
    public String login()
    {
        return "login";
    }

    @PostMapping
    public String doLogin(HttpServletRequest request) {
        try {
            authenticate(request.getParameter("username"), request.getParameter("password"));
        } catch (Exception e) {
            return "redirect:/login";
        }
        return "create-order";
    }

    void authenticate(String username, String password) throws Exception {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        } catch (DisabledException e) {
            throw new Exception("USER_DISABLED", e);
        } catch (BadCredentialsException e) {
            throw new Exception("INVALID_CREDENTIALS", e);
        }
    }
}
