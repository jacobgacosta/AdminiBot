package dagger;

import android.content.Context;

import io.dojogeek.adminibot.daos.ExpenseDaoImpl;
import io.dojogeek.adminibot.daos.ExpenseTypeDaoImpl;
import io.dojogeek.adminibot.daos.PaymentMethodsDao;
import io.dojogeek.adminibot.daos.PaymentMethodsDaoImpl;
import io.dojogeek.adminibot.daos.UserDaoImpl;
import io.dojogeek.adminibot.presenters.RegisterExpensePresenter;
import io.dojogeek.adminibot.presenters.RegisterExpensePresenterImpl;
import io.dojogeek.adminibot.presenters.LoginPresenter;
import io.dojogeek.adminibot.presenters.LoginPresenterImpl;
import io.dojogeek.adminibot.presenters.NewPurchasePresenter;
import io.dojogeek.adminibot.presenters.NewPurchasePresenterImpl;
import io.dojogeek.adminibot.presenters.RegisterUserPresenter;
import io.dojogeek.adminibot.presenters.RegisterUserPresenterImpl;
import io.dojogeek.adminibot.views.RegisterExpense;
import io.dojogeek.adminibot.views.RegisterExpenseActivity;
import io.dojogeek.adminibot.views.Login;
import io.dojogeek.adminibot.views.LoginActivity;
import io.dojogeek.adminibot.views.InboxFragment;
import io.dojogeek.adminibot.views.Inbox;
import io.dojogeek.adminibot.views.RegisterUser;
import io.dojogeek.adminibot.views.RegisterUserActivity;

@Module
public class AdminiBotModule {

    private RegisterUser mRegisterUser;
    private Login mLogin;
    private Inbox mInbox;
    private Context mContext;
    private RegisterExpense mRegisterExpense;

    public AdminiBotModule(RegisterUser registerUser) {
        mRegisterUser = registerUser;
        mContext = ((RegisterUserActivity) mRegisterUser);
    }

    public AdminiBotModule(Login login) {
        mLogin = login;
        mContext = ((LoginActivity) mLogin);
    }

    public AdminiBotModule(Inbox inbox) {
        mInbox = inbox;
        mContext = ((InboxFragment) mInbox).getActivity();
    }

    public AdminiBotModule(RegisterExpense registerExpense) {
        mRegisterExpense = registerExpense;
        mContext = ((RegisterExpenseActivity) registerExpense);
    }

    @Provides
    @AdminBotScope
    RegisterUserPresenter provideRegisterUserPresenter(UserDaoImpl userDaoImpl) {

        return new RegisterUserPresenterImpl(mRegisterUser, userDaoImpl);
    }

    @Provides
    @AdminBotScope
    LoginPresenter provideLoginPresenter() {
        return new LoginPresenterImpl(mLogin);
    }

    @Provides
    @AdminBotScope
    NewPurchasePresenter provideNewPurchasePresenter(ExpenseDaoImpl expenseDaoImpl) {

        return new NewPurchasePresenterImpl(mInbox, expenseDaoImpl);
    }

    @Provides
    @AdminBotScope
    RegisterExpensePresenter provideExpenseRegistrationPresenter(ExpenseTypeDaoImpl expenseTypeDaoImpl,
                                                                 ExpenseDaoImpl expenseDaoImpl,
                                                                 UserDaoImpl userDaoImpl, PaymentMethodsDaoImpl paymentMethodsDaoImpl) {

        return new RegisterExpensePresenterImpl(mRegisterExpense, expenseTypeDaoImpl, expenseDaoImpl,
                userDaoImpl, paymentMethodsDaoImpl);
    }

    @Provides
    @AdminBotScope
    Context provideApplicationContext() {

        return mContext;

    }

}
