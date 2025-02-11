package pe.reto.retocurrencyexchange.domain.port.in;

import pe.reto.retocurrencyexchange.adapter.dto.request.ExchangeRequest;
import pe.reto.retocurrencyexchange.adapter.dto.request.ExchangeRequestDto;
import pe.reto.retocurrencyexchange.adapter.dto.response.ExchangeResponse;
import pe.reto.retocurrencyexchange.adapter.dto.response.ExchangeInfoDto;
import pe.reto.retocurrencyexchange.adapter.dto.response.ExchangeResponseDto;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface ExchangeUseCase {
    Mono<ExchangeResponse> performExchange(ExchangeRequest request, String username);

    Mono<ExchangeResponseDto> createRate(ExchangeRequestDto dto);

    Mono<ExchangeResponseDto> updateRate(Long id, ExchangeRequestDto dto);

    Flux<ExchangeInfoDto> getRates();

    Mono<ExchangeResponseDto> getRate(Long id);
}
