package com.ou.configs;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.ou.handler.LoginSuccessHandler;

import net.bytebuddy.utility.nullability.AlwaysNull;

@Configuration
@EnableWebSecurity
@EnableTransactionManagement
@ComponentScan(basePackages = {
        
})
public class SpringSecurityConfigs extends WebSecurityConfigurerAdapter {
    @Autowired
    private UserDetailsService accountService;

//     @Autowired
//     private LoginSuccessHandler loginSuccessHandler;

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /*
    @Order(1)
    @Configuration
    public static class AdminSecurityConfig extends WebSecurityConfigurerAdapter
    {
    @Autowired
    private PasswordEncoder adminPasswordEncoder;

    @Autowired
    private UserDetailsService accountService;

    @Override
    protected void configure(AuthenticationManagerBuilder auth)
    throws Exception {
    auth.userDetailsService(accountService)
    .passwordEncoder(adminPasswordEncoder);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
    http.formLogin(login -> login.loginPage("/login")
    .usernameParameter("gmail")
    .passwordParameter("password"));
    http.formLogin(login -> login.defaultSuccessUrl("/admin")
    .failureUrl("/login?error"));
    http.exceptionHandling(handling ->
    handling.accessDeniedPage("/login?access_denied"));
    http.authorizeRequests(requests -> requests.antMatchers("/").permitAll()
    .antMatchers("/admin").access("hasRole('ADMIN')"));
    http.csrf().disable();
    }
    }

    @Order(2)
    @Configuration
    public static class UserSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private PasswordEncoder userPasswordEncoder;

    @Autowired
    private UserDetailsService accountService;

    @Override
    protected void configure(AuthenticationManagerBuilder auth)
    throws Exception {
    auth.userDetailsService(accountService)
    .passwordEncoder(userPasswordEncoder);
    }

    @Override
    protected void configure(HttpSecurity http){

    }
    }
    */
    
    @Override
    protected void configure(AuthenticationManagerBuilder auth)
            throws Exception {
        auth.userDetailsService(accountService)
                .passwordEncoder(passwordEncoder());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.formLogin(login ->
                login.loginPage("/login")
                .usernameParameter("email")
                .passwordParameter("password")
                .defaultSuccessUrl("/")
                .failureUrl("/login?error"));
        http.exceptionHandling(handling -> handling.accessDeniedPage("/login?access_denied"));
        http.authorizeRequests(requests ->
                requests.antMatchers("/**/admin/accounts")
                        .access("hasRole('ADMIN')")
                        .antMatchers("/")
                        .permitAll());
        // http.formLogin(l -> l.successHandler(loginSuccessHandler));
        http.csrf().disable();
    }
}
