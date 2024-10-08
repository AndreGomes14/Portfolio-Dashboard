package com.InvestmentsTracker.investment_portfolio.mapper;

import com.InvestmentsTracker.investment_portfolio.dto.crypto.CryptoRequestDTO;
import com.InvestmentsTracker.investment_portfolio.dto.crypto.CryptoResponseDTO;
import com.InvestmentsTracker.investment_portfolio.dto.etf.EtfRequestDTO;
import com.InvestmentsTracker.investment_portfolio.dto.etf.EtfResponseDTO;
import com.InvestmentsTracker.investment_portfolio.dto.investment.*;
import com.InvestmentsTracker.investment_portfolio.dto.other.OtherRequestDTO;
import com.InvestmentsTracker.investment_portfolio.dto.other.OtherResponseDTO;
import com.InvestmentsTracker.investment_portfolio.dto.savings.SavingsRequestDTO;
import com.InvestmentsTracker.investment_portfolio.dto.savings.SavingsResponseDTO;
import com.InvestmentsTracker.investment_portfolio.dto.stock.StockRequestDTO;
import com.InvestmentsTracker.investment_portfolio.dto.stock.StockResponseDTO;
import com.InvestmentsTracker.investment_portfolio.exception.InvestmentException;
import com.InvestmentsTracker.investment_portfolio.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Component
public class InvestmentMapper {

    private static final String DATE_FORMAT = "yyyy-MM-dd";

    @Autowired
    private StockMapper stockMapper;

    /**
     * Converte uma string no formato "yyyy-MM-dd" para um objeto Date.
     *
     * @param dateStr Data como string.
     * @return Objeto Date correspondente.
     * @throws InvestmentException Se o formato da data for inválido.
     */
    private Date parseDate(String dateStr) throws InvestmentException {
        try {
            SimpleDateFormat formatter = new SimpleDateFormat(DATE_FORMAT);
            formatter.setLenient(false);
            return formatter.parse(dateStr);
        } catch (ParseException e) {
            throw new InvestmentException("Formato de data inválido: " + dateStr, e);
        }
    }

    /**
     * Converte um objeto Date para uma string no formato "yyyy-MM-dd".
     *
     * @param date Objeto Date.
     * @return Data como string.
     */
    private String formatDate(Date date) {
        SimpleDateFormat formatter = new SimpleDateFormat(DATE_FORMAT);
        return formatter.format(date);
    }

    /**
     * Mapeia um DTO de requisição para a entidade específica de investimento.
     *
     * @param dto DTO de requisição.
     * @return Entidade específica de investimento.
     * @throws InvestmentException Se ocorrer um erro durante a conversão.
     */
    public Investment toEntity(InvestmentRequestDTO dto) throws InvestmentException {
        switch (dto.getInvestmentType()) {
            case "Crypto":
                CryptoRequestDTO cryptoDTO = (CryptoRequestDTO) dto;
                Crypto crypto = new Crypto();
                crypto.setBuyPrice(cryptoDTO.getBuyPrice());
                crypto.setDate(parseDate(cryptoDTO.getDate()));
                crypto.setRiskLevel(cryptoDTO.getRiskLevel());
                crypto.setTicker(cryptoDTO.getTicker());
                crypto.setUnits(cryptoDTO.getUnits());
                return crypto;
            case "Etf":
                EtfRequestDTO etfDTO = (EtfRequestDTO) dto;
                Etf etf = new Etf();
                etf.setBuyPrice(etfDTO.getBuyPrice());
                etf.setDate(parseDate(etfDTO.getDate()));
                etf.setRiskLevel(etfDTO.getRiskLevel());
                etf.setTicker(etfDTO.getTicker());
                etf.setUnits(etfDTO.getUnits());
                return etf;
            case "Savings":
                SavingsRequestDTO savingsDTO = (SavingsRequestDTO) dto;
                Savings savings = new Savings();
                savings.setBuyPrice(savingsDTO.getBuyPrice());
                savings.setDate(parseDate(savingsDTO.getDate()));
                savings.setRiskLevel(savingsDTO.getRiskLevel());
                return savings;
            case "Stock":
                StockRequestDTO stockDTO = (StockRequestDTO) dto;
                Stock stock = new Stock();
                stock.setBuyPrice(stockDTO.getBuyPrice());
                stock.setDate(parseDate(stockDTO.getDate()));
                stock.setRiskLevel(stockDTO.getRiskLevel());
                stock.setTicker(stockDTO.getTicker());
                stock.setUnits(stockDTO.getUnits());
                return stock;
            case "Other":
                OtherRequestDTO otherDTO = (OtherRequestDTO) dto;
                Other other = new Other();
                other.setBuyPrice(otherDTO.getBuyPrice());
                other.setDate(parseDate(otherDTO.getDate()));
                other.setRiskLevel(otherDTO.getRiskLevel());
                other.setDescription(otherDTO.getDescription());
                other.setCategory(otherDTO.getCategory());
                return other;
            default:
                throw new IllegalArgumentException("Tipo de investimento inválido: " + dto.getInvestmentType());
        }
    }

    /**
     * Mapeia uma entidade específica de investimento para seu DTO de resposta.
     *
     * @param investment Entidade específica de investimento.
     * @return DTO de resposta.
     */
    public InvestmentResponseDTO toDTO(Investment investment) {
        switch (investment.getInvestmentType()) {
            case "Crypto":
                Crypto crypto = (Crypto) investment;
                CryptoResponseDTO cryptoDTO = new CryptoResponseDTO();
                cryptoDTO.setId(crypto.getId());
                cryptoDTO.setBuyPrice(crypto.getBuyPrice());
                cryptoDTO.setDate(formatDate(crypto.getDate()));
                cryptoDTO.setRiskLevel(crypto.getRiskLevel());
                cryptoDTO.setCurrentValue(crypto.getCurrentValue());
                cryptoDTO.setPortfolioId(crypto.getPortfolio().getId());
                cryptoDTO.setInvestmentType(crypto.getInvestmentType());
                cryptoDTO.setTicker(crypto.getTicker());
                cryptoDTO.setUnits(crypto.getUnits());
                cryptoDTO.setLastSyncedPrice(crypto.getLastSyncedPrice());
                return cryptoDTO;
            case "Etf":
                Etf etf = (Etf) investment;
                EtfResponseDTO etfDTO = new EtfResponseDTO();
                etfDTO.setId(etf.getId());
                etfDTO.setBuyPrice(etf.getBuyPrice());
                etfDTO.setDate(formatDate(etf.getDate()));
                etfDTO.setRiskLevel(etf.getRiskLevel());
                etfDTO.setCurrentValue(etf.getCurrentValue());
                etfDTO.setPortfolioId(etf.getPortfolio().getId());
                etfDTO.setInvestmentType(etf.getInvestmentType());
                etfDTO.setTicker(etf.getTicker());
                etfDTO.setUnits(etf.getUnits());
                etfDTO.setLastSyncedPrice(etf.getLastSyncedPrice());
                return etfDTO;
            case "Savings":
                Savings savings = (Savings) investment;
                SavingsResponseDTO savingsDTO = new SavingsResponseDTO();
                savingsDTO.setId(savings.getId());
                savingsDTO.setBuyPrice(savings.getBuyPrice());
                savingsDTO.setDate(formatDate(savings.getDate()));
                savingsDTO.setRiskLevel(savings.getRiskLevel());
                savingsDTO.setCurrentValue(savings.getCurrentValue());
                savingsDTO.setPortfolioId(savings.getPortfolio().getId());
                savingsDTO.setInvestmentType(savings.getInvestmentType());
                return savingsDTO;
            case "Stock":
                Stock stock = (Stock) investment;
                StockResponseDTO stockDTO = new StockResponseDTO();
                stockDTO.setId(stock.getId());
                stockDTO.setBuyPrice(stock.getBuyPrice());
                stockDTO.setDate(formatDate(stock.getDate()));
                stockDTO.setRiskLevel(stock.getRiskLevel());
                stockDTO.setCurrentValue(stock.getCurrentValue());
                stockDTO.setPortfolioId(stock.getPortfolio().getId());
                stockDTO.setInvestmentType(stock.getInvestmentType());
                stockDTO.setTicker(stock.getTicker());
                stockDTO.setUnits(stock.getUnits());
                stockDTO.setLastSyncedPrice(stock.getLastSyncedPrice());
                return stockDTO;
            case "Other":
                Other other = (Other) investment;
                OtherResponseDTO otherDTO = new OtherResponseDTO();
                otherDTO.setId(other.getId());
                otherDTO.setBuyPrice(other.getBuyPrice());
                otherDTO.setDate(formatDate(other.getDate()));
                otherDTO.setRiskLevel(other.getRiskLevel());
                otherDTO.setCurrentValue(other.getCurrentValue());
                otherDTO.setPortfolioId(other.getPortfolio().getId());
                otherDTO.setInvestmentType(other.getInvestmentType());
                otherDTO.setDescription(other.getDescription());
                otherDTO.setCategory(other.getCategory());
                return otherDTO;
            default:
                throw new IllegalArgumentException("Tipo de investimento inválido: " + investment.getInvestmentType());
        }
    }
}
