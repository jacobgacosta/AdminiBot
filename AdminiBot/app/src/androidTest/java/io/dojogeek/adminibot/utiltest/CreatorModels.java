package io.dojogeek.adminibot.utiltest;

import io.dojogeek.adminibot.R;
import io.dojogeek.adminibot.models.ExpenseTypeModel;
import io.dojogeek.adminibot.models.PaymentMethodModel;
import io.dojogeek.adminibot.models.UserModel;

public class CreatorModels {

    public static UserModel createUserModel() {

        UserModel userModel = new UserModel();
        userModel.name = "Jacob";
        userModel.lastName = "Guzman";
        userModel.email = "jgacosta@dojogeek.io";

        return userModel;
    }

    public static PaymentMethodModel createPaymentMethodModel() {

        PaymentMethodModel paymentMethodModel = createPaymentMethodModel(R.string.payment_methods_food_coupons,
                R.string.payment_methods_food_coupons_description);

        return paymentMethodModel;
    }

    public static PaymentMethodModel createPaymentMethodModel(int name, int description) {
        PaymentMethodModel paymentMethodModel = new PaymentMethodModel();
        paymentMethodModel.name = name;
        paymentMethodModel.description = description;

        return paymentMethodModel;
    }

    public static PaymentMethodModel [] createPaymentMethods() {

        PaymentMethodModel  [] paymentMethods = {createPaymentMethodModel(R.string.payment_methods_food_coupons,
                R.string.payment_methods_food_coupons_description), createPaymentMethodModel(R.string.payment_methods_cash,
                R.string.payment_methods_cash_description), createPaymentMethodModel(R.string.payment_methods_cheque,
                R.string.payment_methods_cheque_description)};

        return paymentMethods;
    }

    public static ExpenseTypeModel createExpenseTypeModel() {
        ExpenseTypeModel expenseTypeModel = createExpenseTypeModel(R.string.expenses_types_drinks,
                R.string.expenses_types_foods);

        return expenseTypeModel;
    }

    public static ExpenseTypeModel createExpenseTypeModel(int name, int description) {

        ExpenseTypeModel expenseTypeModel = new ExpenseTypeModel();
        expenseTypeModel.name = name;
        expenseTypeModel.description = description;

        return expenseTypeModel;
    }
}
