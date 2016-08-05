package io.dojogeek.adminibot.presenters;

import android.content.Context;
import android.database.SQLException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import io.dojogeek.adminibot.daos.CashDao;
import io.dojogeek.adminibot.factory.ModelsFactory;
import io.dojogeek.adminibot.models.CashModel;
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
    private CashDao mCashDao;

    @InjectMocks
    private CashPresenter cashPresenter = new CashPresenterImpl(mCashActivity, mCashDao);

    @Test
    public void testCreateCash_successfulCreation() {

        CashModel cashModel = ModelsFactory.createCashModel();

        when(mCashDao.createCash(cashModel)).thenReturn(SUCCESS_INSERTION);

        cashPresenter.createCash(cashModel);

        verify(mCashDao).createCash(cashModel);
        verify(mCashActivity).notifySuccessfulInsertion();
        verify(mCashActivity).returnToMyPaymentsMethods();
        verify(mCashActivity, never()).notifyErrorInsertion();
    }

    @Test
    public void testCreateCash_errorInsertion_withSQLException() {

        CashModel cashModel = ModelsFactory.createCashModel();

        when(mCashDao.createCash(cashModel)).thenThrow(new SQLException());

        cashPresenter.createCash(cashModel);

        verify(mCashDao).createCash(cashModel);
        verify(mCashActivity, never()).notifySuccessfulInsertion();
        verify(mCashActivity, never()).returnToMyPaymentsMethods();
        verify(mCashActivity).notifyErrorInsertion();
    }
}
