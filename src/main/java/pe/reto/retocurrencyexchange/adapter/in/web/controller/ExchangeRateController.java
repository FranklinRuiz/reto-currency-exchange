package pe.reto.retocurrencyexchange.adapter.in.web.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import pe.reto.retocurrencyexchange.adapter.dto.request.ExchangeRequestDto;
import pe.reto.retocurrencyexchange.adapter.dto.response.ExchangeInfoDto;
import pe.reto.retocurrencyexchange.adapter.dto.response.ExchangeResponseDto;
import pe.reto.retocurrencyexchange.domain.port.in.ExchangeUseCase;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/rates")
@RequiredArgsConstructor
public class ExchangeRateController {

    private final ExchangeUseCase exchangeUseCase;

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<ExchangeResponseDto> createRate(@RequestBody ExchangeRequestDto requestDto) {
        return exchangeUseCase.createRate(requestDto);
    }

    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<ExchangeResponseDto> updateRate(@PathVariable Long id, @RequestBody ExchangeRequestDto requestDto) {
        return exchangeUseCase.updateRate(id, requestDto);
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public Flux<ExchangeInfoDto> getRates() {
        return exchangeUseCase.getRates();
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<ExchangeResponseDto> getRate(@PathVariable Long id) {
        return exchangeUseCase.getRate(id);
    }
}
