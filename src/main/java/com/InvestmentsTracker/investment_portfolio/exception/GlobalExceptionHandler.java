package com.InvestmentsTracker.investment_portfolio.exception;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(IllegalArgumentException.class)
    public String handleIllegalArgumentException(IllegalArgumentException e, RedirectAttributes redirectAttributes) {
        redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
        return "redirect:/register";
    }
}
