package pe.reto.retocurrencyexchange.domain.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Map;

@Data
public class ExchangeApiResponse {

    @JsonProperty("result")
    private String result;

    @JsonProperty("documentation")
    private String documentation;

    @JsonProperty("terms_of_use")
    private String termsOfUse;

    @JsonProperty("time_last_update_unix")
    private long timeLastUpdateUnix;

    @JsonProperty("time_last_update_utc")
    private String timeLastUpdateUtc;

    @JsonProperty("time_next_update_unix")
    private long timeNextUpdateUnix;

    @JsonProperty("time_next_update_utc")
    private String timeNextUpdateUtc;

    @JsonProperty("base_code")
    private String baseCode;

    @JsonProperty("conversion_rates")
    private Map<String, BigDecimal> conversionRates;
}
