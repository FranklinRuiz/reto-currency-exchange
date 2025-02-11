package pe.reto.retocurrencyexchange.adapter.dto.request;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class ExchangeRequestDto {
    private Long id;
    private String sourceCurrency;
    private String targetCurrency;
    private BigDecimal rate;
}
