package io.dojogeek.adminibot.views;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.List;

import javax.inject.Inject;

import dagger.AdminiBotComponent;
import dagger.AdminiBotModule;
import dagger.AppComponent;
import io.dojogeek.adminibot.R;
import io.dojogeek.adminibot.adapters.PaymentMethodAdapter;
import io.dojogeek.adminibot.enums.TypePaymentMethodEnum;
import io.dojogeek.adminibot.presenters.PaymentMethodsPresenter;
import io.dojogeek.adminibot.utils.LaunchIntents;

public class PaymentMethodsActivity extends BaseActivity implements PaymentMethods,
        View.OnClickListener, AdapterView.OnItemClickListener {

    public static final String TAG = "PaymentMethodsActivity";

    @Inject
    public PaymentMethodsPresenter mPaymentMethodsPresenter;

    private ListView mPaymentMethodsList;

    private TextView mNoPaymentMethodsLabel;

    private FloatingActionButton mAddNewPaymentMethodButton;

    @Override
    public void showRegistered(List<TypePaymentMethodEnum> paymentMethods) {

        if (paymentMethods.isEmpty()) {

            setTitle(R.string.add_payment_method);

            mNoPaymentMethodsLabel.setVisibility(View.VISIBLE);

            mPaymentMethodsList.setVisibility(View.GONE);

            return;
        }

        setTitle(R.string.make_payment_with);

        listPaymentMethods(paymentMethods);
    }

    @Override
    public void onClick(View view) {

        int id = view.getId();

        switch (id) {
            case R.id.add_payment_method:
                LaunchIntents.launchIntentClearTop(this, AddPaymentMethodActivity.class);
                break;
            default:
                Log.v(TAG, "No events for this view");
                break;
        }
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

        TypePaymentMethodEnum typePaymentMethodEnum = (TypePaymentMethodEnum) view.getTag();

        switch (typePaymentMethodEnum) {
            case CREDIT_CARD:
                LaunchIntents.launchIntentClearTop(this, MyCreditCardsActivity.class);
                break;
            case CASH:
                LaunchIntents.launchIntentClearTop(this, MyCashActivity.class);
                break;
            case FOOD_COUPONS:
                LaunchIntents.launchIntentClearTop(this, MyFoodCouponsActivity.class);
                break;
            default:
                Log.v(this.getClass().getName(), "No operations for this event");
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
        RelativeLayout mContainerPaymentMethods = (RelativeLayout) findViewById(R.id.container_payment_methods);
        mPaymentMethodsList = (ListView) mContainerPaymentMethods.findViewById(R.id.lst_payment_methods);
        mNoPaymentMethodsLabel = (TextView) findViewById(R.id.txv_notification_label);
        mAddNewPaymentMethodButton = (FloatingActionButton) findViewById(R.id.btn_add_payment_method);
    }

    @Override
    protected void addListenersToViews() {

        mAddNewPaymentMethodButton.setOnClickListener(this);
        mPaymentMethodsList.setOnItemClickListener(this);
    }

    @Override
    protected void loadDataView() {

        mPaymentMethodsPresenter.loadAvailablePaymentMethods();
    }

    @Override
    protected int getLayoutActivity() {
        return R.layout.activity_payment_methods;
    }

    @Override
    protected void closeConnections() {
        mPaymentMethodsPresenter.closeConnections();
    }

    private void listPaymentMethods(List<TypePaymentMethodEnum> typePaymentMethodEnumList) {

        PaymentMethodAdapter paymentMethodAdapter = new PaymentMethodAdapter(this,
                typePaymentMethodEnumList);

        mPaymentMethodsList.setAdapter(paymentMethodAdapter);
    }
}
