// src/main/java/com/InvestmentsTracker/investment_portfolio/service/crypto/CryptoInvestmentServiceImpl.java

package com.InvestmentsTracker.investment_portfolio.service.crypto;

import com.InvestmentsTracker.investment_portfolio.exception.CryptoPriceRetrievalException;
import com.InvestmentsTracker.investment_portfolio.mapper.CryptoMapper;
import com.InvestmentsTracker.investment_portfolio.model.Crypto;
import com.InvestmentsTracker.investment_portfolio.repository.CryptoRepository;
import com.InvestmentsTracker.investment_portfolio.service.portfolio.PortfolioService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

/**
 * Implementação do serviço específico para investimentos em criptomoedas.
 */
@Service
@Slf4j
public class CryptoInvestmentServiceImpl implements CryptoInvestmentService {

    private final CryptoService cryptoService;
    private final CryptoRepository cryptoRepository;
    private final PortfolioService portfolioService; // Serviço para gerenciar portfólios

    @Autowired
    public CryptoInvestmentServiceImpl(CryptoService cryptoService, CryptoRepository cryptoRepository, PortfolioService portfolioService) {
        this.cryptoService = cryptoService;
        this.cryptoRepository = cryptoRepository;
        this.portfolioService = portfolioService;
    }

    /**
     * Atualiza o preço de uma criptomoeda específica no investimento.
     *
     * @param investmentId ID do investimento em criptomoeda.
     * @return Preço atualizado em EUR.
     * @throws CryptoPriceRetrievalException Se ocorrer um erro ao recuperar o preço.
     */
    @Override
    public double updatePrice(UUID investmentId) throws CryptoPriceRetrievalException {
        Crypto crypto = cryptoRepository.findById(investmentId)
                .orElseThrow(() -> new CryptoPriceRetrievalException("Criptomoeda não encontrada com ID: " + investmentId));

        String ticker = crypto.getTicker();
        if (ticker == null || ticker.trim().isEmpty()) {
            log.error("Ticker está vazio para investimento com ID: {}", investmentId);
            throw new CryptoPriceRetrievalException("Ticker não pode ser vazio.");
        }

        double currentPrice = cryptoService.getCryptoPriceInEUR(ticker);
        crypto.setLastSyncedPrice(currentPrice);
        cryptoRepository.save(crypto);

        log.info("Preço atualizado para criptomoeda '{}' (ID: {}): {} EUR", ticker, investmentId, currentPrice);
        return currentPrice;
    }

    /**
     * Calcula o valor atual de uma criptomoeda no investimento.
     *
     * @param investmentId ID do investimento em criptomoeda.
     * @return Valor atual em EUR.
     * @throws CryptoPriceRetrievalException Se ocorrer um erro ao recuperar o preço.
     */
    @Override
    public double getCurrentValue(UUID investmentId) throws CryptoPriceRetrievalException {
        Crypto crypto = cryptoRepository.findById(investmentId)
                .orElseThrow(() -> new CryptoPriceRetrievalException("Criptomoeda não encontrada com ID: " + investmentId));

        double currentPrice = crypto.getLastSyncedPrice();
        double units = crypto.getUnits();
        double currentValue = currentPrice * units;
        crypto.setCurrentValue(currentValue);
        cryptoRepository.save(crypto);

        log.info("Valor atual para criptomoeda '{}' (ID: {}): {} EUR", crypto.getTicker(), investmentId, currentValue);
        return currentValue;
    }

    /**
     * Recupera todas as criptomoedas associadas a um usuário.
     *
     * @param userId ID do usuário.
     * @return Lista de criptomoedas.
     */
    @Override
    public List<Crypto> getAllCryptosByUser(UUID userId) {
        List<Crypto> cryptos = cryptoRepository.findByPortfolioUserId(userId);
        log.info("Encontradas {} criptomoedas para o usuário ID: {}", cryptos.size(), userId);
        return cryptos;
    }

    /**
     * Adiciona uma nova criptomoeda ao portfólio.
     *
     * @param cryptoRequestDTO DTO contendo os dados da criptomoeda.
     * @return Criptomoeda adicionada.
     * @throws CryptoPriceRetrievalException Se ocorrer um erro ao recuperar o preço.
     */
    @Override
    public Crypto addCrypto(Crypto cryptoRequestDTO) throws CryptoPriceRetrievalException {
        // Verifica se o portfólio existe
        UUID portfolioId = cryptoRequestDTO.getPortfolio().getId();
        if (!portfolioService.existsById(portfolioId)) {
            log.error("Portfólio não encontrado com ID: {}", portfolioId);
            throw new CryptoPriceRetrievalException("Portfólio não encontrado com ID: " + portfolioId);
        }

        // Mapeia DTO para entidade
        Crypto crypto = CryptoMapper.toEntity(cryptoRequestDTO);
        crypto.setPortfolio(portfolioService.getPortfolioById(portfolioId));

        // Inicializa o valor atual
        crypto.setCurrentValue(0.0);
        crypto.setLastSyncedPrice(0.0);

        // Salva a criptomoeda
        Crypto savedCrypto = cryptoRepository.save(crypto);
        log.info("Nova criptomoeda adicionada: '{}', ID: {}", savedCrypto.getTicker(), savedCrypto.getId());

        return savedCrypto;
    }

    /**
     * Remove uma criptomoeda específica do portfólio.
     *
     * @param cryptoId ID da criptomoeda a ser removida.
     * @throws CryptoPriceRetrievalException Se ocorrer um erro ao remover a criptomoeda.
     */
    @Override
    public void removeCrypto(UUID cryptoId) throws CryptoPriceRetrievalException {
        Crypto crypto = cryptoRepository.findById(cryptoId)
                .orElseThrow(() -> new CryptoPriceRetrievalException("Criptomoeda não encontrada com ID: " + cryptoId));

        try {
            cryptoRepository.delete(crypto);
            log.info("Criptomoeda removida: '{}', ID: {}", crypto.getTicker(), cryptoId);
        } catch (Exception e) {
            log.error("Erro ao remover criptomoeda '{}' (ID: {}): {}", crypto.getTicker(), cryptoId, e.getMessage());
            throw new CryptoPriceRetrievalException("Erro ao remover a criptomoeda: " + crypto.getTicker(), e);
        }
    }

    /**
     * Atualiza os preços de todas as criptomoedas no portfólio do usuário.
     *
     * @param portfolioId ID do portfólio do usuário.
     * @throws CryptoPriceRetrievalException Se ocorrer um erro ao recuperar o preço.
     */
    @Override
    public void updateAllPrices(UUID portfolioId) throws CryptoPriceRetrievalException {
        List<Crypto> cryptos = cryptoRepository.findByPortfolioId(portfolioId);
        for (Crypto crypto : cryptos) {
            try {
                updatePrice(crypto.getId());
            } catch (CryptoPriceRetrievalException e) {
                log.error("Erro ao atualizar preço para criptomoeda ID: {}", crypto.getId(), e);
                // Dependendo da necessidade, pode optar por continuar ou interromper a atualização
                throw e;
            }
        }
        log.info("Todos os preços das criptomoedas no portfólio ID {} foram atualizados.", portfolioId);
    }
}
