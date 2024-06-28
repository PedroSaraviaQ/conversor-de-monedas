package com.example.conversordemonedas;

public class Currency {
    private String initialCurrency;
    private String finalCurrency;
    private Double rate;

    public Currency(String initialCurrency, String finalCurrency, Double rate) {
        this.initialCurrency = initialCurrency;
        this.finalCurrency = finalCurrency;
        this.rate = rate;
    }

    public String getInitialCurrency() {
        return initialCurrency;
    }

    public String getFinalCurrency() {
        return finalCurrency;
    }

    public Double getRate() {
        return rate;
    }
}
