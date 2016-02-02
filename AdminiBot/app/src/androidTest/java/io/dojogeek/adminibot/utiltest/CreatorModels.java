package io.dojogeek.adminibot.utiltest;

import io.dojogeek.adminibot.R;
import io.dojogeek.adminibot.components.PaymentMethod;
import io.dojogeek.adminibot.models.ExpenseTypeModel;
import io.dojogeek.adminibot.models.PaymentMethodModel;
import io.dojogeek.adminibot.models.TypePaymentMethodModel;
import io.dojogeek.adminibot.models.UserModel;

public class CreatorModels {

    public static UserModel createUserModel() {

        UserModel userModel = new UserModel();
        userModel.name = "Jacob";
        userModel.lastName = "Guzman";
        userModel.email = "jgacosta@dojogeek.io";

        return userModel;
    }

    public static TypePaymentMethodModel createTypePaymentMethodModel() {

        TypePaymentMethodModel typePaymentMethodModel = createTypePaymentMethodModel(R.string.payment_methods_food_coupons,
                R.string.payment_methods_food_coupons_description);

        return typePaymentMethodModel;
    }

    public static TypePaymentMethodModel createTypePaymentMethodModel(int name, int description) {
        TypePaymentMethodModel typePaymentMethodModel = new TypePaymentMethodModel();
        typePaymentMethodModel.name = name;
        typePaymentMethodModel.description = description;

        return typePaymentMethodModel;
    }

    public static TypePaymentMethodModel[] createTypesPaymentMethods() {

        TypePaymentMethodModel[] typesPaymentMethods = {createTypePaymentMethodModel(R.string.payment_methods_food_coupons,
                R.string.payment_methods_food_coupons_description), createTypePaymentMethodModel(R.string.payment_methods_cash,
                R.string.payment_methods_cash_description), createTypePaymentMethodModel(R.string.payment_methods_cheque,
                R.string.payment_methods_cheque_description)};

        return typesPaymentMethods;
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

    public static PaymentMethodModel createPaymentMethodModel() {

        PaymentMethodModel paymentMethodModel = createPaymentMethodModel("Debt payment", "AD7689832D", 1, 1650, 4);

        return paymentMethodModel;
    }

    public static PaymentMethodModel createPaymentMethodModel(String name, String reference,
                                                              long typePaymentMethodId,
                                                              double availableCredit, int userId) {

        PaymentMethodModel paymentMethod = new PaymentMethodModel();
        paymentMethod.name = name;
        paymentMethod.reference = reference;
        paymentMethod.typePaymentMethod = typePaymentMethodId;
        paymentMethod.availabreCredit = availableCredit;
        paymentMethod.userId = userId;

        return paymentMethod;
    }
}
