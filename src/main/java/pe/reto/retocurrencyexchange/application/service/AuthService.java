package pe.reto.retocurrencyexchange.application.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;
import pe.reto.retocurrencyexchange.adapter.configuration.JWTUtil;
import pe.reto.retocurrencyexchange.application.exception.ExchangeRateException;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final ReactiveAuthenticationManager loginAuthenticationManager;
    private final JWTUtil jwtUtil;

    public Mono<String> authenticate(String username, String password) {
        return loginAuthenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(username, password))
                .map(auth -> jwtUtil.generateToken(username))
                .onErrorResume(throwable -> {
                    if (throwable instanceof BadCredentialsException) {
                        return Mono.error(new ExchangeRateException("Credenciales inválidas: Usuario o contraseña no son correctos. Por favor, inténtelo nuevamente."));
                    }
                    return Mono.error(throwable);
                });
    }
}
