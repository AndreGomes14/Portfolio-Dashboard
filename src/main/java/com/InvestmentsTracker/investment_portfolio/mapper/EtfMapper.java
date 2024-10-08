package com.InvestmentsTracker.investment_portfolio.mapper;

import com.InvestmentsTracker.investment_portfolio.dto.etf.EtfRequestDTO;
import com.InvestmentsTracker.investment_portfolio.dto.etf.EtfResponseDTO;
import com.InvestmentsTracker.investment_portfolio.model.Etf;

public class EtfMapper {

    /**
     * Converte um DTO de requisição em uma entidade Etf.
     *
     * @param dto DTO contendo os dados da Etf.
     * @return Entidade Etf.
     */
    public static Etf toEntity(EtfRequestDTO dto) {
        Etf etf = new Etf();
        etf.setTicker(dto.getTicker());
        etf.setUnits(dto.getUnits());
        etf.setBuyPrice(dto.getBuyPrice());
        etf.setDate(java.sql.Date.valueOf(dto.getDate()));
        etf.setRiskLevel(dto.getRiskLevel());
        // Portfolio será associado separadamente via serviço
        return etf;
    }

    /**
     * Converte uma entidade Etf em um DTO de resposta.
     *
     * @param etf Entidade Etf.
     * @return DTO de resposta da Etf.
     */
    public static EtfResponseDTO toDTO(Etf etf) {
        EtfResponseDTO dto = new EtfResponseDTO();
        dto.setId(etf.getId());
        dto.setTicker(etf.getTicker());
        dto.setUnits(etf.getUnits());
        dto.setBuyPrice(etf.getBuyPrice());
        dto.setDate(etf.getDate().toString());
        dto.setRiskLevel(etf.getRiskLevel());
        dto.setLastSyncedPrice(etf.getLastSyncedPrice());
        dto.setCurrentValue(etf.getCurrentValue());
        dto.setPortfolioId(etf.getPortfolio().getId());
        dto.setInvestmentType(etf.getInvestmentType());
        return dto;
    }
}
