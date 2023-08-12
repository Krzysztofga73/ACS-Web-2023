package com.example.accesscontrolsystem.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    @Bean
    protected SecurityFilterChain configure(HttpSecurity http) throws Exception {
        return http.authorizeHttpRequests(auth -> {
                    auth.requestMatchers("/login", "/loginError", "/error").permitAll()
                            .requestMatchers("/**").authenticated();
                })
                .formLogin(login -> login.loginPage("/login").permitAll().failureUrl("/loginError"))
                .logout(logout -> logout.logoutSuccessUrl("/login"))
                .csrf(csrf -> csrf.disable()).build();
    }

    @Bean
    public UserDetailsService users() {
        UserDetails user = User.builder()
                .username("admin")
                .password("{noop}admin")
                .roles("USER")
                .build();
        return new InMemoryUserDetailsManager(user);
    }

//NASZE
    /*
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

*/
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
/*
//SLACK AGA

package com.example.pantry.config;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
@RequiredArgsConstructor
@Configuration
@EnableWebSecurity
public class Auth {
@Bean
protected SecurityFilterChain configure(HttpSecurity http) throws Exception {
    return http.authorizeHttpRequests(auth -> {
                auth.requestMatchers("/login", "/loginError", "/error").permitAll()
                        .requestMatchers("/**").authenticated();
            })
            .formLogin(login -> login.loginPage("/login").permitAll().failureUrl("/loginError"))
            .logout(logout -> logout.logoutSuccessUrl("/login"))
            .csrf(csrf -> csrf.disable()).build();
}
    @Bean
    public UserDetailsService users() {
        UserDetails user = User.builder()
                .username("user")
                .password("{noop}password")
                .roles("USER")
                .build();
        return new InMemoryUserDetailsManager(user);
    }
}

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
@Controller
public class AuthController {
    @GetMapping("/login")
    public String getLogin() {
        return "loginView";
    }
    @GetMapping("/loginError")
    public String showError() {
        return "loginErrorView";
    }
}

//SLACK MACIEJ
package com.example.pantry.config;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
@RequiredArgsConstructor
@Configuration
@EnableWebSecurity
public class Auth {
@Bean
protected SecurityFilterChain configure(HttpSecurity http) throws Exception {
    return http.authorizeHttpRequests(auth -> {
                auth.requestMatchers("/login", "/loginError", "/error").permitAll()
                        .requestMatchers("/**").authenticated();
            })
            .formLogin(login -> login.loginPage("/login").permitAll().failureUrl("/loginError"))
            .logout(logout -> logout.logoutSuccessUrl("/login"))
            .csrf(csrf -> csrf.disable()).build();
}
    @Bean
    public UserDetailsService users() {
        UserDetails user = User.builder()
                .username("user")
                .password("{noop}password")
                .roles("USER")
                .build();
        return new InMemoryUserDetailsManager(user);
    }
}

 */