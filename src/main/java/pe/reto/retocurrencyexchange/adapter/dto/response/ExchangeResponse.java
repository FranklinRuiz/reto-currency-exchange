package pe.reto.retocurrencyexchange.adapter.dto.response;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
public class ExchangeResponse {
    private BigDecimal originalAmount;
    private BigDecimal convertedAmount;
    private BigDecimal rate;
    private String sourceCurrency;
    private String targetCurrency;
}
