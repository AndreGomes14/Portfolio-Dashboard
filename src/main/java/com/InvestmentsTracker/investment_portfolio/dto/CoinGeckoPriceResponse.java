// src/main/java/com/InvestmentsTracker/investment_portfolio/dto/CoinGeckoPriceResponse.java
package com.InvestmentsTracker.investment_portfolio.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * Classe DTO para mapear a resposta da CoinGecko API.
 */
@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class CoinGeckoPriceResponse {
    @JsonProperty("eur")
    private Double eur;
}
