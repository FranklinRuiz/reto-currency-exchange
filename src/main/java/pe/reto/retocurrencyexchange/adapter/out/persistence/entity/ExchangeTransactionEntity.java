package pe.reto.retocurrencyexchange.adapter.out.persistence.entity;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;
import pe.reto.retocurrencyexchange.domain.model.audit.AuditEntity;

import java.math.BigDecimal;

@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@Table("EXCHANGE_TRANSACTION")
public class ExchangeTransactionEntity extends AuditEntity {
    @Id
    private Long id;

    private String sourceCurrency;
    private String targetCurrency;
    private BigDecimal originalAmount;
    private BigDecimal convertedAmount;
    private BigDecimal rate;
}
