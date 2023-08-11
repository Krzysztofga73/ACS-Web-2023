package com.example.accesscontrolsystem.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {


    @Bean
    protected SecurityFilterChain configure(HttpSecurity http) throws Exception {

        http.authorizeHttpRequests(auth -> {
                    auth.requestMatchers("/login").permitAll()
                            .requestMatchers("/**").authenticated();
                })
                .formLogin(login -> login.permitAll())
                .logout(logout -> logout.logoutSuccessUrl("/"));
        return http.build();
    }

    @Bean
    public AuthenticationManager authManager(HttpSecurity http) throws Exception {
        return http.getSharedObject(AuthenticationManagerBuilder.class)
                .inMemoryAuthentication()
                .withUser("admin")
                .password("admin").roles("ADMIN")
                .and().and()
                .build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return NoOpPasswordEncoder.getInstance();
    }


//    @Bean
//    protected SecurityFilterChain configure(HttpSecurity http) throws Exception {
//
//        http.authorizeHttpRequests(auth -> {
//                    auth.requestMatchers("/login")
//                            .permitAll().anyRequest()
//                            .authenticated();
//                })
//                .formLogin(login -> login.loginPage("/login").defaultSuccessUrl("/", true))
//                .logout(logout -> logout.logoutSuccessUrl("/login"))
//                .csrf(i->i.disable());
//
//        return http.build();
//    }
//
//    @Bean
//    public AuthenticationManager authManager(HttpSecurity http) throws Exception {
//        var authManager = http.getSharedObject(AuthenticationManagerBuilder.class);
//        authManager.inMemoryAuthentication().withUser("user").password("password").roles("USER");
//        return authManager.build();
//    }
}
