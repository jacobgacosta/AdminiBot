package dagger;

import io.dojogeek.adminibot.views.PaymentMethodsActivity;

@AdminBotScope
@Subcomponent(modules = {AdminiBotModule.class})
public interface AdminiBotComponent {

    void inject(PaymentMethodsActivity activity);

}
