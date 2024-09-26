package com.InvestmentsTracker.investment_portfolio.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;

import com.InvestmentsTracker.investment_portfolio.exception.ErrorResponse;

import javax.naming.AuthenticationException;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public final ResponseEntity<ErrorResponse> handleResourceNotFoundException(ResourceNotFoundException ex, WebRequest request) {
        ErrorResponse errorDetails = new ErrorResponse(HttpStatus.NOT_FOUND.value(), ex.getMessage());
        return new ResponseEntity<>(errorDetails, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(UnauthorizedAccessException.class)
    public final ResponseEntity<ErrorResponse> handleUnauthorizedAccessException(UnauthorizedAccessException ex, WebRequest request) {
        ErrorResponse errorDetails = new ErrorResponse(HttpStatus.FORBIDDEN.value(), ex.getMessage());
        return new ResponseEntity<>(errorDetails, HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(InvalidInputException.class)
    public final ResponseEntity<ErrorResponse> handleInvalidInputException(InvalidInputException ex, WebRequest request) {
        ErrorResponse errorDetails = new ErrorResponse(HttpStatus.BAD_REQUEST.value(), ex.getMessage());
        return new ResponseEntity<>(errorDetails, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    public final ResponseEntity<ErrorResponse> handleAllExceptions(Exception ex, WebRequest request) {
        ErrorResponse errorDetails = new ErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), "An error occurred");
        return new ResponseEntity<>(errorDetails, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(DuplicateUserException.class)
    public final ResponseEntity<ErrorResponse> handleDuplicateUserException(DuplicateUserException ex, WebRequest request) {
        ErrorResponse errorDetails = new ErrorResponse(HttpStatus.CONFLICT.value(), ex.getMessage());
        return new ResponseEntity<>(errorDetails, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(CryptoPriceRetrievalException.class)
    public ResponseEntity<ErrorResponse> handleCryptoPriceRetrievalException(CryptoPriceRetrievalException ex) {
        log.error("CryptoPriceRetrievalException: {}", ex.getMessage());
        ErrorResponse error = new ErrorResponse(HttpStatus.BAD_REQUEST.value(), ex.getMessage());
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    /**
     * Lida com exceções de recuperação de preço de ETFs.
     *
     * @param ex Exceção EtfPriceRetrievalException
     * @return ResponseEntity com status 400 e mensagem de erro
     */
    @ExceptionHandler(EtfPriceRetrievalException.class)
    public ResponseEntity<ErrorResponse> handleEtfPriceRetrievalException(EtfPriceRetrievalException ex) {
        log.error("EtfPriceRetrievalException: {}", ex.getMessage());
        ErrorResponse error = new ErrorResponse(HttpStatus.BAD_REQUEST.value(), ex.getMessage());
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }
    /**
     * Lida com exceções de recuperação de preço de Other investments.
     *
     * @param ex Exceção OtherPriceRetrievalException
     * @return ResponseEntity com status 400 e mensagem de erro
     */
    @ExceptionHandler(OtherPriceRetrievalException.class)
    public ResponseEntity<ErrorResponse> handleOtherPriceRetrievalException(OtherPriceRetrievalException ex) {
        log.error("OtherPriceRetrievalException: {}", ex.getMessage());
        ErrorResponse error = new ErrorResponse(HttpStatus.BAD_REQUEST.value(), ex.getMessage());
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    /**
     * Lida com exceções de investimento inválido.
     *
     * @param ex Exceção IllegalArgumentException
     * @return ResponseEntity com status 400 e mensagem de erro
     */
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ErrorResponse> handleIllegalArgumentException(IllegalArgumentException ex) {
        log.error("IllegalArgumentException: {}", ex.getMessage());
        ErrorResponse error = new ErrorResponse(HttpStatus.BAD_REQUEST.value(), ex.getMessage());
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    /**
     * Lida com exceções de recuperação de preço de Stocks.
     *
     * @param ex Exceção StockPriceRetrievalException
     * @return ResponseEntity com status 400 e mensagem de erro
     */
    @ExceptionHandler(StockPriceRetrievalException.class)
    public ResponseEntity<ErrorResponse> handleStockPriceRetrievalException(StockPriceRetrievalException ex) {
        log.error("StockPriceRetrievalException: {}", ex.getMessage());
        ErrorResponse error = new ErrorResponse(HttpStatus.BAD_REQUEST.value(), ex.getMessage());
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    /**
     * Lida com outras exceções não tratadas explicitamente.
     *
     * @param ex Exceção Exception
     * @return ResponseEntity com status 500 e mensagem de erro
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleException(Exception ex) {
        log.error("Exception: {}", ex.getMessage(), ex);
        ErrorResponse error = new ErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Erro interno do servidor.");
        return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    /**
     * Lida com exceções de usuário não encontrado.
     *
     * @param ex Exceção UserNotFoundException
     * @return ResponseEntity com status 404 e mensagem de erro
     */
    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleUserNotFoundException(UserNotFoundException ex) {
        log.error("UserNotFoundException: {}", ex.getMessage());
        ErrorResponse error = new ErrorResponse(HttpStatus.NOT_FOUND.value(), ex.getMessage());
        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }

    /**
     * Lida com exceções de portfólio não encontrado.
     *
     * @param ex Exceção PortfolioNotFoundException
     * @return ResponseEntity com status 404 e mensagem de erro
     */
    @ExceptionHandler(PortfolioNotFoundException.class)
    public ResponseEntity<ErrorResponse> handlePortfolioNotFoundException(PortfolioNotFoundException ex) {
        log.error("PortfolioNotFoundException: {}", ex.getMessage());
        ErrorResponse error = new ErrorResponse(HttpStatus.NOT_FOUND.value(), ex.getMessage());
        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }

    /**
     * Lida com exceções quando o email já está registrado.
     *
     * @param ex Exceção EmailAlreadyExistsException
     * @return ResponseEntity com status 409 e mensagem de erro
     */
    @ExceptionHandler(EmailAlreadyExistsException.class)
    public ResponseEntity<ErrorResponse> handleEmailAlreadyExistsException(EmailAlreadyExistsException ex) {
        log.error("EmailAlreadyExistsException: {}", ex.getMessage());
        ErrorResponse error = new ErrorResponse(HttpStatus.CONFLICT.value(), ex.getMessage());
        return new ResponseEntity<>(error, HttpStatus.CONFLICT);
    }

    /**
     * Lida com exceções quando as credenciais são inválidas.
     *
     * @param ex Exceção InvalidCredentialsException
     * @return ResponseEntity com status 401 e mensagem de erro
     */
    @ExceptionHandler(InvalidCredentialsException.class)
    public ResponseEntity<ErrorResponse> handleInvalidCredentialsException(InvalidCredentialsException ex) {
        log.error("InvalidCredentialsException: {}", ex.getMessage());
        ErrorResponse error = new ErrorResponse(HttpStatus.UNAUTHORIZED.value(), ex.getMessage());
        return new ResponseEntity<>(error, HttpStatus.UNAUTHORIZED);
    }

    /**
     * Lida com exceções de autenticação não autorizada.
     *
     * @param ex Exceção AuthenticationException
     * @return ResponseEntity com status 401 e mensagem de erro
     */
    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<ErrorResponse> handleAuthenticationException(AuthenticationException ex) {
        log.error("AuthenticationException: {}", ex.getMessage());
        ErrorResponse error = new ErrorResponse(HttpStatus.UNAUTHORIZED.value(), "Autenticação falhou.");
        return new ResponseEntity<>(error, HttpStatus.UNAUTHORIZED);
    }


}
