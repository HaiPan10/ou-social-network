package com.ou.configs;

import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

public class DispatcherServletInit extends 
    AbstractAnnotationConfigDispatcherServletInitializer{

    @Override
    protected Class<?>[] getRootConfigClasses() {
        return new Class[] {
            HibernateConfig.class,
            SpringSecurityConfigs.class,
            JwtService.class,
            TilesConfig.class,
            JwtTokenFilter.class
        };
    }

    @Override
    protected Class<?>[] getServletConfigClasses() {
        return new Class[] {
            WebApplicationContextConfig.class
        };
    }

    @Override
    protected String[] getServletMappings() {
        return new String[] 
        {
            "/", "/api/accounts/register", "/api/accounts/login", "/login", "/api/user/update_avatar"
        };
    }
    
}
