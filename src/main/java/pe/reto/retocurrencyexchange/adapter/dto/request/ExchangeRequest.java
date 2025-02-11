package pe.reto.retocurrencyexchange.adapter.dto.request;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class ExchangeRequest {
    private BigDecimal amount;
    private String sourceCurrency;
    private String targetCurrency;
}
