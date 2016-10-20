package io.dojogeek.adminibot.presenters;

import android.database.SQLException;

import io.dojogeek.adminibot.daos.OtherPaymentMethodDao;
import io.dojogeek.adminibot.daos.OtherPaymentMethodDaoImpl;
import io.dojogeek.adminibot.models.OtherPaymentMethodModel;
import io.dojogeek.adminibot.views.FoodCoupons;

public class FoodCouponPresenterImpl implements FoodCouponPresenter {

    private FoodCoupons mFoodCoupons;
    private OtherPaymentMethodDao mOtherPaymentMethodDao;

    public FoodCouponPresenterImpl(FoodCoupons foodCoupons, OtherPaymentMethodDao otherPaymentMethodDao) {
        mFoodCoupons = foodCoupons;
        mOtherPaymentMethodDao = otherPaymentMethodDao;
    }

    @Override
    public void createFoodCoupon(OtherPaymentMethodModel otherPaymentMethodModel) {

        try {

            mOtherPaymentMethodDao.createOtherPaymentMethod(otherPaymentMethodModel);
            mFoodCoupons.notifySuccessfulInsertion();
            mFoodCoupons.returnToMyPaymentsMethods();

        } catch (SQLException exception) {

            mFoodCoupons.notifyErrorInsertion();
        }

    }

    @Override
    public void unnusedView() {
        ((OtherPaymentMethodDaoImpl) mOtherPaymentMethodDao).closeConnection();
    }
}
