package io.dojogeek.adminibot.models;

public class MovementExpenseBankCardModel {

    private long id;
    private String description;
    private String date;
    private double amount;
    private long expenseId;
    private long bankCardId;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public long getExpenseId() {
        return expenseId;
    }

    public void setExpenseId(long expenseId) {
        this.expenseId = expenseId;
    }

    public long getBankCardId() {
        return bankCardId;
    }

    public void setBankCardId(long bankCardId) {
        this.bankCardId = bankCardId;
    }
}
