package org.example.usermanagement.config;

import org.example.usermanagement.repository.UserRepository;
import org.example.usermanagement.security.JwtAuthenticationFilter;
import org.example.usermanagement.service.CustomUserDetailsService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@EnableMethodSecurity
public class SecurityConfig {
    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    public SecurityConfig(JwtAuthenticationFilter jwtAuthenticationFilter) {
        this.jwtAuthenticationFilter = jwtAuthenticationFilter;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .cors(withDefaults())
                .csrf(csrf -> csrf.disable())
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/auth/**").permitAll()
                        .requestMatchers(HttpMethod.POST,"/users/").hasAnyAuthority("CAN_CREATE_USER")
                        .requestMatchers(HttpMethod.GET,"/users/").hasAnyAuthority("CAN_READ_USERS")
                        .requestMatchers(HttpMethod.PUT,"/users/{id}/").hasAnyAuthority("CAN_UPDATE_USERS")
                        .requestMatchers(HttpMethod.DELETE,"/users/{id}/").hasAnyAuthority("CAN_DELETE_USERS")


                        .requestMatchers(HttpMethod.POST,"/orders/").hasAnyAuthority("CAN_PLACE_ORDER")
                        .requestMatchers(HttpMethod.GET,"/orders/").hasAnyAuthority("CAN_SEARCH_ORDER")
                        .requestMatchers(HttpMethod.GET,"/orders/search").hasAnyAuthority("CAN_SEARCH_ORDER")
                        .requestMatchers(HttpMethod.PUT,"/orders/cancel/").hasAnyAuthority("CAN_CANCEL_ORDER")
                        .requestMatchers(HttpMethod.GET,"/orders/track/{id}").hasAnyAuthority("CAN_TRACK_ORDER")
                        .requestMatchers(HttpMethod.POST,"/orders/schedule/").hasAnyAuthority("CAN_SCHEDULE_ORDER")

                        .requestMatchers("/api/dishes/**").permitAll()
                        .requestMatchers("api/exercises/**").permitAll()
                        .requestMatchers("/api/trainings/**").permitAll()
                        .requestMatchers("/api/ingredients/**").permitAll()
                        .requestMatchers(HttpMethod.OPTIONS, "/api/**").permitAll()
                        .requestMatchers("/api/meal-plans/**").permitAll()
                        .anyRequest().authenticated()
                )
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(List.of("http://localhost:4200", "https://teretana-admin.onrender.com"));
        configuration.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        configuration.setAllowedHeaders(List.of("Authorization", "Content-Type"));
        configuration.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

    @Bean
    public UserDetailsService userDetailsService(UserRepository userRepository) {
        return new CustomUserDetailsService(userRepository);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
