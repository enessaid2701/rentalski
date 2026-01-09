package com.library.service;

import org.apache.catalina.User;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationService
{
    public User getUser()
    {
        return (User)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }

    public String getCurrentRole()
    {
        SimpleGrantedAuthority grant = (SimpleGrantedAuthority)SecurityContextHolder.getContext().getAuthentication().getAuthorities().iterator().next();
        return grant.getAuthority();
    }

    public boolean isAdmin()
    {
        SimpleGrantedAuthority grant = (SimpleGrantedAuthority)SecurityContextHolder.getContext().getAuthentication().getAuthorities().iterator().next();
        return grant.getAuthority().equals("ADMIN");
    }

    public boolean isOperator()
    {
        SimpleGrantedAuthority grant = (SimpleGrantedAuthority)SecurityContextHolder.getContext().getAuthentication().getAuthorities().iterator().next();
        return grant.getAuthority().equals("OPERATOR");
    }
}
