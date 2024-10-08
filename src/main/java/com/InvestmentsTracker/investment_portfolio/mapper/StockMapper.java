package com.InvestmentsTracker.investment_portfolio.mapper;

import com.InvestmentsTracker.investment_portfolio.dto.stock.StockRequestDTO;
import com.InvestmentsTracker.investment_portfolio.dto.stock.StockResponseDTO;
import com.InvestmentsTracker.investment_portfolio.exception.InvestmentException;
import com.InvestmentsTracker.investment_portfolio.model.Stock;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Component
public class StockMapper {

    private static final String DATE_FORMAT = "yyyy-MM-dd";

    /**
     * Converte um DTO de requisição para a entidade Stock.
     *
     * @param dto DTO de requisição.
     * @return Entidade Stock.
     * @throws InvestmentException Se ocorrer um erro durante a conversão.
     */
    public Stock toEntity(StockRequestDTO dto) throws InvestmentException {
        Stock stock = new Stock();
        stock.setTicker(dto.getTicker());
        stock.setUnits(dto.getUnits());
        stock.setBuyPrice(dto.getBuyPrice());

        try {
            SimpleDateFormat formatter = new SimpleDateFormat(DATE_FORMAT);
            formatter.setLenient(false);
            Date parsedDate = formatter.parse(dto.getDate());
            stock.setDate(parsedDate);
        } catch (ParseException e) {
            throw new InvestmentException("Formato de data inválido: " + dto.getDate(), e);
        }

        stock.setRiskLevel(dto.getRiskLevel());
        // O portfólio será setado no serviço após verificar sua existência
        return stock;
    }

    /**
     * Converte uma entidade Stock para um DTO de resposta.
     *
     * @param stock Entidade Stock.
     * @return DTO de resposta.
     */
    public StockResponseDTO toDTO(Stock stock) {
        StockResponseDTO dto = new StockResponseDTO();
        dto.setId(stock.getId());
        dto.setTicker(stock.getTicker());
        dto.setUnits(stock.getUnits());
        dto.setBuyPrice(stock.getBuyPrice());

        SimpleDateFormat formatter = new SimpleDateFormat(DATE_FORMAT);
        String formattedDate = formatter.format(stock.getDate());
        dto.setDate(formattedDate);

        dto.setRiskLevel(stock.getRiskLevel());
        dto.setCurrentValue(stock.getCurrentValue());
        dto.setPortfolioId(stock.getPortfolio().getId());
        dto.setInvestmentType(stock.getInvestmentType());
        dto.setLastSyncedPrice(stock.getLastSyncedPrice());
        return dto;
    }
}
