package com.InvestmentsTracker.investment_portfolio.service;

import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

public class CryptoService {

    private static final String COINGECKO_API_URL = "https://api.coingecko.com/api/v3/simple/price?ids=bitcoin&vs_currencies=eur";

    public double getCryptoPriceInEUR(String coinId) {
        RestTemplate restTemplate = new RestTemplate();
        String url = "https://api.coingecko.com/api/v3/simple/price?ids=" + coinId + "&vs_currencies=eur";
        ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);

        if (response.getStatusCode() == HttpStatus.OK) {
            try {
                JSONObject jsonObject = new JSONObject(response.getBody());
                double price = jsonObject
                        .getJSONObject(coinId)
                        .getDouble("eur"); // Price in EUR
                return price;
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return -1;
    }
}


