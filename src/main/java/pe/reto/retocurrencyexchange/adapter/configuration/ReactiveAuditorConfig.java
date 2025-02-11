package pe.reto.retocurrencyexchange.adapter.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.ReactiveAuditorAware;
import org.springframework.data.r2dbc.config.EnableR2dbcAuditing;
import pe.reto.retocurrencyexchange.util.ReactiveSecurityUtil;

@Configuration
@EnableR2dbcAuditing(auditorAwareRef = "reactiveAuditorProvider")
public class ReactiveAuditorConfig {

    @Bean
    public ReactiveAuditorAware<String> reactiveAuditorProvider() {
        // Se obtiene el nombre del usuario de forma reactiva y se provee "anonymous" en caso de ausencia.
        return () -> ReactiveSecurityUtil.getCurrentUsername()
                .map(optional -> optional.orElse("anonymous"));
    }
}
