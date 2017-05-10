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
import io.dojogeek.adminibot.views.Check;

import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class CheckPresenterTest {

    private static long SUCCESS_INSERTION = 1;

    @Mock
    private Check mCheckActivity;

    @Mock
    private PaymentMethodDaoImpl mOtherPaymentMethodDaoImpl;

    @InjectMocks
    private CheckPresenter mCheckPresenter = new CheckPresenterImpl(mCheckActivity, mOtherPaymentMethodDaoImpl);

    @Test
    public void testCreateCheck_successfulCreation() {

        PaymentMethodModel paymentMethod = ModelsFactory.createOtherPaymentMethodModel();

        when(mOtherPaymentMethodDaoImpl.create(paymentMethod)).
                thenReturn(SUCCESS_INSERTION);

        mCheckPresenter.createCheck(paymentMethod);

        verify(mOtherPaymentMethodDaoImpl).create(paymentMethod);
        verify(mCheckActivity).notifySuccessfulInsertion();
        verify(mCheckActivity).returnToMyPaymentsMethods();
        verify(mCheckActivity, never()).notifyErrorInsertion();
    }

    @Test
    public void testCreateCheck_errorInsertion_withSQLException() {

        PaymentMethodModel otherPaymentMethodModel = ModelsFactory.createOtherPaymentMethodModel();

        when(mOtherPaymentMethodDaoImpl.create(otherPaymentMethodModel)).thenThrow(new SQLException());

        mCheckPresenter.createCheck(otherPaymentMethodModel);

        verify(mOtherPaymentMethodDaoImpl).create(otherPaymentMethodModel);
        verify(mCheckActivity, never()).notifySuccessfulInsertion();
        verify(mCheckActivity, never()).returnToMyPaymentsMethods();
        verify(mCheckActivity).notifyErrorInsertion();
    }

    @Test
    public void testUnnusedView_closeConnections() {
        mCheckPresenter.unnusedView();
        verify(mOtherPaymentMethodDaoImpl).closeConnection();
    }
}
