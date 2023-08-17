package com.ou.configs;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.cors.CorsConfigurationSource;

import com.ou.filter.CustomAccessDeniedHandler;
import com.ou.filter.JwtTokenFilter;
import com.ou.filter.RestAuthenticationEntryPoint;

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
@Order(1)
public class JwtSecurityConfigs extends WebSecurityConfigurerAdapter {

    @Autowired
    private CorsConfigurationSource corsConfigurationSource;

    @Bean
    public JwtTokenFilter jwtTokenFilter() throws Exception {
        JwtTokenFilter jwtAuthenticationTokenFilter = new JwtTokenFilter();
        jwtAuthenticationTokenFilter.setAuthenticationManager(authenticationManager());
        return jwtAuthenticationTokenFilter;
    }

    @Bean
    @Override
    protected AuthenticationManager authenticationManager() throws Exception {
        return super.authenticationManager();
    }

    @Bean
    public RestAuthenticationEntryPoint restServicesEntryPoint() {
        return new RestAuthenticationEntryPoint();
    }

    @Bean
    public CustomAccessDeniedHandler customAccessDeniedHandler() {
        return new CustomAccessDeniedHandler();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf(csrf -> csrf.ignoringAntMatchers("/api/**"));
        http.cors(cors -> cors.configurationSource(corsConfigurationSource));
        http.antMatcher("/api/**").httpBasic(basic -> basic.authenticationEntryPoint(restServicesEntryPoint()))
                .sessionManagement(management -> management.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeRequests(requests -> requests.antMatchers("/api/accounts/login",
                        "/api/accounts/register",
                        // "/api/email/verify/**",
                        "/api/accounts/verify/**/**")
                        .permitAll()
                        .anyRequest()
                        .authenticated())
                .exceptionHandling(handling -> handling.accessDeniedHandler(customAccessDeniedHandler()));
        http.addFilterBefore(jwtTokenFilter(), UsernamePasswordAuthenticationFilter.class);
    }
}
