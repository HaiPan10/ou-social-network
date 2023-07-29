package com.ou.configs;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

@Component
public class JwtTokenFilter extends OncePerRequestFilter{

    @Autowired
    private JwtService jwtService;

    @Autowired
    private UserDetailsService userDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        // if(request.getRequestURI().equals("/api/accounts/register")){
        //     filterChain.doFilter(request, response);
        // }

        System.out.println("[DEBUG] - Start filter Token");
        System.out.println("[DEBUG] - uri=" + request.getRequestURI());

        // DEBUG header
        System.out.println("[DEBUG] - Header Authorization: " + request.getHeader("Authorization"));

        String header = getAuthorization(request);

        if(header == null || !header.startsWith("Bearer")){
            filterChain.doFilter(request, response);
            return;
        }

        System.out.println("[DEBUG] - Has Authorization Bearer");

        String token = getAccessToken(header);

        if(!jwtService.isValidAccessToken(token)){
            filterChain.doFilter(request, response);
            return;
        }

        System.out.println("[DEBUG] - Given token is valid");

        setAuthenticationContext(token, request);
        filterChain.doFilter(request, response);
    }

    private String getAuthorization(HttpServletRequest request){
        String header = request.getHeader("Authorization");
        if(header == null) {
            Cookie[] cookies = request.getCookies();
            if(cookies != null){
                for(Cookie cookie : cookies){
                if(cookie.getName().equals("Authorization")){
                    header = "Bearer " + cookie.getValue();
                    break;
                }
            }
            }
        }
        return header;
    }

    private String getAccessToken(String header){
        String token = header.split(" ")[1].trim();
        return token;
    }

    private void setAuthenticationContext(String token, HttpServletRequest request){
        UserDetails userDetails = getUserDetails(token);
        UsernamePasswordAuthenticationToken authentication = new 
            UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
        authentication.setDetails(
            new WebAuthenticationDetailsSource().buildDetails(request)
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }
    
    //Re-create the user with given token
    private UserDetails getUserDetails(String token){
        String[] claims = jwtService.getSubject(token).split(",");
        // Account account = new Account();
        System.out.println("[User ID] - " + claims[0]);
        System.out.println("[Email] - " + claims[1]);
        System.out.println("[INFO] - Load the user detail");
        // account.setId(Integer.parseInt(claims[0]));
        // account.setEmail(claims[1]);
        return userDetailsService.loadUserByUsername(claims[1]);
    }
}
