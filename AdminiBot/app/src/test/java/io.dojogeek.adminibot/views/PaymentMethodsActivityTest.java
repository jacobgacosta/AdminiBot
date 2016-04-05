package io.dojogeek.adminibot.views;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.AppCompatDelegate;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.runners.MockitoJUnitRunner;
import org.powermock.core.classloader.annotations.PrepareForTest;

import java.util.ArrayList;
import java.util.List;

import dagger.AdminiBotComponent;
import dagger.AdminiBotModule;
import dagger.AppComponent;
import dagger.DaggerAppComponent;
import io.dojogeek.adminibot.R;
import io.dojogeek.adminibot.adapters.PaymentMethodAdapter;
import io.dojogeek.adminibot.enums.TypePaymentMethodEnum;
import io.dojogeek.adminibot.factory.ModelsFactory;
import io.dojogeek.adminibot.presenters.ExpenseCreationPresenter;
import io.dojogeek.adminibot.presenters.PaymentMethodsPresenter;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;
import static org.powermock.api.support.membermodification.MemberModifier.*;

@RunWith(MockitoJUnitRunner.class)
public class PaymentMethodsActivityTest {

    @Mock
    private LinearLayout mContainerPaymentMethods;

    @Mock
    private ListView mListPaymentMethods;

    @Mock
    private LinearLayout mAddPaymentMethod;

    @Mock
    private PaymentMethodsPresenter mPaymentMethodsPresenter;

    @InjectMocks
    private PaymentMethodsActivity mPaymentMethods = new PaymentMethodsActivity();

    @Test
    public void testShowTypesPaymentMethods_listWithPaymentMethods() {

        List<TypePaymentMethodEnum> typePaymentMethodEnumList = ModelsFactory.createTypePaymentMethodEnumList();

        when(mContainerPaymentMethods.findViewById(R.id.payment_methods)).thenReturn(mListPaymentMethods);

        mPaymentMethods.showTypesPaymentMethods(typePaymentMethodEnumList);

        verify(mContainerPaymentMethods).findViewById(R.id.payment_methods);
        verify(mListPaymentMethods).setVisibility(View.VISIBLE);
        verify(mListPaymentMethods).setAdapter((PaymentMethodAdapter) anyObject());
    }

    @Test
    public void testShowTypesPaymentMethods_emptyListPaymentMethods() {

        List<TypePaymentMethodEnum> typePaymentMethodEnumList = new ArrayList<>();

        when(mContainerPaymentMethods.findViewById(R.id.add_payment_method)).thenReturn(mAddPaymentMethod);

        mPaymentMethods.showTypesPaymentMethods(typePaymentMethodEnumList);

        verify(mContainerPaymentMethods).findViewById(R.id.add_payment_method);
        verify(mAddPaymentMethod).setVisibility(View.VISIBLE);

    }

}
