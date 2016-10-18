package dagger;

import io.dojogeek.adminibot.views.AddNewPaymentMethodActivity;
import io.dojogeek.adminibot.views.AddPaymentMethodActivity;
import io.dojogeek.adminibot.views.CardCreationActivity;
import io.dojogeek.adminibot.views.CashActivity;
import io.dojogeek.adminibot.views.CheckActivity;
import io.dojogeek.adminibot.views.CreditCardActivity;
import io.dojogeek.adminibot.views.FoodCouponsActivity;
import io.dojogeek.adminibot.views.MyPaymentMethodsActivity;
import io.dojogeek.adminibot.views.PaymentMethodsActivity;
import io.dojogeek.adminibot.views.RegisterExpenseActivity;
import io.dojogeek.adminibot.views.LoginActivity;
import io.dojogeek.adminibot.views.InboxFragment;
import io.dojogeek.adminibot.views.RegisterUserActivity;

@AdminBotScope
@Subcomponent(modules = {AdminiBotModule.class})
public interface AdminiBotComponent {

    void inject(RegisterUserActivity activity);

    void inject(LoginActivity activity);

    void inject(InboxFragment fragment);

    void inject(RegisterExpenseActivity activity);

    void inject(PaymentMethodsActivity activity);

    void inject(AddPaymentMethodActivity activity);

    void inject(CardCreationActivity activity);

    void inject(CreditCardActivity activity);

    void inject(CashActivity activity);

    void inject(CheckActivity activity);

    void inject(FoodCouponsActivity activity);

    void inject(AddNewPaymentMethodActivity activity);

    void inject(MyPaymentMethodsActivity activity);
}
