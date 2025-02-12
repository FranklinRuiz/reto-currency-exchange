package pe.reto.retocurrencyexchange.domain.port.out;

import pe.reto.retocurrencyexchange.domain.model.ExchangeTransaction;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface ExchangeTransactionRepositoryPort {
    Mono<ExchangeTransaction> save(ExchangeTransaction transaction);

    Flux<ExchangeTransaction> findAll();
}
