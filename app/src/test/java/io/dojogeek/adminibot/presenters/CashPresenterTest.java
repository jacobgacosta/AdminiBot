package io.dojogeek.adminibot.presenters;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import io.dojogeek.adminibot.daos.PaymentMethodDaoImpl;
import io.dojogeek.adminibot.views.Cash;

import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class CashPresenterTest {

    private static long SUCCESS_INSERTION = 1;

    @Mock
    private Cash mCashActivity;

    @Mock
    private PaymentMethodDaoImpl mOtherPaymentMethodDaoImpl;

    @InjectMocks
    private CashPresenter mCashPresenter = new CashPresenterImpl(mCashActivity, mOtherPaymentMethodDaoImpl);

    @Test
    public void testCreateCash_successfulCreation() {

        //PaymentMethodModel otherPaymentMethodModel= ModelsFactory.createOtherPaymentMethodModel();

//        when(mOtherPaymentMethodDaoImpl.create(otherPaymentMethodModel)).thenReturn(SUCCESS_INSERTION);

        //mCashPresenter.createCash(otherPaymentMethodModel);

        //verify(mOtherPaymentMethodDaoImpl).create(otherPaymentMethodModel);
        verify(mCashActivity).notifySuccessfulInsertion();
        verify(mCashActivity).returnToMyPaymentsMethods();
        verify(mCashActivity, never()).notifyErrorInsertion();
    }

    @Test
    public void testCreateCash_errorInsertion_withSQLException() {

        //7PaymentMethodModel otherPaymentMethodModel = ModelsFactory.createOtherPaymentMethodModel();

//        when(mOtherPaymentMethodDaoImpl.create(otherPaymentMethodModel)).thenThrow(new SQLException());

  //      mCashPresenter.createCash(otherPaymentMethodModel);

    //    verify(mOtherPaymentMethodDaoImpl).create(otherPaymentMethodModel);
        verify(mCashActivity, never()).notifySuccessfulInsertion();
        verify(mCashActivity, never()).returnToMyPaymentsMethods();
        verify(mCashActivity).notifyErrorInsertion();
    }

    @Test
    public void testUnnusedView_closeConnections() {
        mCashPresenter.unnusedView();
        verify(mOtherPaymentMethodDaoImpl).closeConnection();
    }
}
