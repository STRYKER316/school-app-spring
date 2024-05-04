package com.example.schoolapp.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import static org.springframework.security.config.Customizer.withDefaults;


@Configuration
public class ProjectSecurityConfig {

    @Bean
    SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http) throws Exception {
        http.csrf((csrf) -> csrf.disable())
                .authorizeHttpRequests((requests) -> requests.requestMatchers("/", "/home").permitAll()
                .requestMatchers("/dashboard").authenticated()
                .requestMatchers("/login").permitAll()
                .requestMatchers("/holidays/**").permitAll()
                .requestMatchers("/contact").permitAll()
                .requestMatchers("/saveMsg").permitAll()
                .requestMatchers("/courses").permitAll()
                .requestMatchers("/about").permitAll()
                .requestMatchers("/assets/**").permitAll()
        );

        http.formLogin(httpSecurityFormLoginConfigurer -> httpSecurityFormLoginConfigurer.loginPage("/login")
                .defaultSuccessUrl("/dashboard")
                .failureUrl("/login?error=true")
                .permitAll()
        );

        http.logout(httpSecurityLogoutConfigurer -> httpSecurityLogoutConfigurer.logoutUrl("/logout")
                .logoutSuccessUrl("/login?logout=true")
                .invalidateHttpSession(true)
                .permitAll()
        );

        http.httpBasic(withDefaults());

        return http.build();
    }


    @Bean
    public InMemoryUserDetailsManager userDetailsService() {

        UserDetails user = User.withDefaultPasswordEncoder()
                .username("user")
                .password("12345")
                .roles("USER")
                .build();
        UserDetails admin = User.withDefaultPasswordEncoder()
                .username("admin")
                .password("54321")
                .roles("USER", "ADMIN")
                .build();
        return new InMemoryUserDetailsManager(user, admin);
    }
}
