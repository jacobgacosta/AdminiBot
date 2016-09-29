package io.dojogeek.adminibot.models;

public class CardDetailModel {

    private long id;
    private double creditLimit;
    private double currentBalance;
    private String cuttoffDate;
    private String payDayLimit;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public double getCreditLimit() {
        return creditLimit;
    }

    public void setCreditLimit(double creditLimit) {
        this.creditLimit = creditLimit;
    }

    public double getCurrentBalance() {
        return currentBalance;
    }

    public void setCurrentBalance(double currentBalance) {
        this.currentBalance = currentBalance;
    }

    public String getCuttoffDate() {
        return cuttoffDate;
    }

    public void setCuttoffDate(String cuttoffDate) {
        this.cuttoffDate = cuttoffDate;
    }

    public String getPayDayLimit() {
        return payDayLimit;
    }

    public void setPayDayLimit(String payDayLimit) {
        this.payDayLimit = payDayLimit;
    }

}
