package pe.reto.retocurrencyexchange.adapter.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.authentication.UserDetailsRepositoryReactiveAuthenticationManager;
import org.springframework.security.config.web.server.SecurityWebFiltersOrder;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.core.userdetails.MapReactiveUserDetailsService;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.ReactiveUserDetailsService;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.security.web.server.context.NoOpServerSecurityContextRepository;

@Configuration
public class SecurityConfig {

    @Value("${jwt.user-default.username}")
    private String usernameDefault;

    @Value("${jwt.user-default.password}")
    private String passwordDefault;

    private final JWTUtil jwtUtil;

    public SecurityConfig(JWTUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }

    @Bean
    public MapReactiveUserDetailsService userDetailsService(PasswordEncoder passwordEncoder) {
        return new MapReactiveUserDetailsService(
                User.withUsername(usernameDefault)
                        .password(passwordEncoder.encode(passwordDefault))
                        .roles("USER")
                        .build()
        );
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

    @Bean("jwtAuthenticationManager")
    public ReactiveAuthenticationManager jwtAuthenticationManager(ReactiveUserDetailsService userDetailsService) {
        return new JWTReactiveAuthenticationManager(jwtUtil, userDetailsService);
    }

    @Primary
    @Bean("loginAuthenticationManager")
    public ReactiveAuthenticationManager loginAuthenticationManager(ReactiveUserDetailsService userDetailsService, PasswordEncoder passwordEncoder) {
        UserDetailsRepositoryReactiveAuthenticationManager authManager =
                new UserDetailsRepositoryReactiveAuthenticationManager(userDetailsService);
        authManager.setPasswordEncoder(passwordEncoder);
        return authManager;
    }

    @Bean
    public SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity http, ReactiveUserDetailsService userDetailsService) {
        return http
                .csrf(ServerHttpSecurity.CsrfSpec::disable)
                .httpBasic(ServerHttpSecurity.HttpBasicSpec::disable)
                .formLogin(ServerHttpSecurity.FormLoginSpec::disable)
                .securityContextRepository(NoOpServerSecurityContextRepository.getInstance())
                .authorizeExchange(exchange -> exchange
                        .pathMatchers("/api/v1/auth/**").permitAll()
                        .anyExchange().authenticated()
                )
                .addFilterAt(new JWTAuthenticationWebFilter(jwtUtil, userDetailsService), SecurityWebFiltersOrder.AUTHENTICATION)
                .build();
    }

}
