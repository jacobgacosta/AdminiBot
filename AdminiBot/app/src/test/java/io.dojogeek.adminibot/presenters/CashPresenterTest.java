package io.dojogeek.adminibot.presenters;

import android.content.Context;
import android.database.SQLException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import io.dojogeek.adminibot.daos.CashDao;
import io.dojogeek.adminibot.daos.OtherPaymentMethodDao;
import io.dojogeek.adminibot.factory.ModelsFactory;
import io.dojogeek.adminibot.models.CashModel;
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
    private OtherPaymentMethodDao mOtherPaymentMethodDao;

    @InjectMocks
    private CashPresenter cashPresenter = new CashPresenterImpl(mCashActivity, mOtherPaymentMethodDao);

    @Test
    public void testCreateCash_successfulCreation() {

        OtherPaymentMethodModel otherPaymentMethodModel= ModelsFactory.createOtherPaymentMethodModel();

        when(mOtherPaymentMethodDao.createOtherPaymentMethod(otherPaymentMethodModel)).thenReturn(SUCCESS_INSERTION);

        cashPresenter.createCash(otherPaymentMethodModel);

        verify(mOtherPaymentMethodDao).createOtherPaymentMethod(otherPaymentMethodModel);
        verify(mCashActivity).notifySuccessfulInsertion();
        verify(mCashActivity).returnToMyPaymentsMethods();
        verify(mCashActivity, never()).notifyErrorInsertion();
    }

    @Test
    public void testCreateCash_errorInsertion_withSQLException() {

        OtherPaymentMethodModel otherPaymentMethodModel = ModelsFactory.createOtherPaymentMethodModel();

        when(mOtherPaymentMethodDao.createOtherPaymentMethod(otherPaymentMethodModel)).thenThrow(new SQLException());

        cashPresenter.createCash(otherPaymentMethodModel);

        verify(mOtherPaymentMethodDao).createOtherPaymentMethod(otherPaymentMethodModel);
        verify(mCashActivity, never()).notifySuccessfulInsertion();
        verify(mCashActivity, never()).returnToMyPaymentsMethods();
        verify(mCashActivity).notifyErrorInsertion();
    }
}
