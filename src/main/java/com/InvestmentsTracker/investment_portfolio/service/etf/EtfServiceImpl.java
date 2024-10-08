package com.InvestmentsTracker.investment_portfolio.service.etf;

import com.InvestmentsTracker.investment_portfolio.exception.EtfPriceRetrievalException;
import com.InvestmentsTracker.investment_portfolio.model.Etf;
import com.InvestmentsTracker.investment_portfolio.repository.EtfRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

/**
 * Implementação do serviço de gerenciamento de Etfs.
 */
@Service
@Slf4j
public class EtfServiceImpl implements EtfService {

    private final EtfRepository etfRepository;
    private final EtfPriceService etfPriceService; // Dependência para obter preços de mercado

    @Autowired
    public EtfServiceImpl(EtfRepository etfRepository, EtfPriceService etfPriceService) {
        this.etfRepository = etfRepository;
        this.etfPriceService = etfPriceService;
    }

    /**
     * Atualiza o valor atual de um Etf específico.
     *
     * @param etf      Instância de Etf a ser atualizada.
     * @param newValue Novo valor atual em EUR.
     */
    @Override
    public void updateEtfValue(Etf etf, double newValue) throws EtfPriceRetrievalException {
        if (newValue < 0) {
            log.error("Novo valor para Etf '{}' é inválido: {}", etf.getTicker(), newValue);
            throw new EtfPriceRetrievalException("O novo valor não pode ser negativo.");
        }

        try {
            etf.setCurrentValue(newValue);
            etfRepository.save(etf);
            log.info("Valor da Etf '{}' (ID: {}) atualizado para: {} EUR", etf.getTicker(), etf.getId(), newValue);
        } catch (Exception e) {
            log.error("Erro ao atualizar Etf '{}' (ID: {}): {}", etf.getTicker(), etf.getId(), e.getMessage());
            throw new EtfPriceRetrievalException("Erro ao atualizar o valor do Etf: " + etf.getTicker(), e);
        }
    }

    /**
     * Adiciona uma nova Etf.
     *
     * @param etf Instância de Etf a ser adicionada.
     * @return Etf criada.
     */
    @Override
    public Etf addEtf(Etf etf) {
        Etf savedEtf = etfRepository.save(etf);
        log.info("Nova Etf adicionada: '{}', ID: {}", savedEtf.getTicker(), savedEtf.getId());
        return savedEtf;
    }

    /**
     * Atualiza uma Etf existente.
     *
     * @param etf Instância de Etf a ser atualizada.
     * @return Etf atualizada.
     */
    @Override
    public Etf updateEtf(Etf etf) {
        if (etf.getId() == null || !etfRepository.existsById(etf.getId())) {
            log.warn("Tentativa de atualizar Etf inexistente: ID {}", etf.getId());
            throw new IllegalArgumentException("Etf não encontrada com ID: " + etf.getId());
        }
        Etf updatedEtf = etfRepository.save(etf);
        log.info("Etf atualizada: '{}', ID: {}", updatedEtf.getTicker(), updatedEtf.getId());
        return updatedEtf;
    }

    /**
     * Remove uma Etf específica.
     *
     * @param etfId ID da Etf a ser removida.
     * @throws EtfPriceRetrievalException Se ocorrer um erro ao remover a Etf.
     */
    @Override
    public void removeEtf(UUID etfId) throws EtfPriceRetrievalException {
        Etf etf = etfRepository.findById(etfId)
                .orElseThrow(() -> new EtfPriceRetrievalException("Etf não encontrada com ID: " + etfId));

        etfRepository.delete(etf);
        log.info("Etf removida: '{}', ID: {}", etf.getTicker(), etfId);
    }

    /**
     * Recupera uma Etf por ID.
     *
     * @param etfId ID da Etf.
     * @return Etf encontrada.
     */
    @Override
    public Etf getEtfById(UUID etfId) {
        Etf etf = etfRepository.findById(etfId)
                .orElseThrow(() -> new IllegalArgumentException("Etf não encontrada com ID: " + etfId));
        log.info("Etf recuperada: '{}', ID: {}", etf.getTicker(), etfId);
        return etf;
    }

    /**
     * Recupera todas as Etfs associadas a um usuário específico.
     *
     * @param userId ID do usuário.
     * @return Lista de Etfs.
     */
    @Override
    public List<Etf> getAllEtfsByUser(UUID userId) {
        List<Etf> etfs = etfRepository.findByPortfolioUserId(userId);
        log.info("Encontradas {} Etfs para o usuário ID: {}", etfs.size(), userId);
        return etfs;
    }
}
