package io.dojogeek.adminibot.views;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.LinearLayout;
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

public class PaymentMethodsActivity extends BaseActivity implements PaymentMethods, View.OnClickListener,
        AdapterView.OnItemClickListener {

    @Inject
    public PaymentMethodsPresenter paymentMethodsPresenter;

    private RelativeLayout mContainerPaymentMethods;

    private ListView mPaymentMethods;

    private TextView mNotificationLabel;

    private FloatingActionButton mAddNewPaymentMethodButton;

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
            case CARD:
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
        mContainerPaymentMethods = (RelativeLayout) findViewById(R.id.container_payment_methods);
        mPaymentMethods = (ListView) mContainerPaymentMethods.findViewById(R.id.payment_methods);
        mNotificationLabel = (TextView) findViewById(R.id.notification_label);
        mAddNewPaymentMethodButton = (FloatingActionButton) findViewById(R.id.add_payment_method);
    }

    @Override
    protected void addListenersToViews() {

        mAddNewPaymentMethodButton.setOnClickListener(this);
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

        mNotificationLabel.setVisibility(View.VISIBLE);
        mPaymentMethods.setVisibility(View.GONE);
    }

    private void preparePaymentMethods(List<TypePaymentMethodEnum> typePaymentMethodEnumList) {

        setTitle(R.string.title_activity_choice_payment_method);

        setListAdapter(typePaymentMethodEnumList);

    }

    private void setListAdapter(List<TypePaymentMethodEnum> typePaymentMethodEnumList) {

        PaymentMethodAdapter paymentMethodAdapter = new PaymentMethodAdapter(this, typePaymentMethodEnumList);

        mPaymentMethods.setAdapter(paymentMethodAdapter);
    }


}
