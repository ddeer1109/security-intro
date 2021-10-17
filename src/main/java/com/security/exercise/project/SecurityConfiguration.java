package com.security.exercise.project;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {
    private AuthenticationService authenticationService;
    private PasswordEncoder encoder;

    @Autowired
    public SecurityConfiguration(AuthenticationService authenticationService, PasswordEncoder encoder) {
        this.authenticationService = authenticationService;
        this.encoder = encoder;
    }
//
//    @Bean
//    public CorsFilter corsFilter() {
//        final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
//
//        final CorsConfiguration config = new CorsConfiguration();
//        config.setAllowCredentials(true);
//        config.setAllowedOrigins(Collections.singletonList("http://localhost:3000"));
//        config.setAllowedHeaders(Arrays.asList("Origin", "Content-Type", "Accept", "Authorization"));
//        config.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS", "PATCH"));
//
//        source.registerCorsConfiguration("/**", config);
//        return new CorsFilter(source);
//    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .cors()
                .and()
                .authorizeRequests()
                .antMatchers("/restricted/**")
                .authenticated()
                .and()
                .authorizeRequests()
                .antMatchers("/api/v1/status")
                .permitAll()
                .and()
                .formLogin()
                .loginProcessingUrl("/api/authentication")
                .successHandler(new DefaultAuthenticationSuccessHandler())
                .failureHandler(new DefaultAuthenticationFailureHandler())
                .permitAll();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth
                .eraseCredentials(true)
                .userDetailsService(authenticationService)
                .passwordEncoder(encoder);
    }
}
