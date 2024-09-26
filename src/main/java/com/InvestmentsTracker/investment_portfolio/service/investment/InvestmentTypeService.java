package com.InvestmentsTracker.investment_portfolio.service.investment;

public interface InvestmentTypeService {
    /**
     * Atualiza o preço específico do investimento.
     *
     * @param investmentId ID do investimento
     * @return Preço atualizado
     */
    double updatePrice(Long investmentId);
}
