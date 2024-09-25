package com.InvestmentsTracker.investment_portfolio.config;

import com.InvestmentsTracker.investment_portfolio.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Autowired
    private UserService userService;

    // Bean for password encoding
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    // Authentication provider bean
    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();

        authProvider.setUserDetailsService(userService);
        authProvider.setPasswordEncoder(passwordEncoder());

        return authProvider;
    }

    // Security filter chain bean
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                // Configure CSRF
                .csrf(AbstractHttpConfigurer::disable)
                // Configure URL authorization
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers("/register", "/login", "/css/**", "/js/**").permitAll()
                        .anyRequest().authenticated()
                )
                // Configure form login
                .formLogin(formLogin -> formLogin
                        .loginPage("/login")
                        .defaultSuccessUrl("/dashboard", true)
                        .permitAll()
                )
                // Configure logout
                .logout(logout -> logout
                        .logoutSuccessUrl("/login?logout")
                        .permitAll()
                )
                // Configure authentication provider
                .authenticationProvider(authenticationProvider());

        return http.build();
    }
}
