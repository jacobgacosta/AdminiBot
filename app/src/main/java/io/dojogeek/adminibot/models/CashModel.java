package io.dojogeek.adminibot.models;

import java.math.BigDecimal;

public class CashModel {

    private String alias;
    private BigDecimal amount;

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }
}
