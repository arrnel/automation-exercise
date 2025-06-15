package com.automationexercise.tests.models;

import lombok.Builder;

import java.math.BigDecimal;

@Builder
public record PriceDTO(

        Currency currency,

        BigDecimal amount

) {

    public String getPriceText() {
        return "%s. %s".formatted(getCapitalizeCurrencyText(), getAmountText());
    }

    public String getCapitalizeCurrencyText() {
        if (this.currency == null)
            throw new IllegalStateException("Currency cannot be null");
        return this.currency.name().charAt(0) + this.currency.name().substring(1).toLowerCase();
    }

    public String getAmountText() {
        if (this.amount == null)
            throw new IllegalStateException("Amount can not be null");
        var strippedAmount = this.amount.stripTrailingZeros();
        return strippedAmount.scale() <= 0
                ? strippedAmount.toBigInteger().toString()
                : strippedAmount.toPlainString();
    }

}
