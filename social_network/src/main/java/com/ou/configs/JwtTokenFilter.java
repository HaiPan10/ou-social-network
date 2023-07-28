package com.ou.configs;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;
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
        System.out.println("[DEBUG] - " + request.getRequestURI());
        
        if(!hasAuthorizationBearer(request)){
            filterChain.doFilter(request, response);
            return;
        }

        System.out.println("Has Authorization Bearer");

        String token = getAccessToken(request);

        if(!jwtService.isValidAccessToken(token)){
            filterChain.doFilter(request, response);
            return;
        }

        setAuthenticationContext(token, request);
        filterChain.doFilter(request, response);
    }

    private boolean hasAuthorizationBearer(HttpServletRequest request){
        String header = request.getHeader("Authorization");
        if(ObjectUtils.isEmpty(header) || !header.startsWith("Bearer")){
            return false;
        }

        return true;
    }

    private String getAccessToken(HttpServletRequest request){
        String header = request.getHeader("Authorization");
        String token = header.split(" ")[1].trim();
        return token;
    }

    private void setAuthenticationContext(String token, HttpServletRequest request){
        UserDetails userDetails = getUserDetails(token);
        UsernamePasswordAuthenticationToken authentication = new 
            UsernamePasswordAuthenticationToken(userDetails, null, null);
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
        // account.setId(Integer.parseInt(claims[0]));
        // account.setEmail(claims[1]);
        return userDetailsService.loadUserByUsername(claims[1]);
    }
}
