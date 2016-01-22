package io.dojogeek.adminibot.utiltest;

import io.dojogeek.adminibot.R;
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

        PaymentMethodModel paymentMethodModel = createPaymentMethodModel(R.string.payment_methods_credit_card,
                R.string.payment_methods_credit_card_description);

        return paymentMethodModel;
    }

    public static PaymentMethodModel createPaymentMethodModel(int name, int description) {
        PaymentMethodModel paymentMethodModel = new PaymentMethodModel();
        paymentMethodModel.name = name;
        paymentMethodModel.description = description;

        return paymentMethodModel;
    }
}
