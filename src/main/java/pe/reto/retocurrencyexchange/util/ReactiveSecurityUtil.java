package pe.reto.retocurrencyexchange.util;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.ReactiveSecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import reactor.core.publisher.Mono;

import java.util.Optional;

@Slf4j
public final class ReactiveSecurityUtil {

    private ReactiveSecurityUtil() {
    }

    public static Mono<Optional<String>> getCurrentUsername() {
        return getAuthenticationReactive()
                .map(authentication -> {
                    Object principal = authentication.getPrincipal();
                    if (principal instanceof UserDetails userDetails) {
                        return Optional.of(userDetails.getUsername());
                    } else if (principal instanceof String s) {
                        return Optional.of(s);
                    }
                    return Optional.empty();
                });
    }

    private static Mono<Authentication> getAuthenticationReactive() {
        return ReactiveSecurityContextHolder.getContext()
                .mapNotNull(ctx -> {
                    Authentication auth = ctx.getAuthentication();
                    if (auth == null) {
                        log.debug("No hay información de autenticación en el Security Context reactivo.");
                    }
                    return auth;
                });
    }
}
