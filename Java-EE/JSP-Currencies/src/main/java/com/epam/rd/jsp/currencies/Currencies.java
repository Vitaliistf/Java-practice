package com.epam.rd.jsp.currencies;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.util.*;

public class Currencies {
    private final Map<String, BigDecimal> curs = new TreeMap<>();

    public void addCurrency(String currency, BigDecimal weight) {
       curs.put(currency, weight);
    }

    public Collection<String> getCurrencies() {
        return curs.keySet();
    }

    public Map<String, BigDecimal> getExchangeRates(String referenceCurrency) {
        Map<String, BigDecimal> result = new TreeMap<>();

        for(Map.Entry<String, BigDecimal> entry : curs.entrySet()) {
            result.put(entry.getKey(), convert(BigDecimal.valueOf(1), referenceCurrency, entry.getKey()));
        }

        return result;
    }

    public BigDecimal convert(BigDecimal amount, String sourceCurrency, String targetCurrency) {
        BigDecimal sourceRate = curs.get(sourceCurrency);

        BigDecimal targetRate = curs.get(targetCurrency);
        return sourceRate.divide(targetRate, new MathContext(10, RoundingMode.HALF_UP))
                .multiply(amount).setScale(5, RoundingMode.HALF_UP);
    }
}
