package com.InvestmentsTracker.investment_portfolio.controller;

import com.InvestmentsTracker.investment_portfolio.model.User;
import com.InvestmentsTracker.investment_portfolio.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;

@Controller
public class RegistrationController {

    @Autowired
    private UserService userService;

    // Display the registration form
    @GetMapping("/register")
    public String showRegistrationForm(Model model) {
        model.addAttribute("user", new User());
        return "register"; // This refers to register.html in templates
    }

    // Handle form submission
    @PostMapping("/register")
    public String registerUser(@Valid @ModelAttribute("user") User user, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            // Return to registration form if validation errors exist
            return "register";
        }

        try {
            userService.registerUser(user);
            return "redirect:/login?registered"; // Redirect to login page with a success message
        } catch (IllegalArgumentException e) {
            model.addAttribute("registrationError", e.getMessage());
            return "register";
        }
    }
}
