package com.ou.configs;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.web.servlet.config.annotation.DefaultServletHandlerConfigurer;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.springframework.web.servlet.view.JstlView;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;

@Configuration
@EnableWebMvc
@ComponentScan(basePackages = {
    "com.ou.controller",
    "com.ou.repository",
    "com.ou.service",
    "com.ou.validator"
})
@PropertySource("classpath:configs.properties")
public class WebApplicationContextConfig implements WebMvcConfigurer{
    @Autowired
    private Environment environment;

    @Override
    public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) {
        configurer.enable();
    }

    @Bean
    public InternalResourceViewResolver internalResourceViewResolver() {
        InternalResourceViewResolver r = new InternalResourceViewResolver();
        r.setViewClass(JstlView.class);
        r.setPrefix("/WEB-INF/pages/");
        r.setSuffix(".jsp");

        return r;
    }

    @Bean
    public Cloudinary getCloudinary(){
        return new Cloudinary(ObjectUtils.asMap(
            "cloud_name", environment.getProperty("CLOUDINARY_CLOUD_NAME"),
            "api_key", environment.getProperty("CLOUDINARY_API_KEY"),
            "api_secret", environment.getProperty("CLOUDINARY_API_SECRET"),
            "secure", true
        ));
    }
}
