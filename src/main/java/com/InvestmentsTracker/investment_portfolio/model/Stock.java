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
@Table(name = "stocks")
public class Stock extends Investment {

    @Column(name = "ticker", nullable = false, unique = true)
    @NotBlank(message = "Ticker não pode ser vazio.")
    private String ticker;

    @Column(name = "units", nullable = false)
    @Positive(message = "Units deve ser positivo.")
    private double units;

    @Column(name = "last_synced_price")
    private double lastSyncedPrice;

    @Override
    public double getCurrentMarketPrice() {
        return lastSyncedPrice;
    }

    @Override
    public String getInvestmentType() {
        return "Stock";
    }

    @Override
    public String toString() {
        return "Stock{" +
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
