package pe.reto.retocurrencyexchange.adapter.configuration;

import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.ReactiveUserDetailsService;
import reactor.core.publisher.Mono;

public class JWTReactiveAuthenticationManager implements ReactiveAuthenticationManager {

    private final JWTUtil jwtUtil;
    private final ReactiveUserDetailsService userDetailsService;

    public JWTReactiveAuthenticationManager(JWTUtil jwtUtil, ReactiveUserDetailsService userDetailsService) {
        this.jwtUtil = jwtUtil;
        this.userDetailsService = userDetailsService;
    }

    @Override
    public Mono<org.springframework.security.core.Authentication> authenticate(org.springframework.security.core.Authentication authentication) {
        String authToken = authentication.getCredentials().toString();
        String username;
        try {
            username = jwtUtil.extractUsername(authToken);
        } catch (Exception e) {
            return Mono.empty();
        }
        return userDetailsService.findByUsername(username)
                .filter(userDetails -> jwtUtil.validateToken(authToken, userDetails.getUsername()))
                .map(userDetails -> new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities()));
    }
}
