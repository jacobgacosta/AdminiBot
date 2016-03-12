package io.dojogeek.adminibot.models;

public class IncomeBankCardModel {

    private long id;
    private String description;
    private String date;
    private double amount;
    private long incomeId;
    private long bankCardId;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public long getIncomeId() {
        return incomeId;
    }

    public void setIncomeId(long incomeId) {
        this.incomeId = incomeId;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public long getBankCardId() {
        return bankCardId;
    }

    public void setBankCardId(long bankCardId) {
        this.bankCardId = bankCardId;
    }
}
