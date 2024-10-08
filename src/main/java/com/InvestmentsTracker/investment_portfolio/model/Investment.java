package com.InvestmentsTracker.investment_portfolio.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.UUID;

@Getter
@Setter
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Table(name = "investments")
public abstract class Investment {

    @Id
    @GeneratedValue // Utiliza a estratégia padrão para UUID
    @Column(name = "id", updatable = false, nullable = false, columnDefinition = "CHAR(36)")
    private UUID id;

    @Column(name = "buy_price", nullable = false)
    private double buyPrice;

    @Column(name = "units", nullable = false)
    private int units;

    @Column(name = "date", nullable = false)
    @Temporal(TemporalType.DATE)
    private Date date;

    @Column(name = "risk_level", nullable = false)
    private int riskLevel;

    @Column(name = "last_synced_price")
    private double lastSyncedPrice;

    @Transient
    private double amountInvested;

    @Transient
    private double currentValue;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "portfolio_id") // Chave estrangeira para a tabela Portfolio
    private Portfolio portfolio;

    /**
     * Calcula o valor total investido.
     *
     * @return Valor total investido.
     */
    public double calculateTotalValue() {
        return buyPrice * units;
    }

    /**
     * Obtém o valor investido.
     *
     * @return Valor investido.
     */
    public double getAmountInvested() {
        return buyPrice * units;
    }

    /**
     * Obtém o valor atual do investimento no mercado.
     *
     * @return Valor atual no mercado.
     */
    public double getCurrentValue() {
        return getCurrentMarketPrice();
    }

    /**
     * Método abstrato para obter o preço atual de mercado.
     *
     * @return Preço atual de mercado.
     */
    public abstract double getCurrentMarketPrice();

    /**
     * Método abstrato para obter o tipo de investimento.
     *
     * @return Tipo de investimento.
     */
    @Transient
    public abstract String getInvestmentType();

    @Override
    public String toString() {
        return "Investment{" +
                "id=" + id +
                ", buyPrice=" + buyPrice +
                ", date=" + date +
                ", riskLevel=" + riskLevel +
                ", currentValue=" + currentValue +
                ", portfolio=" + portfolio +
                '}';
    }
}
