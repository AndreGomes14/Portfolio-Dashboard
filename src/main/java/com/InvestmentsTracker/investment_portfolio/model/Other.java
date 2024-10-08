package com.InvestmentsTracker.investment_portfolio.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@Entity
@Table(name = "others")
public class Other extends Investment {

    @Column(name = "description", nullable = false)
    @NotBlank(message = "Descrição não pode ser vazia.")
    private String description;

    @Column(name = "category", nullable = false)
    @NotBlank(message = "Categoria não pode ser vazia.")
    private String category;

    @Override
    public double getCurrentMarketPrice() {
        // Implementação específica para outros tipos de investimentos
        return getCurrentValue();
    }

    @Override
    public String getInvestmentType() {
        return "Other";
    }

    @Override
    public String toString() {
        return "Other{" +
                "id=" + getId() +
                ", description='" + description + '\'' +
                ", category='" + category + '\'' +
                ", buyPrice=" + getBuyPrice() +
                ", date=" + getDate() +
                ", riskLevel=" + getRiskLevel() +
                ", currentValue=" + getCurrentValue() +
                '}';
    }
}
