package pe.reto.retocurrencyexchange.adapter.out.persistence.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;
import pe.reto.retocurrencyexchange.domain.model.audit.AuditEntity;

import java.math.BigDecimal;

@Data
@SuperBuilder
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Table("EXCHANGE_RATE")
public class ExchangeRateEntity extends AuditEntity {
    @Id
    private Long id;
    private String sourceCurrency;
    private String targetCurrency;
    private BigDecimal rate;
}
