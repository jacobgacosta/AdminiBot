package io.dojogeek.adminibot.presenters;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.powermock.modules.junit4.PowerMockRunner;

import java.math.BigDecimal;

import io.dojogeek.adminibot.daos.IncomeDao;
import io.dojogeek.adminibot.models.IncomeModel;
import io.dojogeek.adminibot.views.PaymentMethods;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


@RunWith(PowerMockRunner.class)
public class PaymentMethodPresenterImplTest {

    private PaymentMethods mPaymentMethods = mock(PaymentMethods.class);
    private IncomeDao mIncomeDao = mock(IncomeDao.class);

    @InjectMocks
    private PaymentMethodsPresenterImpl mPresenter = new PaymentMethodsPresenterImpl(mPaymentMethods, mIncomeDao);

    @Test
    public void testRegisterIncome_correctFlow() {

        IncomeModel income = new IncomeModel();
        income.setName("Income test");
        income.setTotalAmount(new BigDecimal(0.0));

        when(mIncomeDao.createIncome(income)).thenReturn(1L);

        //mPresenter.registerIncome(income);

        verify(mIncomeDao).createIncome(income);
        //verify(mPaymentMethods).stashIncomeId(1L);

    }

}
