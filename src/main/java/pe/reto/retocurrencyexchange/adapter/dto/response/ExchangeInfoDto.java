package pe.reto.retocurrencyexchange.adapter.dto.response;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Builder
public class ExchangeInfoDto {
    private Long id;
    private String sourceCurrency;
    private String targetCurrency;
    private BigDecimal rate;
    private String createdUser;
    private LocalDateTime createdDate;
    private String updatedUser;
    private LocalDateTime updatedDate;
}