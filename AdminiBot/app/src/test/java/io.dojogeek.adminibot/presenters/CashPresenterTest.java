package io.dojogeek.adminibot.presenters;

import android.database.SQLException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import io.dojogeek.adminibot.daos.OtherPaymentMethodDaoImpl;
import io.dojogeek.adminibot.factory.ModelsFactory;
import io.dojogeek.adminibot.models.OtherPaymentMethodModel;
import io.dojogeek.adminibot.views.Cash;

import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class CashPresenterTest {

    private static long SUCCESS_INSERTION = 1;

    @Mock
    private Cash mCashActivity;

    @Mock
    private OtherPaymentMethodDaoImpl mOtherPaymentMethodDao;

    @InjectMocks
    private CashPresenter mCashPresenter = new CashPresenterImpl(mCashActivity, mOtherPaymentMethodDao);

    @Test
    public void testCreateCash_successfulCreation() {

        OtherPaymentMethodModel otherPaymentMethodModel= ModelsFactory.createOtherPaymentMethodModel();

        when(mOtherPaymentMethodDao.createOtherPaymentMethod(otherPaymentMethodModel)).thenReturn(SUCCESS_INSERTION);

        mCashPresenter.createCash(otherPaymentMethodModel);

        verify(mOtherPaymentMethodDao).createOtherPaymentMethod(otherPaymentMethodModel);
        verify(mCashActivity).notifySuccessfulInsertion();
        verify(mCashActivity).returnToMyPaymentsMethods();
        verify(mCashActivity, never()).notifyErrorInsertion();
    }

    @Test
    public void testCreateCash_errorInsertion_withSQLException() {

        OtherPaymentMethodModel otherPaymentMethodModel = ModelsFactory.createOtherPaymentMethodModel();

        when(mOtherPaymentMethodDao.createOtherPaymentMethod(otherPaymentMethodModel)).thenThrow(new SQLException());

        mCashPresenter.createCash(otherPaymentMethodModel);

        verify(mOtherPaymentMethodDao).createOtherPaymentMethod(otherPaymentMethodModel);
        verify(mCashActivity, never()).notifySuccessfulInsertion();
        verify(mCashActivity, never()).returnToMyPaymentsMethods();
        verify(mCashActivity).notifyErrorInsertion();
    }

    @Test
    public void testUnnusedView_closeConnections() {
        mCashPresenter.unnusedView();
        verify(mOtherPaymentMethodDao).closeConnection();
    }
}
