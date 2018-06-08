package dagger;

import android.content.Context;

import io.dojogeek.adminibot.daos.IncomeDaoImpl;
import io.dojogeek.adminibot.presenters.PaymentMethodsPresenter;
import io.dojogeek.adminibot.presenters.PaymentMethodsPresenterImpl;
import io.dojogeek.adminibot.views.PaymentMethods;
import io.dojogeek.adminibot.views.PaymentMethodsActivity;

@Module
public class AdminiBotModule {

    private Context mContext;
    private PaymentMethods mPaymentMethods;

    public AdminiBotModule(PaymentMethods paymentMethods) {
        mPaymentMethods = paymentMethods;
        mContext = ((PaymentMethodsActivity) paymentMethods);
    }

    @Provides
    @AdminBotScope
    PaymentMethodsPresenter providePaymentMethodsPresenter(IncomeDaoImpl incomeDao) {

        return new PaymentMethodsPresenterImpl(mPaymentMethods, incomeDao);

    }

    @Provides
    @AdminBotScope
    Context provideApplicationContext() {

        return mContext;

    }

}
