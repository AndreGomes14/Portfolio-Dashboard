package com.InvestmentsTracker.investment_portfolio.service.etf;

import com.InvestmentsTracker.investment_portfolio.exception.EtfPriceRetrievalException;
import com.InvestmentsTracker.investment_portfolio.exception.InvestmentException;
import com.InvestmentsTracker.investment_portfolio.model.Etf;

import java.util.List;
import java.util.UUID;

/**
 * Interface para serviços de gerenciamento de Etfs.
 */
public interface EtfService {

    /**
     * Atualiza o valor atual de um Etf específico.
     *
     * @param etf      Instância de Etf a ser atualizada.
     * @param newValue Novo valor atual em EUR.
     */
    void updateEtfValue(Etf etf, double newValue) throws EtfPriceRetrievalException;

    /**
     * Adiciona uma nova Etf.
     *
     * @param etf Instância de Etf a ser adicionada.
     * @return Etf criada.
     */
    Etf addEtf(Etf etf);

    /**
     * Atualiza uma Etf existente.
     *
     * @param etf Instância de Etf a ser atualizada.
     * @return Etf atualizada.
     */
    Etf updateEtf(Etf etf);

    /**
     * Remove uma Etf específica.
     *
     * @param etfId ID da Etf a ser removida.
     * @throws EtfPriceRetrievalException Se ocorrer um erro ao remover a Etf.
     */
    void removeEtf(UUID etfId) throws EtfPriceRetrievalException;

    /**
     * Recupera uma Etf por ID.
     *
     * @param etfId ID da Etf.
     * @return Etf encontrada.
     */
    Etf getEtfById(UUID etfId);

    /**
     * Recupera todas as Etfs associadas a um usuário específico.
     *
     * @param userId ID do usuário.
     * @return Lista de Etfs.
     */
    List<Etf> getAllEtfsByUser(UUID userId);

    double getEtfPriceInEUR(String ticker) throws InvestmentException;

}
