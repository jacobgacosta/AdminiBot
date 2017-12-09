package io.dojogeek.adminibot.models;

public class ExpenseOtherPaymentMethodModel {

    private long id;
    private long expenseId;
    private long otherPaymentMethodId;
    private double amount;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getExpenseId() {
        return expenseId;
    }

    public void setExpenseId(long expenseId) {
        this.expenseId = expenseId;
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
