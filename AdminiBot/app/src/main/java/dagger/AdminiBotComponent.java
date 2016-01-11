package dagger;

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

}
