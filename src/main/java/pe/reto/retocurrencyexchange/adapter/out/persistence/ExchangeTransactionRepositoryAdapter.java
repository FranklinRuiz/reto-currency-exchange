package pe.reto.retocurrencyexchange.adapter.out.persistence;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import pe.reto.retocurrencyexchange.adapter.out.persistence.entity.ExchangeTransactionEntity;
import pe.reto.retocurrencyexchange.adapter.out.persistence.repository.ExchangeTransactionRepository;
import pe.reto.retocurrencyexchange.domain.model.ExchangeTransaction;
import pe.reto.retocurrencyexchange.domain.port.out.ExchangeTransactionRepositoryPort;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class ExchangeTransactionRepositoryAdapter implements ExchangeTransactionRepositoryPort {

    private final ExchangeTransactionRepository repository;

    private ExchangeTransaction mapToDomain(ExchangeTransactionEntity entity) {
        return ExchangeTransaction.builder()
                .id(entity.getId())
                .sourceCurrency(entity.getSourceCurrency())
                .targetCurrency(entity.getTargetCurrency())
                .originalAmount(entity.getOriginalAmount())
                .convertedAmount(entity.getConvertedAmount())
                .rate(entity.getRate())
                .createdUser(entity.getCreatedUser())
                .createdDate(entity.getUpdatedDate())
                .updatedUser(entity.getUpdatedUser())
                .updatedDate(entity.getUpdatedDate())
                .build();
    }

    private ExchangeTransactionEntity mapToEntity(ExchangeTransaction transaction) {
        return ExchangeTransactionEntity.builder()
                .id(transaction.getId())
                .sourceCurrency(transaction.getSourceCurrency())
                .targetCurrency(transaction.getTargetCurrency())
                .originalAmount(transaction.getOriginalAmount())
                .convertedAmount(transaction.getConvertedAmount())
                .rate(transaction.getRate())
                .build();
    }

    @Override
    public Mono<ExchangeTransaction> save(ExchangeTransaction transaction) {
        return repository.save(mapToEntity(transaction))
                .map(this::mapToDomain);
    }
}
