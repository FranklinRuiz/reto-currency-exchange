package pe.reto.retocurrencyexchange.adapter.configuration;

import org.springframework.http.HttpHeaders;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.ReactiveSecurityContextHolder;
import org.springframework.security.core.userdetails.ReactiveUserDetailsService;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

public class JWTAuthenticationWebFilter implements WebFilter {

    private final JWTUtil jwtUtil;
    private final ReactiveUserDetailsService userDetailsService;

    public JWTAuthenticationWebFilter(JWTUtil jwtUtil, ReactiveUserDetailsService userDetailsService) {
        this.jwtUtil = jwtUtil;
        this.userDetailsService = userDetailsService;
    }

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
        ServerHttpRequest request = exchange.getRequest();
        String authHeader = request.getHeaders().getFirst(HttpHeaders.AUTHORIZATION);
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            String authToken = authHeader.substring(7);
            String username;
            try {
                username = jwtUtil.extractUsername(authToken);
            } catch (Exception e) {
                return chain.filter(exchange);
            }
            if (username != null) {
                return userDetailsService.findByUsername(username)
                        .flatMap(userDetails -> {
                            if (jwtUtil.validateToken(authToken, userDetails.getUsername())) {
                                UsernamePasswordAuthenticationToken auth =
                                        new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                                return chain.filter(exchange)
                                        .contextWrite(ReactiveSecurityContextHolder.withAuthentication(auth));
                            }
                            return chain.filter(exchange);
                        });
            }
        }
        return chain.filter(exchange);
    }
}
