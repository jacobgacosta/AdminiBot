package io.dojogeek.adminibot.views;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.Arrays;
import java.util.List;

import dagger.AdminiBotComponent;
import dagger.AdminiBotModule;
import dagger.AppComponent;
import io.dojogeek.adminibot.R;
import io.dojogeek.adminibot.adapters.PaymentMethodAdapter;
import io.dojogeek.adminibot.enums.TypePaymentMethodEnum;
import io.dojogeek.adminibot.utils.LaunchIntents;

public class AddPaymentMethodActivity extends BaseActivity implements AddPaymentMethod, AdapterView.OnItemClickListener {

    private ListView mPaymentMethods;

    private FloatingActionButton mAddNewPaymentMethodButton;

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
        mAddNewPaymentMethodButton = (FloatingActionButton) findViewById(R.id.add_payment_method);
    }

    @Override
    protected void addListenersToViews() {
        mPaymentMethods.setOnItemClickListener(this);
    }

    @Override
    protected void loadDataView() {

        setTitle(R.string.title_payment_methods);

        mAddNewPaymentMethodButton.setVisibility(View.GONE);

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

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        TypePaymentMethodEnum typePaymentMethodEnum = (TypePaymentMethodEnum) view.getTag();

        switch (typePaymentMethodEnum) {
            case CARD:
                LaunchIntents.launchIntentClearTop(this, CardCreationActivity.class);
                break;
            case CASH:
                LaunchIntents.launchIntentClearTop(this, CashActivity.class);
                break;
            /*case CHEQUE:
                LaunchIntents.launchIntentClearTop(this, CheckActivity.class);
                break;*/
            case FOOD_COUPONS:
                LaunchIntents.launchIntentClearTop(this, FoodCouponsActivity.class);
                break;
        }

    }
}
