package com.example.schoolapp.config;

import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.servlet.util.matcher.MvcRequestMatcher;
import org.springframework.web.servlet.handler.HandlerMappingIntrospector;

import static org.springframework.security.config.Customizer.withDefaults;


@Configuration
public class ProjectSecurityConfig {

    @Bean
    SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http) throws Exception {
        http.csrf((csrf) -> csrf.ignoringRequestMatchers("/saveMsg")
                .ignoringRequestMatchers("/public/**")
                .ignoringRequestMatchers("/api/**")
                .ignoringRequestMatchers("/data-sapi/**")
                .ignoringRequestMatchers("/schoolapp.actuator/**"))
                .authorizeHttpRequests((requests) -> requests.requestMatchers("/", "/home").permitAll()
                .requestMatchers("/dashboard").authenticated()
                .requestMatchers("/displayMessages/**").hasRole("ADMIN")
                .requestMatchers("/closeMsg/**").hasRole("ADMIN")
                .requestMatchers("/admin/**").hasRole("ADMIN")
                .requestMatchers("/schoolapp/actuator/**").hasRole("ADMIN")
                .requestMatchers("/student/**").hasRole("STUDENT")
                .requestMatchers("/api/**").authenticated()
                .requestMatchers("/data-api/**").authenticated()
                .requestMatchers("/displayProfile").authenticated()
                .requestMatchers("/updateProfile").authenticated()
                .requestMatchers("/login").permitAll()
                .requestMatchers("/logout").permitAll()
                .requestMatchers("/holidays/**").permitAll()
                .requestMatchers("/contact").permitAll()
                .requestMatchers("/saveMsg").permitAll()
                .requestMatchers("/courses").permitAll()
                .requestMatchers("/about").permitAll()
                .requestMatchers("/public/**").permitAll()
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
    public PasswordEncoder passwordEncode() {
        return new BCryptPasswordEncoder();
    }


//    @Bean
//    SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http, HandlerMappingIntrospector introspector) throws Exception {
//
//        MvcRequestMatcher.Builder mvcMatcherBuilder = new MvcRequestMatcher.Builder(introspector);
//
//        http.csrf((csrf) -> csrf.ignoringRequestMatchers(mvcMatcherBuilder.pattern("/saveMsg"))
//                        .ignoringRequestMatchers(PathRequest.toH2Console()))
//                .authorizeHttpRequests((requests) -> requests.requestMatchers(mvcMatcherBuilder.pattern("/")).permitAll()
//                        .requestMatchers(mvcMatcherBuilder.pattern("/home")).permitAll()
//                        .requestMatchers(mvcMatcherBuilder.pattern("/dashboard")).authenticated()
//                        .requestMatchers(mvcMatcherBuilder.pattern("/login")).permitAll()
//                        .requestMatchers(mvcMatcherBuilder.pattern("/logout")).permitAll()
//                        .requestMatchers(mvcMatcherBuilder.pattern("/holidays/**")).permitAll()
//                        .requestMatchers(mvcMatcherBuilder.pattern("/contact")).permitAll()
//                        .requestMatchers(mvcMatcherBuilder.pattern("/saveMsg")).permitAll()
//                        .requestMatchers(mvcMatcherBuilder.pattern("/courses")).permitAll()
//                        .requestMatchers(mvcMatcherBuilder.pattern("/about")).permitAll()
//                        .requestMatchers(mvcMatcherBuilder.pattern("/assets/**")).permitAll()
//                        .requestMatchers(PathRequest.toH2Console()).permitAll()
//                );
//
//        http.formLogin(httpSecurityFormLoginConfigurer -> httpSecurityFormLoginConfigurer.loginPage("/login")
//                .defaultSuccessUrl("/dashboard")
//                .failureUrl("/login?error=true")
//                .permitAll()
//        );
//
//        http.logout(httpSecurityLogoutConfigurer -> httpSecurityLogoutConfigurer.logoutUrl("/logout")
//                .logoutSuccessUrl("/login?logout=true")
//                .invalidateHttpSession(true)
//                .permitAll()
//        );
//
//        http.httpBasic(withDefaults());
//
//        http.headers(httpSecurityHeadersConfigurer -> httpSecurityHeadersConfigurer.frameOptions(frameOptionsConfig -> frameOptionsConfig.disable()));
//
//        return http.build();
//    }
}
