// src/main/java/com/InvestmentsTracker/investment_portfolio/config/AppConfig.java
package com.InvestmentsTracker.investment_portfolio.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class AppConfig {

    /**
     * Configura e disponibiliza uma instância de RestTemplate para injeção.
     *
     * @return Instância de RestTemplate.
     */
    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

    // Outras configurações, se necessário
}
