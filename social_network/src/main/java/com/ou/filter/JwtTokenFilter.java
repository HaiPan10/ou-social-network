package com.ou.filter;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;

import com.ou.configs.JwtService;
import com.ou.pojo.Account;
import com.ou.service.interfaces.AccountService;

public class JwtTokenFilter extends UsernamePasswordAuthenticationFilter {

    @Autowired
    private JwtService jwtService;

    @Autowired
    private AccountService accountService;

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
            throws ServletException, IOException {
        // if(request.getRequestURI().equals("/api/accounts/register")){
        // filterChain.doFilter(request, response);
        // }
        HttpServletRequest request = (HttpServletRequest) servletRequest;

        System.out.println("[DEBUG] - Start filter Token");
        System.out.println("[DEBUG] - uri=" + request.getRequestURI());

        // DEBUG header
        System.out.println("[DEBUG] - Header Authorization: " + request.getHeader("Authorization"));

        String header = jwtService.getAuthorization(request);
        request.setCharacterEncoding("UTF-8");

        if (header == null || !header.startsWith("Bearer")) {
            filterChain.doFilter(servletRequest, servletResponse);
            return;
        }

        System.out.println("[DEBUG] - Has Authorization Bearer");

        String token = jwtService.getAccessToken(header);

        if (!jwtService.isValidAccessToken(token)) {
            filterChain.doFilter(servletRequest, servletResponse);
            return;
        }

        System.out.println("[DEBUG] - Given token is valid");

        setAuthenticationContext(token, request);
        filterChain.doFilter(servletRequest, servletResponse);
    }

    // private String getAuthorization(HttpServletRequest request) {
    //     String header = request.getHeader("Authorization");
    //     if (header == null) {
    //         Cookie[] cookies = request.getCookies();
    //         if (cookies != null) {
    //             for (Cookie cookie : cookies) {
    //                 if (cookie.getName().equals("Authorization")) {
    //                     header = "Bearer " + cookie.getValue();
    //                     break;
    //                 }
    //             }
    //         }
    //     }
    //     return header;
    // }

    // private String getAccessToken(String header) {
    //     String token = header.split(" ")[1].trim();
    //     return token;
    // }

    private void setAuthenticationContext(String token, HttpServletRequest request) {
        Account account = getAccount(token);
        if (account != null) {
            boolean enabled = true;
            boolean accountNonExpired = true;
            boolean credentialsNonExpired = true;
            boolean accountNonLocked = true;

            Set<GrantedAuthority> authorities = new HashSet<>();
            authorities.add(new SimpleGrantedAuthority(account.getRoleId().getName()));
            UserDetails userDetail = new org.springframework.security.core.userdetails.User(account.getEmail(),
                    account.getPassword(), enabled, accountNonExpired,
                    credentialsNonExpired, accountNonLocked, authorities);
            UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetail,
                    null, authorities);
            authentication.setDetails(
                    new WebAuthenticationDetailsSource().buildDetails(request));
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }

    }

    // Re-create the user with given token
    private Account getAccount(String token) {
        String[] claims = jwtService.getSubject(token).split(",");
        // Account account = new Account();
        System.out.println("[User ID] - " + claims[0]);
        System.out.println("[Email] - " + claims[1]);
        System.out.println("[INFO] - Load the user detail");
        // account.setId(Integer.parseInt(claims[0]));
        // account.setEmail(claims[1]);
        return accountService.loadAccountByEmail(claims[1]);
    }
}
