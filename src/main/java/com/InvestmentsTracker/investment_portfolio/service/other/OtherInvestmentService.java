package com.InvestmentsTracker.investment_portfolio.service.other;

import com.InvestmentsTracker.investment_portfolio.exception.OtherPriceRetrievalException;
import com.InvestmentsTracker.investment_portfolio.model.Other;

import java.util.List;

/**
 * Interface para serviços específicos de investimentos em "Other".
 */
public interface OtherInvestmentService {

    /**
     * Atualiza manualmente o valor de um investimento em "Other" específico.
     *
     * @param investmentId ID do investimento em "Other".
     * @param newValue Novo valor atual em EUR.
     * @throws OtherPriceRetrievalException Se ocorrer um erro ao atualizar o valor.
     */
    void updateValue(Long investmentId, double newValue) throws OtherPriceRetrievalException;

    /**
     * Atualiza os valores de todos os investimentos em "Other" no portfólio do usuário.
     *
     * @param portfolioId ID do portfólio do usuário.
     * @param newValue Novo valor atual a ser definido para cada "Other".
     * @throws OtherPriceRetrievalException Se ocorrer um erro ao atualizar os valores.
     */
    void updateAllValues(Long portfolioId, double newValue) throws OtherPriceRetrievalException;

    /**
     * Recupera o valor atual de um investimento em "Other".
     *
     * @param investmentId ID do investimento em "Other".
     * @return Valor atual em EUR.
     * @throws OtherPriceRetrievalException Se ocorrer um erro ao recuperar o valor.
     */
    double getCurrentValue(Long investmentId) throws OtherPriceRetrievalException;

    /**
     * Recupera todas as instâncias de "Other" associadas a um usuário específico.
     *
     * @param userId ID do usuário.
     * @return Lista de investimentos em "Other".
     */
    List<Other> getAllOthersByUser(Long userId);

    /**
     * Adiciona um novo investimento em "Other".
     *
     * @param other Instância de Other a ser adicionada.
     * @return Other criado.
     */
    Other addOther(Other other);

    /**
     * Remove um investimento em "Other" específico.
     *
     * @param investmentId ID do investimento em "Other".
     * @throws OtherPriceRetrievalException Se ocorrer um erro ao remover o "Other".
     */
    void removeOther(Long investmentId) throws OtherPriceRetrievalException;
}
