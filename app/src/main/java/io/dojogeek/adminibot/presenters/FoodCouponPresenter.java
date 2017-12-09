package io.dojogeek.adminibot.presenters;

import io.dojogeek.adminibot.models.PaymentMethodModel;

public interface FoodCouponPresenter {

    void createFoodCoupon(PaymentMethodModel paymentMethod);

    void unnusedView();

}
