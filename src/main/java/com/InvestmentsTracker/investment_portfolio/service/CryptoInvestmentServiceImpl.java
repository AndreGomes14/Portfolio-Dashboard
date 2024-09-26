package com.InvestmentsTracker.investment_portfolio.service;

import com.InvestmentsTracker.investment_portfolio.exception.CryptoPriceRetrievalException;
import com.InvestmentsTracker.investment_portfolio.model.Crypto;
import com.InvestmentsTracker.investment_portfolio.repository.CryptoRepository;
import com.InvestmentsTracker.investment_portfolio.service.CryptoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Implementação do serviço específico para investimentos em criptomoedas.
 */
@Service
@Slf4j
public class CryptoInvestmentServiceImpl implements CryptoInvestmentService {

    private final CryptoService cryptoService;
    private final CryptoRepository cryptoRepository;

    @Autowired
    public CryptoInvestmentServiceImpl(CryptoService cryptoService, CryptoRepository cryptoRepository) {
        this.cryptoService = cryptoService;
        this.cryptoRepository = cryptoRepository;
    }

    /**
     * Atualiza o preço de uma criptomoeda específica no investimento.
     *
     * @param investmentId ID do investimento em criptomoeda.
     * @return Preço atualizado em EUR.
     * @throws CryptoPriceRetrievalException Se ocorrer um erro ao recuperar o preço.
     */
    @Override
    public double updatePrice(Long investmentId) throws CryptoPriceRetrievalException {
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
     * Atualiza os preços de todas as criptomoedas no portfólio do usuário.
     *
     * @param portfolioId ID do portfólio do usuário.
     * @throws CryptoPriceRetrievalException Se ocorrer um erro ao recuperar o preço.
     */
    @Override
    public void updateAllPrices(Long portfolioId) throws CryptoPriceRetrievalException {
        List<Crypto> cryptos = cryptoRepository.findByPortfolioId(portfolioId);
        for (Crypto crypto : cryptos) {
            try {
                updatePrice(crypto.getId());
            } catch (CryptoPriceRetrievalException e) {
                log.error("Erro ao atualizar preço para criptomoeda ID: {}", crypto.getId(), e);
                // Dependendo da necessidade, você pode optar por continuar ou interromper a atualização
                throw e;
            }
        }
        log.info("Todos os preços das criptomoedas no portfólio ID {} foram atualizados.", portfolioId);
    }

    /**
     * Calcula o valor atual de uma criptomoeda no investimento.
     *
     * @param investmentId ID do investimento em criptomoeda.
     * @return Valor atual em EUR.
     * @throws CryptoPriceRetrievalException Se ocorrer um erro ao recuperar o preço.
     */
    @Override
    public double getCurrentValue(Long investmentId) throws CryptoPriceRetrievalException {
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
    public List<Crypto> getAllCryptosByUser(Long userId) {
        List<Crypto> cryptos = cryptoRepository.findByPortfolioUserId(userId);
        log.info("Encontradas {} criptomoedas para o usuário ID: {}", cryptos.size(), userId);
        return cryptos;
    }


}
