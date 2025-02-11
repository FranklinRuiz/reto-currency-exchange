package pe.reto.retocurrencyexchange.adapter.out.persistence.repository;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import pe.reto.retocurrencyexchange.adapter.out.persistence.entity.ExchangeTransactionEntity;

public interface ExchangeTransactionRepository extends ReactiveCrudRepository<ExchangeTransactionEntity, Long> {
}
