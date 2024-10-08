// src/main/java/com/InvestmentsTracker/investment_portfolio/model/Crypto.java

package com.InvestmentsTracker.investment_portfolio.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@Entity
@Table(name = "cryptos") // Nome da tabela no banco de dados
public class Crypto extends Investment {

    @Column(name = "ticker", nullable = false, unique = true)
    @NotBlank(message = "Ticker não pode ser vazio.")
    private String ticker;

    @Column(name = "units", nullable = false)
    @Positive(message = "Units deve ser positivo.")
    private double units;

    @Column(name = "last_synced_price")
    private double lastSyncedPrice;

    // Outros campos específicos de Crypto, se necessário

    @Override
    public double getCurrentMarketPrice() {
        // Implementação específica para obter o preço de mercado
        // Pode ser uma chamada a um serviço externo ou lógica específica
        return lastSyncedPrice;
    }

    @Override
    public String getInvestmentType() {
        return "Crypto";
    }

    @Override
    public String toString() {
        return "Crypto{" +
                "id=" + getId() +
                ", ticker='" + ticker + '\'' +
                ", units=" + units +
                ", buyPrice=" + getBuyPrice() +
                ", date=" + getDate() +
                ", riskLevel=" + getRiskLevel() +
                ", lastSyncedPrice=" + lastSyncedPrice +
                ", currentValue=" + getCurrentValue() +
                '}';
    }
}
