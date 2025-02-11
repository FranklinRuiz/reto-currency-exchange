package pe.reto.retocurrencyexchange.adapter.dto.response;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
public class ExchangeResponseDto {
    private Long id;
    private String sourceCurrency;
    private String targetCurrency;
    private BigDecimal rate;
}
