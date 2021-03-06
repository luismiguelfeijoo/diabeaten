package com.diabeaten.edgeservice.security;

import com.diabeaten.edgeservice.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.savedrequest.NullRequestCache;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled = true)
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private UserService userService;

    @Bean
    public PasswordEncoder passwordEncoder () {
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth
                .userDetailsService(userService)
                .passwordEncoder(passwordEncoder);
    }

    @Override
    public void configure(HttpSecurity httpSecurity) throws Exception {


        httpSecurity.httpBasic();

        httpSecurity.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and().authorizeRequests()
                //mvcMatchers
                .mvcMatchers("/users**").hasAuthority("ROLE_ADMIN")
                .mvcMatchers(HttpMethod.POST, "/patients").permitAll()
                .mvcMatchers(HttpMethod.GET,"/patients").hasAuthority("ROLE_ADMIN")
                .mvcMatchers(HttpMethod.DELETE,"/patients/**").hasAuthority("ROLE_ADMIN")
                .mvcMatchers("/patients/**").hasAnyAuthority("ROLE_ADMIN", "ROLE_PATIENT")
                .mvcMatchers(HttpMethod.GET,"/monitors").hasAnyAuthority("ROLE_ADMIN", "ROLE_PATIENT")
                .mvcMatchers(HttpMethod.POST,"/monitors").hasAnyAuthority("ROLE_ADMIN", "ROLE_PATIENT")
                .mvcMatchers(HttpMethod.DELETE,"/monitors/**").hasAnyAuthority("ROLE_ADMIN", "ROLE_PATIENT")
                .mvcMatchers("/monitors/**").hasAnyAuthority("ROLE_ADMIN", "ROLE_PATIENT", "ROLE_MONITOR")
                .anyRequest().permitAll()
                .and().requestCache().requestCache(new NullRequestCache()).and().httpBasic().and().cors().and().csrf().disable();



        /*
        httpSecurity.csrf().disable();
        httpSecurity.logout()
                .logoutUrl("/logout")
                .invalidateHttpSession(true)
                .deleteCookies("JSESSIONID");
        httpSecurity.httpBasic();
        httpSecurity.authorizeRequests()
                .mvcMatchers(HttpMethod.GET, "/patients").hasAnyAuthority("ROLE_ADMIN", "ROLE_PATIENT")
                .mvcMatchers(HttpMethod.POST, "/patients").hasAuthority("ROLE_ADMIN")
                .anyRequest().permitAll();


         */

    }

}
