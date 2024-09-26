package com.InvestmentsTracker.investment_portfolio.service.etf;

import com.InvestmentsTracker.investment_portfolio.exception.EtfPriceRetrievalException;
import com.InvestmentsTracker.investment_portfolio.model.Etf;
import com.InvestmentsTracker.investment_portfolio.repository.EtfRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Implementação do serviço específico para investimentos em ETFs.
 */
@Service
@Slf4j
public class EtfInvestmentServiceImpl implements EtfInvestmentService {

    private final EtfService etfService;
    private final EtfRepository etfRepository;

    @Autowired
    public EtfInvestmentServiceImpl(EtfService etfService, EtfRepository etfRepository) {
        this.etfService = etfService;
        this.etfRepository = etfRepository;
    }

    /**
     * Atualiza manualmente o valor de um investimento em ETF específico.
     *
     * @param investmentId ID do investimento em ETF.
     * @param newValue Novo valor atual em EUR.
     * @throws EtfPriceRetrievalException Se ocorrer um erro ao atualizar o valor.
     */
    @Override
    public void updateValue(Long investmentId, double newValue) throws EtfPriceRetrievalException {
        Etf etf = etfRepository.findById(investmentId)
                .orElseThrow(() -> new EtfPriceRetrievalException("ETF não encontrado com ID: " + investmentId));

        etfService.updateEtfValue(etf, newValue);
        etfRepository.save(etf);

        log.info("ETF '{}' (ID: {}) atualizado com novo valor: {} EUR", etf.getDescription(), investmentId, newValue);
    }

    /**
     * Atualiza os valores de todos os investimentos em ETFs no portfólio do usuário.
     *
     * @param portfolioId ID do portfólio do usuário.
     * @param newValue Novo valor atual a ser definido para cada ETF.
     * @throws EtfPriceRetrievalException Se ocorrer um erro ao atualizar os valores.
     */
    @Override
    public void updateAllValues(Long portfolioId, double newValue) throws EtfPriceRetrievalException {
        List<Etf> etfList = etfRepository.findByPortfolioId(portfolioId);
        for (Etf etf : etfList) {
            try {
                updateValue(etf.getId(), newValue);
            } catch (EtfPriceRetrievalException e) {
                log.error("Erro ao atualizar ETF ID {}: {}", etf.getId(), e.getMessage(), e);
                // Dependendo da necessidade, pode-se optar por continuar ou interromper
                throw e;
            }
        }
        log.info("Todos os ETFs no portfólio ID {} foram atualizados com o valor: {} EUR", portfolioId, newValue);
    }

    /**
     * Recupera o valor atual de um investimento em ETF.
     *
     * @param investmentId ID do investimento em ETF.
     * @return Valor atual em EUR.
     * @throws EtfPriceRetrievalException Se ocorrer um erro ao recuperar o valor.
     */
    @Override
    public double getCurrentValue(Long investmentId) throws EtfPriceRetrievalException {
        Etf etf = etfRepository.findById(investmentId)
                .orElseThrow(() -> new EtfPriceRetrievalException("ETF não encontrado com ID: " + investmentId));

        double currentValue = etf.getCurrentValue();
        log.info("Valor atual para ETF '{}' (ID: {}): {} EUR", etf.getDescription(), investmentId, currentValue);
        return currentValue;
    }

    /**
     * Recupera todas as instâncias de ETFs associadas a um usuário específico.
     *
     * @param userId ID do usuário.
     * @return Lista de investimentos em ETFs.
     */
    @Override
    public List<Etf> getAllEtfsByUser(Long userId) {
        List<Etf> etfList = etfRepository.findByPortfolioUserId(userId);
        log.info("Encontradas {} ETFs para o usuário ID: {}", etfList.size(), userId);
        return etfList;
    }

    @Override
    public Etf addEtf(Etf etf) {
        Etf savedEtf = etfRepository.save(etf);
        log.info("Novo ETF adicionado: '{}', ID: {}", savedEtf.getDescription(), savedEtf.getId());
        return savedEtf;
    }

    /**
     * Remove um investimento em ETF específico.
     *
     * @param investmentId ID do investimento em ETF.
     * @throws EtfPriceRetrievalException Se ocorrer um erro ao remover o ETF.
     */
    @Override
    public void removeEtf(Long investmentId) throws EtfPriceRetrievalException {
        Etf etf = etfRepository.findById(investmentId)
                .orElseThrow(() -> new EtfPriceRetrievalException("ETF não encontrado com ID: " + investmentId));

        etfRepository.delete(etf);
        log.info("ETF removido: '{}', ID: {}", etf.getDescription(), investmentId);
    }
}
