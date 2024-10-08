package com.InvestmentsTracker.investment_portfolio.controller;

import com.InvestmentsTracker.investment_portfolio.dto.etf.EtfRequestDTO;
import com.InvestmentsTracker.investment_portfolio.dto.etf.EtfResponseDTO;
import com.InvestmentsTracker.investment_portfolio.exception.EtfPriceRetrievalException;
import com.InvestmentsTracker.investment_portfolio.exception.InvestmentException;
import com.InvestmentsTracker.investment_portfolio.model.Etf;
import com.InvestmentsTracker.investment_portfolio.service.etf.EtfInvestmentService;
import com.InvestmentsTracker.investment_portfolio.mapper.EtfMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/investments/etf")
@Slf4j
@RequiredArgsConstructor
public class EtfInvestmentController extends InvestmentController {

    private final EtfInvestmentService etfInvestmentService;
    private final EtfMapper etfMapper;

    /**
     * Adiciona um novo investimento em ETF.
     *
     * @param etfRequestDTO DTO contendo os dados do ETF.
     * @return EtfResponseDTO do ETF criado.
     */
    @PostMapping
    public ResponseEntity<EtfResponseDTO> addEtf(@RequestBody EtfRequestDTO etfRequestDTO) {
        try {
            Etf etf = etfInvestmentService.addEtf(etfMapper.toEntity(etfRequestDTO));
            EtfResponseDTO responseDTO = etfMapper.toDTO(etf);
            return new ResponseEntity<>(responseDTO, HttpStatus.CREATED);
        } catch (EtfPriceRetrievalException e) {
            log.error("Erro ao adicionar ETF: {}", e.getMessage());
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * Atualiza o preço de um ETF.
     *
     * @param investmentId ID do investimento.
     * @return ResponseEntity com o preço atualizado.
     */
    @PutMapping("/{investmentId}/updatePrice")
    public ResponseEntity<Double> updateEtfPrice(@PathVariable UUID investmentId) {
        try {
            double updatedPrice = etfInvestmentService.updatePrice(investmentId);
            return ResponseEntity.ok(updatedPrice);
        } catch (EtfPriceRetrievalException | InvestmentException e) {
            log.error("Erro ao atualizar preço do ETF: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    /**
     * Remove um ETF pelo ID.
     *
     * @param investmentId ID do investimento.
     * @return ResponseEntity com status apropriado.
     */
    @DeleteMapping("/{investmentId}")
    public ResponseEntity<Void> removeEtf(@PathVariable UUID investmentId) {
        try {
            etfInvestmentService.removeEtf(investmentId);
            return ResponseEntity.noContent().build();
        } catch (EtfPriceRetrievalException e) {
            log.error("Erro ao remover ETF: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    // Outros endpoints específicos para ETF, se necessário
}
