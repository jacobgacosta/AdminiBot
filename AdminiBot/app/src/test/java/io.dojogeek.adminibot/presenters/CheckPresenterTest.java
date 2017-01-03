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
    private OtherPaymentMethodDaoImpl mOtherPaymentMethodDao;

    @InjectMocks
    private CheckPresenter mCheckPresenter = new CheckPresenterImpl(mCheckActivity, mOtherPaymentMethodDao);

    @Test
    public void testCreateCheck_successfulCreation() {

        OtherPaymentMethodModel otherPaymentMethodModel = ModelsFactory.createOtherPaymentMethodModel();

        when(mOtherPaymentMethodDao.createOtherPaymentMethod(otherPaymentMethodModel)).
                thenReturn(SUCCESS_INSERTION);

        mCheckPresenter.createCheck(otherPaymentMethodModel);

        verify(mOtherPaymentMethodDao).createOtherPaymentMethod(otherPaymentMethodModel);
        verify(mCheckActivity).notifySuccessfulInsertion();
        verify(mCheckActivity).returnToMyPaymentsMethods();
        verify(mCheckActivity, never()).notifyErrorInsertion();
    }

    @Test
    public void testCreateCheck_errorInsertion_withSQLException() {

        OtherPaymentMethodModel otherPaymentMethodModel = ModelsFactory.createOtherPaymentMethodModel();

        when(mOtherPaymentMethodDao.createOtherPaymentMethod(otherPaymentMethodModel)).thenThrow(new SQLException());

        mCheckPresenter.createCheck(otherPaymentMethodModel);

        verify(mOtherPaymentMethodDao).createOtherPaymentMethod(otherPaymentMethodModel);
        verify(mCheckActivity, never()).notifySuccessfulInsertion();
        verify(mCheckActivity, never()).returnToMyPaymentsMethods();
        verify(mCheckActivity).notifyErrorInsertion();
    }

    @Test
    public void testUnnusedView_closeConnections() {
        mCheckPresenter.unnusedView();
        verify(mOtherPaymentMethodDao).closeConnection();
    }
}
