package io.dojogeek.adminibot.models;

import java.util.List;

public class ExpenseModel {

    private long id;
    private String description;
    private double amount;
    private String dateExpediture;
    private String nextExit;
    private long expenseTypeId;
    private long userId;
    private List<ExpenseOtherPaymentMethodModel> otherPaymentMethodModels;
    private List<ExpenseBankCardModel> expenseBankCardModels;

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

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getDateExpediture() {
        return dateExpediture;
    }

    public void setDateExpediture(String dateExpediture) {
        this.dateExpediture = dateExpediture;
    }

    public String getNextExit() {
        return nextExit;
    }

    public void setNextExit(String nextExit) {
        this.nextExit = nextExit;
    }

    public long getExpenseTypeId() {
        return expenseTypeId;
    }

    public void setExpenseTypeId(long expenseTypeId) {
        this.expenseTypeId = expenseTypeId;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public List<ExpenseOtherPaymentMethodModel> getOtherPaymentMethodModels() {
        return otherPaymentMethodModels;
    }

    public void setOtherPaymentMethodModels(List<ExpenseOtherPaymentMethodModel> otherPaymentMethodModels) {
        this.otherPaymentMethodModels = otherPaymentMethodModels;
    }

    public List<ExpenseBankCardModel> getExpenseBankCardModels() {
        return expenseBankCardModels;
    }

    public void setExpenseBankCardModels(List<ExpenseBankCardModel> expenseBankCardModels) {
        this.expenseBankCardModels = expenseBankCardModels;
    }
}
