package pe.reto.retocurrencyexchange.adapter.out.persistence.repository;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import pe.reto.retocurrencyexchange.adapter.out.persistence.entity.ExchangeRateEntity;
import reactor.core.publisher.Mono;

public interface ExchangeRateRepository extends ReactiveCrudRepository<ExchangeRateEntity, Long> {
    Mono<ExchangeRateEntity> findBySourceCurrencyAndTargetCurrency(String sourceCurrency, String targetCurrency);
}
