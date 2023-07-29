package com.ou.configs;

import java.util.Properties;
import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.core.env.Environment;
import org.springframework.format.FormatterRegistry;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.validation.Validator;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.DefaultServletHandlerConfigurer;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.ou.pojo.Account;
import com.ou.repository.interfaces.AccountRepository;

@Configuration
@EnableWebMvc
@ComponentScan(basePackages = {
        "com.ou.controller",
        "com.ou.repository",
        "com.ou.service",
        "com.ou.validator",
        "com.ou.api",
        "com.ou.handler"
})
@PropertySource("classpath:configs.properties")
@EnableTransactionManagement
public class WebApplicationContextConfig implements WebMvcConfigurer {
    @Autowired
    private Environment environment;

    @Autowired
    private AccountRepository accountRepository;

    @Override
    public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) {
        configurer.enable();
    }

    // @Bean
    // public InternalResourceViewResolver internalResourceViewResolver() {
    // InternalResourceViewResolver r = new InternalResourceViewResolver();
    // r.setViewClass(JstlView.class);
    // r.setPrefix("/WEB-INF/pages/");
    // r.setSuffix(".jsp");

    // return r;
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

    @Bean
    public JavaMailSender getJavaMailSender() {
        JavaMailSenderImpl javaMailSenderImpl = new JavaMailSenderImpl();
        javaMailSenderImpl.setHost("cp03hn.emailserver.net.vn");
        javaMailSenderImpl.setUsername("admin@ousocialnetwork.id.vn");
        javaMailSenderImpl.setPassword("Vo2Z%)nF_+,{");
        javaMailSenderImpl.setPort(465);

        Properties mailProperties = new Properties();
        // mailProperties.put("mail.transport.protocol", "smtp");
        // // mailProperties.put("mail.smtp.auth", true);
        // mailProperties.put("mail.smtp.starttls.enable", true);
        // mailProperties.put("mail.smtp.ssl.protocols", "TLSv1.2");
        // mailProperties.put("mail.smtp.ssl.enabledProtocols", "TLSv1.2");
        // // mailProperties.put("mail.smtp.ssl.trust", "smtp.gmail.com");
        // mailProperties.put("mail.debug", "true");
        

        mailProperties.put("mail.smtp.host", "cp03hn.emailserver.net.vn");
        mailProperties.put("mail.smtp.socketFactory.port", "465");
        // mailProperties.put("mail.imap.host", "cp03hn.emailserver.net.vn");
        // mailProperties.put("mail.imap.socketFactory.port", "993");

        mailProperties.put("mail.smtp.auth", "true");
        mailProperties.put("mail.smtp.ssl.enable", "true"); // Enable SSL/TLS for SMTP
        mailProperties.put("mail.smtp.ssl.protocols", "TLSv1.2");
        mailProperties.put("mail.smtp.ssl.enabledProtocols", "TLSv1.2");
        mailProperties.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory"); // Required for SSL/TLS
        mailProperties.put("mail.smtp.socketFactory.fallback", "false");

        // Set the IMAP properties for SSL/TLS
        mailProperties.put("mail.imap.ssl.enable", "true"); // Enable SSL/TLS for IMAP
        mailProperties.put("mail.imap.ssl.protocols", "TLSv1.2");
        mailProperties.put("mail.imap.ssl.enabledProtocols", "TLSv1.2");
        mailProperties.put("mail.imap.socketFactory.class", "javax.net.ssl.SSLSocketFactory"); // Required for SSL/TLS
        mailProperties.put("mail.imap.socketFactory.fallback", "false");
        mailProperties.put("mail.debug", "true");
        javaMailSenderImpl.setJavaMailProperties(mailProperties);
        return javaMailSenderImpl;
    }

    @Override
    public Validator getValidator() {
        return validator();
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/resources/**").addResourceLocations("/resources/");
    }

    // @Bean
    // public WebAppValidator getWebAppValidator(){
    // Set<Validator> springValidators = new HashSet<>();
    // springValidators.add(new PassValidator());

    // WebAppValidator validator = new WebAppValidator();
    // validator.setSpringValidators(springValidators);

    // return validator;
    // }

    @Override
    public void addFormatters(FormatterRegistry registry) {
        // registry.addFormatter(new CategoryFormatter());
        // In case of needed to format fields of pojo
        // create new class and
        // implements the Formatter<T> interface
        // might not necessary
    }

    @Bean
    public UserDetailsService getUserDetailsService() {
        return new UserDetailsService() {
            @Override
            public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
                Account account = accountRepository.findByEmail(email).get();
                if (account == null) {
                    throw new UsernameNotFoundException("Email không tồn tại");
                }
                Set<GrantedAuthority> authorities = new HashSet<>();
                authorities.add(new SimpleGrantedAuthority(account.getRoleId().getName()));
                return new org.springframework.security.core.userdetails.User(
                        account.getEmail(), account.getPassword(), authorities);
            }
        };
    }

    @Bean
    public AuthenticationManager getAuthenticationManager(AuthenticationConfiguration auth) throws Exception{
        return auth.getAuthenticationManager();
    }

    @Override
	public void addCorsMappings(CorsRegistry registry) {

		registry.addMapping("/api/**")
			.allowedOrigins("http://localhost:3000")
			.allowedMethods("PUT", "DELETE", "GET", "POST")
			.allowedHeaders("Authorization")
			.exposedHeaders("Authorization")
			.allowCredentials(true).maxAge(3600);

		// Add more mappings...
	}
}
