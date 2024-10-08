package com.InvestmentsTracker.investment_portfolio.controller;

import com.InvestmentsTracker.investment_portfolio.dto.other.OtherRequestDTO;
import com.InvestmentsTracker.investment_portfolio.dto.other.OtherResponseDTO;
import com.InvestmentsTracker.investment_portfolio.exception.OtherPriceRetrievalException;
import com.InvestmentsTracker.investment_portfolio.mapper.InvestmentMapper;
import com.InvestmentsTracker.investment_portfolio.model.Other;
import com.InvestmentsTracker.investment_portfolio.service.investment.InvestmentService;
import com.InvestmentsTracker.investment_portfolio.service.other.OtherInvestmentService;
import com.InvestmentsTracker.investment_portfolio.mapper.OtherMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

/**
 * Controlador específico para gerenciar investimentos do tipo "Other".
 */
@RestController
@RequestMapping("/api/investments/other")
@Slf4j
public class OtherInvestmentController extends InvestmentController {

    private final OtherInvestmentService otherInvestmentService;
    private final OtherMapper otherMapper;

    public OtherInvestmentController(InvestmentService investmentService,
                                     InvestmentMapper investmentMapper,
                                     OtherInvestmentService otherInvestmentService,
                                     OtherMapper otherMapper) {
        super(investmentService, investmentMapper);
        this.otherInvestmentService = otherInvestmentService;
        this.otherMapper = otherMapper;
    }

    /**
     * Adiciona um novo investimento do tipo "Other".
     *
     * @param otherRequestDTO DTO contendo os dados do investimento "Other".
     * @return Investimento "Other" adicionado.
     * @throws OtherPriceRetrievalException Se ocorrer um erro ao adicionar o investimento.
     */
    @PostMapping
    public ResponseEntity<OtherResponseDTO> addOther(@RequestBody OtherRequestDTO otherRequestDTO) throws OtherPriceRetrievalException {
        Other other = otherMapper.toEntity(otherRequestDTO);
        Other savedOther = otherInvestmentService.addOther(other);
        OtherResponseDTO responseDTO = otherMapper.toDTO(savedOther);
        return new ResponseEntity<>(responseDTO, HttpStatus.CREATED);
    }

    /**
     * Atualiza manualmente o valor de um investimento "Other" específico.
     *
     * @param investmentId ID do investimento "Other".
     * @param newValue Novo valor atual em EUR.
     * @return Resposta vazia com status 200.
     * @throws OtherPriceRetrievalException Se ocorrer um erro ao atualizar o valor.
     */
    @PutMapping("/{investmentId}/update-value")
    public ResponseEntity<Void> updateOtherValue(@PathVariable UUID investmentId, @RequestParam double newValue) throws OtherPriceRetrievalException {
        otherInvestmentService.updateValue(investmentId, newValue);
        return ResponseEntity.ok().build();
    }

    /**
     * Remove um investimento "Other" específico.
     *
     * @param investmentId ID do investimento "Other".
     * @return Resposta vazia com status 204.
     * @throws OtherPriceRetrievalException Se ocorrer um erro ao remover o investimento.
     */
    @DeleteMapping("/{investmentId}")
    @Override
    public ResponseEntity<Void> removeInvestment(@PathVariable UUID investmentId) throws OtherPriceRetrievalException {
        otherInvestmentService.removeOther(investmentId);
        return ResponseEntity.noContent().build();
    }

    /**
     * Recupera o valor atual de um investimento "Other" específico.
     *
     * @param investmentId ID do investimento "Other".
     * @return Valor atual em EUR.
     * @throws OtherPriceRetrievalException Se ocorrer um erro ao recuperar o valor.
     */
    @GetMapping("/{investmentId}/current-value")
    @Override
    public ResponseEntity<Double> getCurrentValue(@PathVariable UUID investmentId) throws OtherPriceRetrievalException {
        double currentValue = otherInvestmentService.getCurrentValue(investmentId);
        return ResponseEntity.ok(currentValue);
    }
}
