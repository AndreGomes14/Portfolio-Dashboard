package com.InvestmentsTracker.investment_portfolio.mapper;

import com.InvestmentsTracker.investment_portfolio.dto.crypto.CryptoRequestDTO;
import com.InvestmentsTracker.investment_portfolio.dto.crypto.CryptoResponseDTO;
import com.InvestmentsTracker.investment_portfolio.model.Crypto;

public class CryptoMapper {

    /**
     * Converte um DTO de requisição em uma entidade Crypto.
     *
     * @param dto DTO contendo os dados da Crypto.
     * @return Entidade Crypto.
     */
    public static Crypto toEntity(CryptoRequestDTO dto) {
        Crypto crypto = new Crypto();
        crypto.setTicker(dto.getTicker());
        crypto.setUnits(dto.getUnits());
        crypto.setBuyPrice(dto.getBuyPrice());
        crypto.setDate(java.sql.Date.valueOf(dto.getDate()));
        crypto.setRiskLevel(dto.getRiskLevel());
        // Portfolio será associado separadamente via serviço
        return crypto;
    }

    /**
     * Converte uma entidade Crypto em um DTO de resposta.
     *
     * @param crypto Entidade Crypto.
     * @return DTO de resposta da Crypto.
     */
    public static CryptoResponseDTO toDTO(Crypto crypto) {
        CryptoResponseDTO dto = new CryptoResponseDTO();
        dto.setId(crypto.getId());
        dto.setTicker(crypto.getTicker());
        dto.setUnits(crypto.getUnits());
        dto.setBuyPrice(crypto.getBuyPrice());
        dto.setDate(crypto.getDate().toString());
        dto.setRiskLevel(crypto.getRiskLevel());
        dto.setLastSyncedPrice(crypto.getLastSyncedPrice());
        dto.setCurrentValue(crypto.getCurrentValue());
        dto.setPortfolioId(crypto.getPortfolio().getId());
        dto.setInvestmentType(crypto.getInvestmentType());
        return dto;
    }
}
