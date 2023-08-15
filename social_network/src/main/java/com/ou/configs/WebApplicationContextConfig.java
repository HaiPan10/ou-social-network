package com.ou.configs;

import java.util.Properties;
import java.io.IOException;
import java.io.InputStream;
import java.security.GeneralSecurityException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.core.env.Environment;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.format.FormatterRegistry;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.validation.Validator;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CharacterEncodingFilter;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.config.annotation.DefaultServletHandlerConfigurer;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.messaging.FirebaseMessaging;
import com.ou.pojo.Account;
import com.ou.repository.interfaces.AccountRepository;
import com.sun.mail.util.MailSSLSocketFactory;

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

    @Autowired
    private ResourceLoader resourceLoader;

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
    public JavaMailSender getJavaMailSender() throws GeneralSecurityException {
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

        MailSSLSocketFactory sf = new MailSSLSocketFactory();
        sf.setTrustAllHosts(true);
        mailProperties.put("mail.smtp.ssl.trust", "*");
        mailProperties.put("mail.smtp.ssl.socketFactory", sf);
        javaMailSenderImpl.setJavaMailProperties(mailProperties);
        return javaMailSenderImpl;
    }

    @Override
    public Validator getValidator() {
        return validator();
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/resources/**").addResourceLocations("/WEB-INF/resources/");
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
    public CorsConfigurationSource corsConfigurationSource() {
        // String clientHostname = environment.getProperty("CLIENT_HOSTNAME");
        CorsConfiguration configuration = new CorsConfiguration();
        // configuration.setAllowedOrigins(Arrays.asList(clientHostname));
        configuration.setAllowedOrigins(Arrays.asList("*"));
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "PATCH", "DELETE", "OPTIONS"));
        configuration.setAllowedHeaders(Arrays.asList("Authorization", "Content-Type", "X-Auth-Token"));
        configuration.setExposedHeaders(Arrays.asList("X-Auth-Token"));
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

    @Bean
    public CommonsMultipartResolver multipartResolver() {
        CommonsMultipartResolver resolver = new CommonsMultipartResolver();
        resolver.setDefaultEncoding("UTF-8");
        return resolver;
    }

    @Bean
    public CharacterEncodingFilter characterEncodingFilter() {
        CharacterEncodingFilter filter = new CharacterEncodingFilter();
        filter.setEncoding("UTF-8");
        filter.setForceEncoding(true);
        return filter;
    }

    @Bean
    public ObjectMapper objectMapper() {
        return new ObjectMapper();
    }

    @Bean
    public ScheduledExecutorService getScheduledService() {
        int threadNumber = Integer.parseInt(environment.getProperty("THREAD_NUMBER"));
        ScheduledExecutorService configs = Executors.newScheduledThreadPool(threadNumber);
        return configs;
    }

    @Bean
    public FirebaseMessaging firebaseMessaging(FirebaseApp firebaseApp) {
        System.out.println("[DEBUG] - Starting create Firebase Messaging");
        return FirebaseMessaging.getInstance(firebaseApp);
    }

    @Bean
    public FirebaseApp firebaseApp(GoogleCredentials credentials) {
        if(FirebaseApp.getApps().isEmpty()){
            System.out.println("[DEBUG] - Starting create Firebase app");
            FirebaseOptions options = FirebaseOptions.builder()
                    .setCredentials(credentials)
                    .build();
            System.out.println("[DEBUG] - Successfully create Firebase options");
            return FirebaseApp.initializeApp(options);
        } else {
            return FirebaseApp.getInstance();
        }
        
    }

    @Bean
    public GoogleCredentials googleCredentials() throws IOException {
        try {
            Resource resource = resourceLoader.getResource("classpath:serviceAccountKey.json");
            try (InputStream serviceAccount = resource.getInputStream()) {
                System.out.println("[DEBUG] - ACCOUNT SERVICE CREDENTIAL");
                return GoogleCredentials.fromStream(serviceAccount);
            }

        } catch (IOException ex) {
            System.out.println("[DEBUG] - DEFAULT CREDENTIAL");
            throw new IOException("FAIL TO INIT FIREBASE APP");
        }
    }
}
