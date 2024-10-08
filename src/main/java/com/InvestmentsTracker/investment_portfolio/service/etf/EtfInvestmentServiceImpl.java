package com.InvestmentsTracker.investment_portfolio.service.etf;

import com.InvestmentsTracker.investment_portfolio.dto.etf.EtfRequestDTO;
import com.InvestmentsTracker.investment_portfolio.exception.EtfPriceRetrievalException;
import com.InvestmentsTracker.investment_portfolio.exception.InvestmentException;
import com.InvestmentsTracker.investment_portfolio.mapper.EtfMapper;
import com.InvestmentsTracker.investment_portfolio.model.Etf;
import com.InvestmentsTracker.investment_portfolio.repository.EtfRepository;
import com.InvestmentsTracker.investment_portfolio.service.portfolio.PortfolioService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

/**
 * Implementação do serviço específico para investimentos em ETFs.
 */
@Service
@Slf4j
public class EtfInvestmentServiceImpl implements EtfInvestmentService {

    private final EtfService etfService;
    private final EtfRepository etfRepository;
    private final PortfolioService portfolioService; // Serviço para gerenciar portfólios

    @Autowired
    public EtfInvestmentServiceImpl(EtfService etfService, EtfRepository etfRepository, PortfolioService portfolioService) {
        this.etfService = etfService;
        this.etfRepository = etfRepository;
        this.portfolioService = portfolioService;
    }

    /**
     * Atualiza o preço de um ETF específico no investimento.
     *
     * @param investmentId ID do investimento em ETF.
     * @return Preço atualizado em EUR.
     * @throws InvestmentException Se ocorrer um erro ao recuperar o preço.
     */
    @Override
    public double updatePrice(UUID investmentId) throws InvestmentException {
        // Tentar encontrar o ETF pelo ID
        Etf etf = etfRepository.findById(investmentId)
                .orElseThrow(() -> new EtfPriceRetrievalException("ETF não encontrada com ID: " + investmentId));

        String ticker = etf.getTicker();
        if (ticker == null || ticker.trim().isEmpty()) {
            log.error("Ticker está vazio para investimento com ID: {}", investmentId);
            throw new InvestmentException("Ticker não pode ser vazio.");
        }

        try {
            // Obter o preço atual do ETF utilizando o serviço externo
            double currentPrice = etfService.getEtfPriceInEUR(ticker);
            etf.setLastSyncedPrice(currentPrice);
            etfRepository.save(etf);

            log.info("Preço atualizado para ETF '{}' (ID: {}): {} EUR", ticker, investmentId, currentPrice);
            return currentPrice;
        } catch (Exception e) {
            log.error("Erro ao atualizar o preço para ETF '{}' (ID: {}): {}", ticker, investmentId, e.getMessage());
            throw new InvestmentException("Erro ao atualizar o preço do ETF: " + ticker, e);
        }
    }

    /**
     * Calcula o valor atual de um ETF no investimento.
     *
     * @param investmentId ID do investimento em ETF.
     * @return Valor atual em EUR.
     * @throws InvestmentException Se ocorrer um erro ao recuperar o preço.
     */
    @Override
    public double getCurrentValue(UUID investmentId) throws InvestmentException {
        Etf etf = etfRepository.findById(investmentId)
                .orElseThrow(() -> new InvestmentException("ETF não encontrada com ID: " + investmentId));

        double currentPrice = etf.getLastSyncedPrice();
        double currentValue = etf.getUnits() * currentPrice;
        etf.setCurrentValue(currentValue);
        etfRepository.save(etf);

        log.info("Valor atual para ETF '{}' (ID: {}): {} EUR", etf.getTicker(), investmentId, currentValue);
        return currentValue;
    }

    /**
     * Recupera todas as Etfs associadas a um usuário.
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

    /**
     * Adiciona uma nova Etf ao portfólio.
     *
     * @param etfRequestDTO DTO contendo os dados da Etf.
     * @return Etf adicionada.
     * @throws EtfPriceRetrievalException Se ocorrer um erro ao adicionar a Etf.
     */
    @Override
    public Etf addEtf(EtfRequestDTO etfRequestDTO) throws EtfPriceRetrievalException {
        // Verifica se o portfólio existe
        UUID portfolioId = etfRequestDTO.getPortfolioId();
        if (!portfolioService.existsById(portfolioId)) {
            log.error("Portfólio não encontrado com ID: {}", portfolioId);
            throw new EtfPriceRetrievalException("Portfólio não encontrado com ID: " + portfolioId);
        }

        // Mapeia DTO para entidade
        Etf etf = EtfMapper.toEntity(etfRequestDTO);
        etf.setPortfolio(portfolioService.getPortfolioById(portfolioId));

        // Inicializa o valor atual
        etf.setCurrentValue(0.0);
        etf.setLastSyncedPrice(0.0);

        // Salva o ETF
        Etf savedEtf = etfRepository.save(etf);
        log.info("Nova Etf adicionada: '{}', ID: {}", savedEtf.getTicker(), savedEtf.getId());

        return savedEtf;
    }

    /**
     * Remove uma Etf específica do portfólio.
     *
     * @param etfId ID da Etf a ser removida.
     * @throws EtfPriceRetrievalException Se ocorrer um erro ao remover a Etf.
     */
    @Override
    public void removeEtf(UUID etfId) throws EtfPriceRetrievalException {
        Etf etf = etfRepository.findById(etfId)
                .orElseThrow(() -> new EtfPriceRetrievalException("Etf não encontrada com ID: " + etfId));

        try {
            etfRepository.delete(etf);
            log.info("Etf removida: '{}', ID: {}", etf.getTicker(), etfId);
        } catch (Exception e) {
            log.error("Erro ao remover Etf '{}' (ID: {}): {}", etf.getTicker(), etfId, e.getMessage());
            throw new EtfPriceRetrievalException("Erro ao remover a Etf: " + etf.getTicker(), e);
        }
    }

    /**
     * Atualiza os preços de todas as Etfs no portfólio do usuário.
     *
     * @param portfolioId ID do portfólio do usuário.
     * @throws EtfPriceRetrievalException Se ocorrer um erro ao recuperar o preço.
     */
    @Override
    public void updateAllPrices(UUID portfolioId) throws EtfPriceRetrievalException {
        List<Etf> etfs = etfRepository.findByPortfolioId(portfolioId);
        for (Etf etf : etfs) {
            try {
                updatePrice(etf.getId());
            } catch (EtfPriceRetrievalException e) {
                log.error("Erro ao atualizar preço para Etf ID: {}", etf.getId(), e);
                // Dependendo da necessidade, pode optar por continuar ou interromper a atualização
                throw e;
            }
        }
        log.info("Todos os preços das Etfs no portfólio ID {} foram atualizados.", portfolioId);
    }
}
