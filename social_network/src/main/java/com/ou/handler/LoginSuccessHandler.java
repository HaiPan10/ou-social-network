package com.ou.handler;

import java.io.IOException;
import java.util.Collection;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

@Component
public class LoginSuccessHandler implements AuthenticationSuccessHandler{

    @Override
    public void onAuthenticationSuccess(HttpServletRequest hsr, HttpServletResponse response, Authentication auth)
            throws IOException, ServletException {
        // Collection<? extends GrantedAuthority> authorities = auth.getAuthorities();
        // boolean isAdmin = authorities.stream().anyMatch(a -> a.getAuthority().equals("ADMIN"));
        // if(isAdmin){
        //     System.out.println("[AUTHORITY] - ADMIN");
        //     response.sendRedirect("/social_network/admin/dashboard");
        // } else {
        //     // response.setStatus(HttpServletResponse.SC_FORBIDDEN);
        // }
        response.sendRedirect("/social_network/admin/dashboard");
    }
    
}
