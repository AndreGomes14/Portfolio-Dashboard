package com.InvestmentsTracker.investment_portfolio.controller;

import com.InvestmentsTracker.investment_portfolio.dto.crypto.CryptoRequestDTO;
import com.InvestmentsTracker.investment_portfolio.dto.crypto.CryptoResponseDTO;
import com.InvestmentsTracker.investment_portfolio.exception.CryptoPriceRetrievalException;
import com.InvestmentsTracker.investment_portfolio.mapper.CryptoMapper;
import com.InvestmentsTracker.investment_portfolio.model.Crypto;
import com.InvestmentsTracker.investment_portfolio.service.crypto.CryptoInvestmentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

/**
 * Controlador específico para gerenciar investimentos do tipo "Crypto".
 */
@RestController
@RequestMapping("/api/investments/crypto")
@Slf4j
@RequiredArgsConstructor
public class CryptoInvestmentController extends InvestmentController {

    private final CryptoInvestmentService cryptoInvestmentService;
    private final CryptoMapper cryptoMapper;

    /**
     * Adiciona um novo investimento do tipo "Crypto".
     *
     * @param cryptoRequestDTO DTO contendo os dados do investimento "Crypto".
     * @return Investimento "Crypto" adicionado.
     * @throws CryptoPriceRetrievalException Se ocorrer um erro ao adicionar o investimento.
     */
    @PostMapping
    public ResponseEntity<CryptoResponseDTO> addCrypto(@RequestBody CryptoRequestDTO cryptoRequestDTO) throws CryptoPriceRetrievalException {
        Crypto crypto = CryptoMapper.toEntity(cryptoRequestDTO);
        Crypto savedCrypto = cryptoInvestmentService.addCrypto(crypto);
        CryptoResponseDTO responseDTO = CryptoMapper.toDTO(savedCrypto);
        return new ResponseEntity<>(responseDTO, HttpStatus.CREATED);
    }

    /**
     * Atualiza manualmente o valor de um investimento "Crypto" específico.
     *
     * @param investmentId ID do investimento "Crypto".
     * @param newValue Novo valor atual em EUR.
     * @return Resposta vazia com status 200.
     * @throws CryptoPriceRetrievalException Se ocorrer um erro ao atualizar o valor.
     */
    @PutMapping("/{investmentId}/update-value")
    public ResponseEntity<Void> updateCryptoValue(@PathVariable UUID investmentId, @RequestParam double newValue) throws CryptoPriceRetrievalException {
        cryptoInvestmentService.getCurrentValue(investmentId);
        return ResponseEntity.ok().build();
    }

    /**
     * Remove um investimento "Crypto" específico.
     *
     * @param investmentId ID do investimento "Crypto".
     * @return Resposta vazia com status 204.
     * @throws CryptoPriceRetrievalException Se ocorrer um erro ao remover o investimento.
     */
    @DeleteMapping("/{investmentId}")
    @Override
    public ResponseEntity<Void> removeInvestment(@PathVariable UUID investmentId) throws CryptoPriceRetrievalException {
        cryptoInvestmentService.removeCrypto(investmentId);
        return ResponseEntity.noContent().build();
    }

    /**
     * Recupera o valor atual de um investimento "Crypto" específico.
     *
     * @param investmentId ID do investimento "Crypto".
     * @return Valor atual em EUR.
     * @throws CryptoPriceRetrievalException Se ocorrer um erro ao recuperar o valor.
     */
    @GetMapping("/{investmentId}/current-value")
    @Override
    public ResponseEntity<Double> getCurrentValue(@PathVariable UUID investmentId) throws CryptoPriceRetrievalException {
        double currentValue = cryptoInvestmentService.getCurrentValue(investmentId);
        return ResponseEntity.ok(currentValue);
    }
}
