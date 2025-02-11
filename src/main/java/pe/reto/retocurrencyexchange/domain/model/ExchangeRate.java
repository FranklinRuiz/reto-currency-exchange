package pe.reto.retocurrencyexchange.domain.model;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Builder
public class ExchangeRate {
    private Long id;
    private String sourceCurrency;
    private String targetCurrency;
    private BigDecimal rate;
    private String createdUser;
    private LocalDateTime createdDate;
    private String updatedUser;
    private LocalDateTime updatedDate;
}
