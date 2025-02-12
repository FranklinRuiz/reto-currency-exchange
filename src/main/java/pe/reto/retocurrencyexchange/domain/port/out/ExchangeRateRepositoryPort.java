package pe.reto.retocurrencyexchange.domain.port.out;

import pe.reto.retocurrencyexchange.adapter.dto.response.ExchangeInfoDto;
import pe.reto.retocurrencyexchange.adapter.dto.response.ExchangeResponseDto;
import pe.reto.retocurrencyexchange.domain.model.ExchangeRate;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface ExchangeRateRepositoryPort {
    Mono<ExchangeRate> findBySourceCurrencyAndTargetCurrency(String sourceCurrency, String targetCurrency);

    Mono<ExchangeResponseDto> save(ExchangeRate exchangeRate);

    Mono<ExchangeRate> findById(Long id);

    Flux<ExchangeInfoDto> findAll();
}
