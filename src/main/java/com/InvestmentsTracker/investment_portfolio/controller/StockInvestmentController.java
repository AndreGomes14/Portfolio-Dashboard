package com.InvestmentsTracker.investment_portfolio.controller;

import com.InvestmentsTracker.investment_portfolio.dto.stock.StockRequestDTO;
import com.InvestmentsTracker.investment_portfolio.dto.stock.StockResponseDTO;
import com.InvestmentsTracker.investment_portfolio.exception.InvestmentException;
import com.InvestmentsTracker.investment_portfolio.exception.StockPriceRetrievalException;
import com.InvestmentsTracker.investment_portfolio.mapper.StockMapper;
import com.InvestmentsTracker.investment_portfolio.model.Stock;
import com.InvestmentsTracker.investment_portfolio.service.stock.StockInvestmentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.UUID;

@RestController
@RequestMapping("/api/stocks")
@Slf4j
@RequiredArgsConstructor
public class StockInvestmentController extends InvestmentController {

    private final StockInvestmentService stockInvestmentService;
    private final StockMapper stockMapper;

    /**
     * Atualiza o preço de uma ação específica.
     *
     * @param investmentId ID da ação.
     * @return Preço atualizado.
     * @throws StockPriceRetrievalException Se ocorrer um erro ao atualizar o preço.
     */
    @PutMapping("/{investmentId}/update-price")
    public ResponseEntity<Double> updatePrice(@PathVariable UUID investmentId) throws StockPriceRetrievalException {
        double updatedPrice = stockInvestmentService.updatePrice(investmentId);
        return ResponseEntity.ok(updatedPrice);
    }

    /**
     * Calcula o valor atual de uma ação específica.
     *
     * @param investmentId ID da ação.
     * @return Valor atual.
     * @throws InvestmentException Se ocorrer um erro ao calcular o valor.
     */
    @GetMapping("/{investmentId}/current-value")
    public ResponseEntity<Double> getCurrentValue(@PathVariable UUID investmentId) throws InvestmentException {
        double currentValue = stockInvestmentService.getCurrentValue(investmentId);
        return ResponseEntity.ok(currentValue);
    }

    /**
     * Adiciona uma nova ação.
     *
     * @param stockRequestDTO DTO de requisição.
     * @return DTO de resposta da ação criada.
     * @throws StockPriceRetrievalException Se ocorrer um erro ao adicionar a ação.
     * @throws InvestmentException Se ocorrer um erro de investimento.
     */
    @PostMapping("/add")
    public ResponseEntity<StockResponseDTO> addStock(@Valid @RequestBody StockRequestDTO stockRequestDTO) throws StockPriceRetrievalException, InvestmentException {
        Stock savedStock = stockInvestmentService.addStock(stockRequestDTO);
        StockResponseDTO responseDTO = stockMapper.toDTO(savedStock);
        return new ResponseEntity<>(responseDTO, HttpStatus.CREATED);
    }

    /**
     * Remove uma ação específica.
     *
     * @param investmentId ID da ação a ser removida.
     * @return Resposta sem conteúdo.
     * @throws StockPriceRetrievalException Se ocorrer um erro ao remover a ação.
     */
    @DeleteMapping("/{investmentId}/remove")
    public ResponseEntity<Void> removeStock(@PathVariable UUID investmentId) throws StockPriceRetrievalException {
        stockInvestmentService.removeStock(investmentId);
        return ResponseEntity.noContent().build();
    }
}
