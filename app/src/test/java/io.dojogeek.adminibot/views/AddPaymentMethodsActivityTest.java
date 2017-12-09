package io.dojogeek.adminibot.views;

import android.content.Intent;
import android.view.View;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.powermock.modules.junit4.PowerMockRunner;

import java.math.BigDecimal;

import io.dojogeek.adminibot.R;
import io.dojogeek.adminibot.dtos.DebitCardDto;
import io.dojogeek.adminibot.dtos.IncomeDto;
import io.dojogeek.adminibot.presenters.PaymentMethodsPresenter;

import static org.mockito.Mockito.when;

@RunWith(PowerMockRunner.class)
public class AddPaymentMethodsActivityTest {

    @Mock
    private View mView;

    @Mock
    public PaymentMethodsPresenter presenter;

    @Mock
    public Intent intent;

    @InjectMocks
    @Spy
    private PaymentMethodsActivity mActivity = new PaymentMethodsActivity();

    @Test
    public void testClickSaveButton_registerIncomeWithAllPaymentMethods() {
        when(mView.getId()).thenReturn(R.id.button_save_payment_methods);

        DebitCardDto debitCard = new DebitCardDto();

        IncomeDto income = new IncomeDto();
        income.setTotalCash(new BigDecimal(19500));
        income.setTotalFoodCoupons(new BigDecimal(2000));
        income.setTotalAmount(new BigDecimal(17500));
        income.setName("Income Test");
        income.setDebitCard(debitCard);

        when(intent.getSerializableExtra(income.getName())).thenReturn(income);
        when(mActivity.getIntent()).thenReturn(intent);


        mActivity.onClick(mView);

    }

}
