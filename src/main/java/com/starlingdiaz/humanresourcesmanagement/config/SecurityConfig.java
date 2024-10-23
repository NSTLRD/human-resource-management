/**
 * @author Starling Diaz on 10/20/2024.
 * @Github https://github.com/NSTLRD
 * @Website https://mentorly.blog/
 * @Academy https://www.mentor-ly.com/
 * @version human-resources-management 1.0
 * @since 10/20/2024.
 */

package com.starlingdiaz.humanresourcesmanagement.config;

import com.starlingdiaz.humanresourcesmanagement.security.JwFilterServiceSecurity;
import jakarta.servlet.Filter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
@EnableMethodSecurity(securedEnabled = true)
public class SecurityConfig{

    private final JwFilterServiceSecurity jwtAuthFilter;
    private final AuthenticationProvider authenticationProvider;

    public static String[] PUBLIC_URLS = {
            "/v1/api/***" ,
            "/v2/api-docs" ,
            "/v3/api-docs/**" ,
            "/v3/api-docs/swagger-config",
            "/swagger-resources/**",
            "/swagger-ui.html",
            "/h2-console/login.do",
            "/h2-console/**",
            "/swagger-ui/**",
            "/swagger-ui/index.html",
            "/api/v1/users/register",
            "/api/v1/users/login",
            "/api/v1/**",
            "/api/auth/**",
            "/api/v1/users/activate-account",
    };

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.cors(withDefaults())
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(req ->
                        req.requestMatchers(PUBLIC_URLS)
                                .permitAll()
                                .anyRequest().authenticated()
                )
                .sessionManagement(sessionManagement ->
                        sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authenticationProvider(authenticationProvider)
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);

        http.headers().frameOptions().disable();
        return http.build();
    }

}