package io.dojogeek.adminibot.presenters;

import android.database.SQLException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import io.dojogeek.adminibot.daos.PaymentMethodDaoImpl;
import io.dojogeek.adminibot.factory.ModelsFactory;
import io.dojogeek.adminibot.models.PaymentMethodModel;
import io.dojogeek.adminibot.views.FoodCoupons;

import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class FoodCouponPresenterTest {

    private static long SUCCESS_INSERTION = 1;

    @Mock
    private FoodCoupons mFoodCoupons;

    @Mock
    private PaymentMethodDaoImpl mOtherPaymentMethodDaoImpl;

    @InjectMocks
    private FoodCouponPresenter mFoodCouponPresenter = new FoodCouponPresenterImpl(mFoodCoupons,
            mOtherPaymentMethodDaoImpl);

    @Test
    public void testCreateFoodCoupon_successfulCreation() {

        PaymentMethodModel otherPaymentMethodModel = ModelsFactory.createOtherPaymentMethodModel();

        when(mOtherPaymentMethodDaoImpl.create(otherPaymentMethodModel)).
                thenReturn(SUCCESS_INSERTION);

        mFoodCouponPresenter.createFoodCoupon(otherPaymentMethodModel);

        verify(mOtherPaymentMethodDaoImpl).create(otherPaymentMethodModel);
        verify(mFoodCoupons).notifySuccessfulInsertion();
        verify(mFoodCoupons).returnToMyPaymentsMethods();
        verify(mFoodCoupons, never()).notifyErrorInsertion();
    }

    @Test
    public void testCreateFoodCoupon_errorInsertion_withSQLException() {

        PaymentMethodModel otherPaymentMethodModel = ModelsFactory.createOtherPaymentMethodModel();

        when(mOtherPaymentMethodDaoImpl.create(otherPaymentMethodModel)).thenThrow(new SQLException());

        mFoodCouponPresenter.createFoodCoupon(otherPaymentMethodModel);

        verify(mOtherPaymentMethodDaoImpl).create(otherPaymentMethodModel);
        verify(mFoodCoupons, never()).notifySuccessfulInsertion();
        verify(mFoodCoupons, never()).returnToMyPaymentsMethods();
        verify(mFoodCoupons).notifyErrorInsertion();
    }

    @Test
    public void testUnnusedView_closeConnections() {
        mFoodCouponPresenter.unnusedView();
        verify(mOtherPaymentMethodDaoImpl).closeConnection();;
    }
}
