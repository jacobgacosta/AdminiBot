package io.dojogeek.adminibot.presenters;

import android.database.SQLException;

import io.dojogeek.adminibot.daos.PaymentMethodDao;
import io.dojogeek.adminibot.daos.PaymentMethodDaoImpl;
import io.dojogeek.adminibot.models.PaymentMethodModel;
import io.dojogeek.adminibot.views.FoodCoupons;

public class FoodCouponPresenterImpl implements FoodCouponPresenter {

    private FoodCoupons mFoodCoupons;
    private PaymentMethodDao mPaymentMethodDao;

    public FoodCouponPresenterImpl(FoodCoupons foodCoupons, PaymentMethodDao paymentMethodDao) {
        mFoodCoupons = foodCoupons;
        mPaymentMethodDao = paymentMethodDao;
    }

    @Override
    public void createFoodCoupon(PaymentMethodModel otherPaymentMethodModel) {

        try {

            mPaymentMethodDao.create(otherPaymentMethodModel);
            mFoodCoupons.notifySuccessfulInsertion();
            mFoodCoupons.returnToMyPaymentsMethods();

        } catch (SQLException exception) {

            mFoodCoupons.notifyErrorInsertion();
        }

    }

    @Override
    public void unnusedView() {
        ((PaymentMethodDaoImpl) mPaymentMethodDao).closeConnection();
    }
}
