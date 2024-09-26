// src/main/java/com/InvestmentsTracker/investment_portfolio/exception/InvalidCredentialsException.java
package com.InvestmentsTracker.investment_portfolio.exception;

/**
 * Exceção lançada quando as credenciais de login são inválidas.
 */
public class InvalidCredentialsException extends RuntimeException {

    public InvalidCredentialsException() {
        super();
    }

    public InvalidCredentialsException(String message) {
        super(message);
    }

    public InvalidCredentialsException(String message, Throwable cause) {
        super(message, cause);
    }

    public InvalidCredentialsException(Throwable cause) {
        super(cause);
    }
}
