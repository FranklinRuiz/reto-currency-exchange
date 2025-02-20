package pe.reto.retocurrencyexchange.adapter.in.web.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import pe.reto.retocurrencyexchange.adapter.dto.request.ExchangeRequest;
import pe.reto.retocurrencyexchange.adapter.dto.response.ExchangeResponse;
import pe.reto.retocurrencyexchange.domain.model.ExchangeTransaction;
import pe.reto.retocurrencyexchange.domain.port.in.ExchangeUseCase;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/exchange")
@RequiredArgsConstructor
public class ExchangeController {

    private final ExchangeUseCase exchangeUseCase;

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<ExchangeResponse> exchange(@RequestBody ExchangeRequest request, @AuthenticationPrincipal UserDetails user) {
        return exchangeUseCase.performExchange(request, user.getUsername());
    }

    @GetMapping(value = "/all", produces = MediaType.APPLICATION_JSON_VALUE)
    public Flux<ExchangeTransaction> getExchangeTransaction() {
        return exchangeUseCase.getExchangeTransactions();
    }
}
