package pe.reto.retocurrencyexchange.adapter.out.persistence;

import org.springframework.stereotype.Component;
import pe.reto.retocurrencyexchange.adapter.dto.response.ExchangeInfoDto;
import pe.reto.retocurrencyexchange.adapter.dto.response.ExchangeResponseDto;
import pe.reto.retocurrencyexchange.adapter.out.persistence.entity.ExchangeRateEntity;
import pe.reto.retocurrencyexchange.adapter.out.persistence.repository.ExchangeRateRepository;
import pe.reto.retocurrencyexchange.domain.model.ExchangeRate;
import pe.reto.retocurrencyexchange.domain.port.out.ExchangeRateRepositoryPort;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Component
public class ExchangeRateRepositoryAdapter implements ExchangeRateRepositoryPort {

    private final ExchangeRateRepository repository;

    public ExchangeRateRepositoryAdapter(ExchangeRateRepository repository) {
        this.repository = repository;
    }

    private ExchangeRate mapToDomain(ExchangeRateEntity entity) {
        return ExchangeRate.builder()
                .id(entity.getId())
                .sourceCurrency(entity.getSourceCurrency())
                .targetCurrency(entity.getTargetCurrency())
                .rate(entity.getRate())
                .createdUser(entity.getCreatedUser())
                .createdDate(entity.getCreatedDate())
                .updatedUser(entity.getUpdatedUser())
                .updatedDate(entity.getUpdatedDate())
                .build();
    }

    private ExchangeRateEntity mapToEntity(ExchangeRate rate) {
        return ExchangeRateEntity.builder()
                .id(rate.getId())
                .sourceCurrency(rate.getSourceCurrency())
                .targetCurrency(rate.getTargetCurrency())
                .rate(rate.getRate())
                .createdUser(rate.getCreatedUser())
                .createdDate(rate.getCreatedDate())
                .build();
    }

    private ExchangeResponseDto mapToExchangeResponse(ExchangeRateEntity entity) {
        return ExchangeResponseDto.builder()
                .id(entity.getId())
                .sourceCurrency(entity.getSourceCurrency())
                .targetCurrency(entity.getTargetCurrency())
                .rate(entity.getRate())
                .build();
    }

    private ExchangeInfoDto mapToExchangeInfoResponse(ExchangeRateEntity entity) {
        return ExchangeInfoDto.builder()
                .id(entity.getId())
                .sourceCurrency(entity.getSourceCurrency())
                .targetCurrency(entity.getTargetCurrency())
                .rate(entity.getRate())
                .createdUser(entity.getCreatedUser())
                .createdDate(entity.getCreatedDate())
                .updatedUser(entity.getUpdatedUser())
                .updatedDate(entity.getUpdatedDate())
                .build();
    }

    @Override
    public Mono<ExchangeRate> findBySourceCurrencyAndTargetCurrency(String sourceCurrency, String targetCurrency) {
        return repository.findBySourceCurrencyAndTargetCurrency(sourceCurrency, targetCurrency)
                .map(this::mapToDomain);
    }

    @Override
    public Mono<ExchangeResponseDto> save(ExchangeRate exchangeRate) {
        return repository.save(mapToEntity(exchangeRate))
                .map(this::mapToExchangeResponse);
    }

    @Override
    public Mono<ExchangeRate> findById(Long id) {
        return repository.findById(id).map(this::mapToDomain);
    }

    @Override
    public Flux<ExchangeInfoDto> findAll() {
        return repository.findAll().map(this::mapToExchangeInfoResponse);
    }
}
