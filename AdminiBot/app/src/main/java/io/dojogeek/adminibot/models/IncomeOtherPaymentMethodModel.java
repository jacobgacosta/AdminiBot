package io.dojogeek.adminibot.models;

public class IncomeOtherPaymentMethodModel {

    private long id;
    private long incomeId;
    private long otherPaymentMethodId;
    private double amount;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getIncomeId() {
        return incomeId;
    }

    public void setIncomeId(long incomeId) {
        this.incomeId = incomeId;
    }

    public long getOtherPaymentMethodId() {
        return otherPaymentMethodId;
    }

    public void setOtherPaymentMethodId(long otherPaymentMethodId) {
        this.otherPaymentMethodId = otherPaymentMethodId;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }
}
