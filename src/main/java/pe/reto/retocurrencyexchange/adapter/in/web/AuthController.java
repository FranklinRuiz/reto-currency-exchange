package pe.reto.retocurrencyexchange.adapter.in.web;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.*;
import pe.reto.retocurrencyexchange.adapter.dto.request.AuthRequest;
import pe.reto.retocurrencyexchange.adapter.dto.response.AuthResponse;
import pe.reto.retocurrencyexchange.application.service.AuthService;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping(value = "/login", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<ResponseEntity<AuthResponse>> login(@RequestBody AuthRequest request) {
        return authService.authenticate(request.getUsername(), request.getPassword())
                .map(token -> ResponseEntity.ok(new AuthResponse(token)))
                .onErrorResume(BadCredentialsException.class, e -> Mono.just(ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                        .build()));
    }
}
