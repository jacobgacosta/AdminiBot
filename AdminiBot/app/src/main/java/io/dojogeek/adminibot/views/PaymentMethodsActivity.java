package io.dojogeek.adminibot.views;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

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
import io.dojogeek.adminibot.exceptions.ArgumentException;
import io.dojogeek.adminibot.presenters.PaymentMethodsPresenter;
import io.dojogeek.adminibot.utils.LaunchIntents;

public class PaymentMethodsActivity extends BaseActivity implements PaymentMethods, View.OnClickListener,
        AdapterView.OnItemClickListener {

    @Inject
    public PaymentMethodsPresenter paymentMethodsPresenter;

    private LinearLayout mContainerPaymentMethods;

    private ListView mPaymentMethods;

    public static final String TAG = "PaymentMethodsActivity";

    @Override
    public void prepareView(List<TypePaymentMethodEnum> typePaymentMethodEnumList) {

        if (typePaymentMethodEnumList.isEmpty()) {

            launchAddPaymentMethodOption();

        } else {
            preparePaymentMethods(typePaymentMethodEnumList);
        }

    }

    @Override
    public void onClick(View view) {

        int id = view.getId();

        switch (id) {
            case R.id.add_payment_method:
                launchAddPaymentMethodOption();
                break;
            default:
                Log.v(TAG, "No events for this view");
                break;
        }
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

        Map<String, Serializable> flags = new HashMap<>();
        flags.put("EXTRA_SESSION_ID", (TypePaymentMethodEnum) view.getTag());

        try {
            LaunchIntents.launchIntentWithExtraValues(this, MyPaymentMethodsActivity.class, flags);
        } catch (ArgumentException e) {
            e.printStackTrace();
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
        mPaymentMethods = (ListView) mContainerPaymentMethods.findViewById(R.id.payment_methods);
    }

    @Override
    protected void addListenersToViews() {
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

    private void launchAddPaymentMethodOption() {

        LaunchIntents.launchIntentClearTop(this, AddPaymentMethodActivity.class);
    }

    private void preparePaymentMethods(List<TypePaymentMethodEnum> typePaymentMethodEnumList) {

        setTitle(R.string.title_activity_choice_payment_method);

        addFooterToListView();

        setListAdapter(typePaymentMethodEnumList);

    }

    private void addFooterToListView() {

        LayoutInflater inflater = getLayoutInflater();

        ViewGroup viewGroup = (ViewGroup) inflater.inflate(R.layout.activity_add_new_payment_method,
                mPaymentMethods, false);

        TextView notificationLabel = (TextView) viewGroup.findViewById(R.id.notification_label);
        notificationLabel.setVisibility(View.GONE);

        Button addPaymentMethod = (Button) viewGroup.findViewById(R.id.add_payment_method);
        addPaymentMethod.setOnClickListener(this);

        mPaymentMethods.addFooterView(viewGroup, null, false);
    }

    private void setListAdapter(List<TypePaymentMethodEnum> typePaymentMethodEnumList) {

        PaymentMethodAdapter paymentMethodAdapter = new PaymentMethodAdapter(this, typePaymentMethodEnumList);

        mPaymentMethods.setAdapter(paymentMethodAdapter);
    }


}
