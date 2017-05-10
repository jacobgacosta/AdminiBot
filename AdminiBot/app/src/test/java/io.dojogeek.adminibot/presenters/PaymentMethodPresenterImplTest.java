package io.dojogeek.adminibot.presenters;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import java.util.ArrayList;
import java.util.List;

import io.dojogeek.adminibot.daos.BankCardDaoImpl;
import io.dojogeek.adminibot.daos.IncomeTypeTypePaymentMethodDaoImpl;
import io.dojogeek.adminibot.daos.PaymentMethodDaoImpl;
import io.dojogeek.adminibot.enums.TypePaymentMethodEnum;
import io.dojogeek.adminibot.views.PaymentMethods;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(PowerMockRunner.class)
@PrepareForTest({PaymentMethodsPresenterImpl.class})
public class PaymentMethodPresenterImplTest {

    @Mock
    private PaymentMethods mActivity;

    @Mock
    private BankCardDaoImpl mBankCardDaoImpl;

    @Mock
    private PaymentMethodDaoImpl mPaymentMethodDaoImpl;

    @Mock
    private IncomeTypeTypePaymentMethodDaoImpl mIncomeTypePaymentMethodDaoImpl;

    @InjectMocks
    private PaymentMethodsPresenter mPresenter = new PaymentMethodsPresenterImpl(mActivity,
            mIncomeTypePaymentMethodDaoImpl, mBankCardDaoImpl, mPaymentMethodDaoImpl);

    @Test
    public void testAvailablePaymentMethods_onlyOtherPaymentMethods() {

        List<TypePaymentMethodEnum> paymentMethods = new ArrayList<>();
        paymentMethods.add(TypePaymentMethodEnum.CASH);
        paymentMethods.add(TypePaymentMethodEnum.FOOD_COUPONS);

        when(mPaymentMethodDaoImpl.getRegisteredTypes()).thenReturn(paymentMethods);

        mPresenter.loadAvailablePaymentMethods();

        verify(mPaymentMethodDaoImpl).getRegisteredTypes();
        verify(mActivity).showRegistered(paymentMethods);
    }

    @Test
    public void testAvailablePaymentMethods_onlyBankCards() {

        List<TypePaymentMethodEnum> bankCards = new ArrayList<>();
        bankCards.add(TypePaymentMethodEnum.CREDIT_CARD);
        bankCards.add(TypePaymentMethodEnum.DEBIT_CARD);

        when(mBankCardDaoImpl.getRegisteredTypes()).thenReturn(bankCards);

        mPresenter.loadAvailablePaymentMethods();

        verify(mBankCardDaoImpl).getRegisteredTypes();
        verify(mActivity).showRegistered(bankCards);
    }

    @Test
    public void testCloseConnections() {

        mPresenter.closeConnections();

        verify(mBankCardDaoImpl).closeConnection();
        verify(mPaymentMethodDaoImpl).closeConnection();
        verify(mIncomeTypePaymentMethodDaoImpl).closeConnection();
    }
}
