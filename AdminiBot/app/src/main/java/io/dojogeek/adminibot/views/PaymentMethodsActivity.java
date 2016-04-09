package io.dojogeek.adminibot.views;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import dagger.AdminiBotComponent;
import dagger.AdminiBotModule;
import dagger.AppComponent;
import io.dojogeek.adminibot.R;
import io.dojogeek.adminibot.adapters.PaymentMethodAdapter;
import io.dojogeek.adminibot.enums.TypePaymentMethodEnum;
import io.dojogeek.adminibot.presenters.PaymentMethodsPresenter;
import io.dojogeek.adminibot.utils.LaunchIntents;

public class PaymentMethodsActivity extends BaseActivity implements PaymentMethods, View.OnClickListener,
        AdapterView.OnItemClickListener {

    @Inject
    public PaymentMethodsPresenter paymentMethodsPresenter;

    private LinearLayout mContainerPaymentMethods;

    private LinearLayout mAddPaymentMethodOption;

    private ListView mPaymentMethods;

    private Button mAddPaymentMethodButton;

    public static final String TAG = "PaymentMethodsActivity";

    @Override
    public void showTypesPaymentMethods(List<TypePaymentMethodEnum> typePaymentMethodEnumList) {

        if (typePaymentMethodEnumList.isEmpty()) {
            showAddPaymentMethodOption();
        } else {
            listPaymentMethods(typePaymentMethodEnumList);
        }

    }

    @Override
    public void onClick(View view) {

        int id = view.getId();

        switch (id) {
            case R.id.add_payment_method:
                LaunchIntents.launchIntentClearTop(this, PaymentMethodsActivity.class);
                break;
            default:
                Log.v(TAG, "No events for this view");
                break;
        }
    }

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
        mContainerPaymentMethods = (LinearLayout) findViewById(R.id.container_payment_methods);
        mAddPaymentMethodOption = (LinearLayout) mContainerPaymentMethods.findViewById(R.id.add_payment_method_container);
        mPaymentMethods = (ListView) mContainerPaymentMethods.findViewById(R.id.payment_methods);
        mAddPaymentMethodButton = (Button) mContainerPaymentMethods.findViewById(R.id.add_payment_method);
    }

    @Override
    protected void addListenersToViews() {
        mAddPaymentMethodButton.setOnClickListener(this);
        mPaymentMethods.setOnItemClickListener(this);
    }

    @Override
    protected void loadDataView() {

        paymentMethodsPresenter.loadPaymentMethods();

    }

    @Override
    protected int getLayoutActivity() {
        return R.layout.activity_payment_methods;
    }

    @Override
    protected void closeConnections() {
        paymentMethodsPresenter.unnusedView();
    }

    private void showAddPaymentMethodOption() {
        setTitle(R.string.title_activity_add_payment_method);
        mAddPaymentMethodOption.setVisibility(View.VISIBLE);
        mPaymentMethods.setVisibility(View.GONE);
    }

    private void listPaymentMethods(List<TypePaymentMethodEnum> typePaymentMethodEnumList) {
        setTitle(R.string.title_activity_choice_payment_method);
        mPaymentMethods.setVisibility(View.VISIBLE);
        mAddPaymentMethodOption.setVisibility(View.GONE);

        PaymentMethodAdapter paymentMethodAdapter = new PaymentMethodAdapter(this, typePaymentMethodEnumList);

        mPaymentMethods.setAdapter(paymentMethodAdapter);
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

        Map<String, Serializable> flags = new HashMap<>();
        flags.put("EXTRA_SESSION_ID", (TypePaymentMethodEnum) view.getTag());

        LaunchIntents.launchIntentWithExtraValues(this, MyPaymentMethodsActivity.class, flags);

    }
}
