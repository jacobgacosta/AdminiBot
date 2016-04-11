package io.dojogeek.adminibot.views;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.List;

import dagger.AdminiBotComponent;
import dagger.AdminiBotModule;
import dagger.AppComponent;
import io.dojogeek.adminibot.R;
import io.dojogeek.adminibot.adapters.PaymentMethodAdapter;
import io.dojogeek.adminibot.enums.TypePaymentMethodEnum;

public class AddPaymentMethodActivity extends BaseActivity implements AddPaymentMethod {

    private ListView mPaymentMethods;

    private LinearLayout mAddPaymentMethod;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void setupComponent(AppComponent appComponent) {
        AdminiBotComponent adminiBotComponent = appComponent.plus(new AdminiBotModule(this));
        adminiBotComponent.inject(this);
    }

    @Override
    protected void loadViews() {
        mPaymentMethods = (ListView) findViewById(R.id.payment_methods);
        mAddPaymentMethod = (LinearLayout) findViewById(R.id.add_payment_method_container);
    }

    @Override
    protected void addListenersToViews() {

    }

    @Override
    protected void loadDataView() {

        mAddPaymentMethod.setVisibility(View.GONE);
        mPaymentMethods.setVisibility(View.VISIBLE);

        List<TypePaymentMethodEnum> paymentMethodEnumList = Arrays.asList(TypePaymentMethodEnum.values());

        PaymentMethodAdapter paymentMethodAdapter = new PaymentMethodAdapter(this, paymentMethodEnumList);

        mPaymentMethods.setAdapter(paymentMethodAdapter);
    }

    @Override
    protected int getLayoutActivity() {
        return R.layout.activity_payment_methods;
    }

    @Override
    protected void closeConnections() {

    }
}
