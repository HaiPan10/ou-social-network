package com.ou.configs;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.core.env.Environment;
import org.springframework.format.FormatterRegistry;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.validation.Validator;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;
import org.springframework.web.servlet.config.annotation.DefaultServletHandlerConfigurer;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.ou.handler.LoginSuccessHandler;

@Configuration
@EnableWebMvc
@ComponentScan(basePackages = {
    "com.ou.controller",
    "com.ou.repository",
    "com.ou.service",
    "com.ou.validator",
    "com.ou.api"
})
@PropertySource("classpath:configs.properties")
@EnableTransactionManagement
public class WebApplicationContextConfig implements WebMvcConfigurer {
    @Autowired
    private Environment environment;

    @Override
    public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) {
        configurer.enable();
    }

    // @Bean
    // public InternalResourceViewResolver internalResourceViewResolver() {
    //     InternalResourceViewResolver r = new InternalResourceViewResolver();
    //     r.setViewClass(JstlView.class);
    //     r.setPrefix("/WEB-INF/pages/");
    //     r.setSuffix(".jsp");

    //     return r;
    // }

    @Bean
    public Cloudinary getCloudinary() {
        return new Cloudinary(ObjectUtils.asMap(
                "cloud_name", environment.getProperty("CLOUDINARY_CLOUD_NAME"),
                "api_key", environment.getProperty("CLOUDINARY_API_KEY"),
                "api_secret", environment.getProperty("CLOUDINARY_API_SECRET"),
                "secure", true));
    }

    @Bean(name = "validator")
    public LocalValidatorFactoryBean validator() {
        LocalValidatorFactoryBean bean = new LocalValidatorFactoryBean();
        bean.setValidationMessageSource(messageSource());
        return bean;
    }

    @Bean
    public MessageSource messageSource() {
        ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();
        messageSource.setBasename("classpath:messages");
        messageSource.setDefaultEncoding("UTF-8");
        return messageSource;
    }

    @Override
    public Validator getValidator(){
        return validator();
    }

    // @Bean
    // public WebAppValidator getWebAppValidator(){
    //     Set<Validator> springValidators = new HashSet<>();
    //     springValidators.add(new PassValidator());

    //     WebAppValidator validator = new WebAppValidator();
    //     validator.setSpringValidators(springValidators);

    //     return validator;
    // }

    @Override
    public void addFormatters(FormatterRegistry registry) {
        // registry.addFormatter(new CategoryFormatter());
        // In case of needed to format fields of pojo
        // create new class and
        // implements the Formatter<T> interface
        // might not necessary
    }

    // @Bean
    // public LoginSuccessHandler loginSuccessHandler(){
    //     return new LoginSuccessHandler();
    // }
}
