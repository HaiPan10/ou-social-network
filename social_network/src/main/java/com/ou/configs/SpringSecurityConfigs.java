package com.ou.configs;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.ou.handler.LoginSuccessHandler;

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
    "com.ou.configs"
})
public class SpringSecurityConfigs extends WebSecurityConfigurerAdapter {
    @Autowired
    private UserDetailsService accountService;

	@Autowired
	private LoginSuccessHandler loginSuccessHandler;

    @Autowired
    private JwtTokenFilter jwtTokenFilter;

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
        http.csrf().disable();
        http.cors();
        http.sessionManagement(management -> {
            management.sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        });
        http.formLogin(login ->
                login.loginPage("/")
                .usernameParameter("email")
                .passwordParameter("password")
                .successHandler(loginSuccessHandler)
                .failureUrl("/login?error"));
        http.authorizeRequests(requests ->
                requests.antMatchers("/**/admin/**")
                        .access("hasAnyRole('ROLE_ADMIN')")
                        .antMatchers("/",
                                    "/api/accounts/login",
                                    "/api/accounts/register",
                                    // "/api/email/verify/**",
                                    "/api/accounts/verify/**/**",
                                    "/resources/**")
                        .permitAll()
                        .anyRequest()
                        .authenticated());
        http.exceptionHandling(handling ->
            handling.authenticationEntryPoint((requests, reponse, ex) -> {
                System.out.printf("[EXCEPTION] - %s\n", ex.getMessage());
                reponse.sendError(HttpServletResponse.SC_UNAUTHORIZED, ex.getMessage());
        }));
        http.addFilterBefore(jwtTokenFilter, UsernamePasswordAuthenticationFilter.class);
    }
}
