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
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableWebSecurity
@EnableTransactionManagement
@ComponentScan(basePackages = {
    "com.ou.controller",
    "com.ou.repository",
    "com.ou.service",
    "com.ou.validator",
    "com.ou.api",
    "com.ou.handler",
    "com.ou.configs",
    "com.ou.components"
})
@Order(2)
public class SpringSecurityConfigs extends WebSecurityConfigurerAdapter {
    @Autowired
    private UserDetailsService accountService;

	// @Autowired
	// private LoginSuccessHandler loginSuccessHandler;

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
    
    @Override
    protected void configure(AuthenticationManagerBuilder auth)
            throws Exception {
        auth.userDetailsService(accountService)
                .passwordEncoder(passwordEncoder());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf(csrf -> csrf.disable());
        http.formLogin(login ->
                login.loginPage("/")
                .usernameParameter("email")
                .passwordParameter("password")
                .defaultSuccessUrl("/admin/dashboard")
                .failureUrl("/"));
        http.logout(logout -> logout.logoutSuccessUrl("/"));
        http.authorizeRequests(requests ->
                requests.antMatchers("/**/admin/**")
                        .access("hasAnyRole('ROLE_ADMIN')")
                        .antMatchers("/",
                                    "/resources/**",
                                    "/login")
                        .permitAll()
                        .anyRequest()
                        .authenticated());
        http.exceptionHandling(handling ->
            handling.accessDeniedHandler((requests, reponse, ex) -> {
                System.out.printf("[EXCEPTION] - %s\n", ex.getMessage());
                reponse.sendRedirect(requests.getContextPath() + "/logout");
        }));
    }
}
