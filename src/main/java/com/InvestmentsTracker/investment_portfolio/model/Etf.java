package com.InvestmentsTracker.investment_portfolio.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@Entity
@Table(name = "etfs") // Evite conflitos com palavras reservadas
public class Etf extends Investment {

    @Column(name = "ticker", nullable = false, unique = true)
    @NotNull(message = "Ticker não pode ser nulo.")
    @Size(min = 1, max = 10, message = "Ticker deve ter entre 1 e 10 caracteres.")
    private String ticker;


    @Column(name = "units", nullable = false)
    private double units;

    @Column(name = "last_synced_price")
    private double lastSyncedPrice;

    @Column(name = "current_value")
    private double currentValue;

    @Override

    public double getCurrentMarketPrice() {

        return lastSyncedPrice; //implementar depois
    }

    @Override
    public String getInvestmentType() {
        return "Etf";
    }

    @Override
    public String toString() {
        return "Etf{" +
                "id=" + getId() +
                ", ticker='" + ticker + '\'' +
                ", units=" + units +
                ", buyPrice=" + getBuyPrice() +
                ", date=" + getDate() +
                ", riskLevel=" + getRiskLevel() +
                ", lastSyncedPrice=" + lastSyncedPrice +
                '}';
    }
}
