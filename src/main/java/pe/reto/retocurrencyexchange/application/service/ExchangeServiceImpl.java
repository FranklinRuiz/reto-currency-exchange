package pe.reto.retocurrencyexchange.application.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import pe.reto.retocurrencyexchange.adapter.dto.request.ExchangeRequest;
import pe.reto.retocurrencyexchange.adapter.dto.request.ExchangeRequestDto;
import pe.reto.retocurrencyexchange.adapter.dto.response.ExchangeResponse;
import pe.reto.retocurrencyexchange.adapter.dto.response.ExchangeInfoDto;
import pe.reto.retocurrencyexchange.adapter.dto.response.ExchangeResponseDto;
import pe.reto.retocurrencyexchange.application.exception.ExchangeRateException;
import pe.reto.retocurrencyexchange.domain.model.ExchangeApiResponse;
import pe.reto.retocurrencyexchange.domain.model.ExchangeRate;
import pe.reto.retocurrencyexchange.domain.model.ExchangeTransaction;
import pe.reto.retocurrencyexchange.domain.port.in.ExchangeUseCase;
import pe.reto.retocurrencyexchange.domain.port.out.ExchangeRateRepositoryPort;
import pe.reto.retocurrencyexchange.domain.port.out.ExchangeTransactionRepositoryPort;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;

@Slf4j
@Service
public class ExchangeServiceImpl implements ExchangeUseCase {

    @Value("${exchangerate.api}")
    private String apiExchangeUrl;

    private final WebClient webClient;
    private final ExchangeRateRepositoryPort rateRepository;
    private final ExchangeTransactionRepositoryPort transactionRepository;

    public ExchangeServiceImpl(WebClient webClient, ExchangeRateRepositoryPort rateRepository, ExchangeTransactionRepositoryPort transactionRepository) {
        this.webClient = webClient;
        this.rateRepository = rateRepository;
        this.transactionRepository = transactionRepository;
    }

    @Override
    public Mono<ExchangeResponse> performExchange(ExchangeRequest request, String username) {
        return rateRepository.findBySourceCurrencyAndTargetCurrency(request.getSourceCurrency(), request.getTargetCurrency())
                .map(ExchangeRate::getRate)
                .switchIfEmpty(fetchRateFromApi(request))
                .flatMap(rate -> buildAndSaveTransaction(request, rate));
    }

    @Override
    public Mono<ExchangeResponseDto> createRate(ExchangeRequestDto dto) {
        ExchangeRate rate = ExchangeRate.builder()
                .id(dto.getId())
                .sourceCurrency(dto.getSourceCurrency())
                .targetCurrency(dto.getTargetCurrency())
                .rate(dto.getRate())
                .build();
        return rateRepository.save(rate);
    }

    @Override
    public Mono<ExchangeResponseDto> updateRate(Long id, ExchangeRequestDto dto) {
        return rateRepository.findById(id)
                .flatMap(existing -> {
                    existing.setSourceCurrency(dto.getSourceCurrency());
                    existing.setTargetCurrency(dto.getTargetCurrency());
                    existing.setRate(dto.getRate());
                    return rateRepository.save(existing);
                });
    }

    @Override
    public Flux<ExchangeInfoDto> getRates() {
        return rateRepository.findAll();
    }

    @Override
    public Mono<ExchangeResponseDto> getRate(Long id) {
        return rateRepository.findById(id).map(exchangeRate -> ExchangeResponseDto.builder()
                .id(exchangeRate.getId())
                .sourceCurrency(exchangeRate.getSourceCurrency())
                .targetCurrency(exchangeRate.getTargetCurrency())
                .rate(exchangeRate.getRate())
                .build());
    }

    @Override
    public Flux<ExchangeTransaction> getExchangeTransactions() {
        return transactionRepository.findAll();
    }

    private Mono<ExchangeResponse> buildAndSaveTransaction(ExchangeRequest request, BigDecimal rate) {
        BigDecimal convertedAmount = request.getAmount().multiply(rate);

        ExchangeResponse response = buildExchangeResponse(request, convertedAmount, rate);
        ExchangeTransaction transaction = buildExchangeTransaction(request, convertedAmount, rate);

        return transactionRepository.save(transaction)
                .thenReturn(response);
    }

    private ExchangeResponse buildExchangeResponse(ExchangeRequest request, BigDecimal convertedAmount, BigDecimal rate) {
        return ExchangeResponse.builder()
                .originalAmount(request.getAmount())
                .convertedAmount(convertedAmount)
                .rate(rate)
                .sourceCurrency(request.getSourceCurrency())
                .targetCurrency(request.getTargetCurrency())
                .build();
    }

    private ExchangeTransaction buildExchangeTransaction(ExchangeRequest request, BigDecimal convertedAmount, BigDecimal rate) {
        return ExchangeTransaction.builder()
                .sourceCurrency(request.getSourceCurrency())
                .targetCurrency(request.getTargetCurrency())
                .originalAmount(request.getAmount())
                .convertedAmount(convertedAmount)
                .rate(rate)
                .build();
    }

    private Mono<BigDecimal> fetchRateFromApi(ExchangeRequest request) {
        String apiUrl = apiExchangeUrl + request.getSourceCurrency();

        return webClient.get()
                .uri(apiUrl)
                .retrieve()
                .bodyToMono(ExchangeApiResponse.class)
                .flatMap(apiResponse -> {
                    BigDecimal rate = apiResponse.getConversionRates().get(request.getTargetCurrency());

                    if (rate == null) {
                        return Mono.error(new ExchangeRateException("No se pudo localizar la tasa de conversión para la moneda: " + request.getTargetCurrency()));
                    }

                    return Mono.just(rate);
                });
    }
}
