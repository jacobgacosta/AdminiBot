package io.dojogeek.adminibot.models;

import java.util.Date;
import java.util.List;

public class ExpenseModel {

    public long id;
    public String description;
    public double amount;
    public String dateExpediture;
    public String nextExit;
    public long expenseTypeId;
    public long userId;
    public List<ExpenseOtherPaymentMethodModel> otherPaymentMethodModels;
}
