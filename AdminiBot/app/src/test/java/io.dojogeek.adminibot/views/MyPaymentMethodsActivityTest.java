package io.dojogeek.adminibot.views;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.modules.junit4.PowerMockRunner;

import io.dojogeek.adminibot.R;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

@RunWith(PowerMockRunner.class)
public class MyPaymentMethodsActivityTest {

    private MyPaymentMethodsActivity  mMyPaymentMethodsActivity = new MyPaymentMethodsActivity();

    @Test
    public void testLoadDataView_listPaymentMethods() {

        mMyPaymentMethodsActivity.loadDataView();


    }

    @Test
    public void testGetLayoutActivity_returnCorrectLayout() {

        int actualLayoutActivity = mMyPaymentMethodsActivity.getLayoutActivity();

        assertThat(actualLayoutActivity, is(R.layout.activity_my_payment_methods));
    }
}
