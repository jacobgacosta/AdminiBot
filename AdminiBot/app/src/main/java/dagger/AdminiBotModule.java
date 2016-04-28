package dagger;

import android.content.Context;

import io.dojogeek.adminibot.daos.BankCardDaoImpl;
import io.dojogeek.adminibot.daos.ExpenseDaoImpl;
import io.dojogeek.adminibot.daos.ExpenseTypeDaoImpl;
import io.dojogeek.adminibot.daos.OtherPaymentMethodDaoImpl;
import io.dojogeek.adminibot.daos.TypesPaymentMethodsDaoImpl;
import io.dojogeek.adminibot.daos.UserDaoImpl;
import io.dojogeek.adminibot.presenters.PaymentMethodsPresenter;
import io.dojogeek.adminibot.presenters.PaymentMethodsPresenterImpl;
import io.dojogeek.adminibot.presenters.RegisterExpensePresenter;
import io.dojogeek.adminibot.presenters.RegisterExpensePresenterImpl;
import io.dojogeek.adminibot.presenters.LoginPresenter;
import io.dojogeek.adminibot.presenters.LoginPresenterImpl;
import io.dojogeek.adminibot.presenters.NewPurchasePresenter;
import io.dojogeek.adminibot.presenters.NewPurchasePresenterImpl;
import io.dojogeek.adminibot.presenters.RegisterUserPresenter;
import io.dojogeek.adminibot.presenters.RegisterUserPresenterImpl;
import io.dojogeek.adminibot.views.AddPaymentMethod;
import io.dojogeek.adminibot.views.AddPaymentMethodActivity;
import io.dojogeek.adminibot.views.CardCreationActivity;
import io.dojogeek.adminibot.views.PaymentMethods;
import io.dojogeek.adminibot.views.PaymentMethodsActivity;
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
    private PaymentMethods mPaymentMethods;
    private AddPaymentMethod mAddPaymentMethod;
    private CardCreationActivity mCardCreation;

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

    public AdminiBotModule(PaymentMethods paymentMethods) {
        mPaymentMethods = paymentMethods;
        mContext = ((PaymentMethodsActivity) paymentMethods);
    }

    public AdminiBotModule(AddPaymentMethod addPaymentMethod) {
        mAddPaymentMethod = addPaymentMethod;
        mContext = ((AddPaymentMethodActivity) addPaymentMethod);
    }

    public AdminiBotModule(CardCreationActivity cardCreation) {
        mCardCreation = cardCreation;
        mContext = (CardCreationActivity) cardCreation;
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
                                                                 UserDaoImpl userDaoImpl, TypesPaymentMethodsDaoImpl typesPaymentMethodsDaoImpl) {

        return new RegisterExpensePresenterImpl(mRegisterExpense, expenseTypeDaoImpl, expenseDaoImpl,
                userDaoImpl, typesPaymentMethodsDaoImpl);
    }

    @Provides
    @AdminBotScope
    PaymentMethodsPresenter providePaymentMethodsPresenter(OtherPaymentMethodDaoImpl otherPaymentMethodDao,
                                                           BankCardDaoImpl bankCardDao) {

        return new PaymentMethodsPresenterImpl(mPaymentMethods, otherPaymentMethodDao, bankCardDao) ;
    }

    @Provides
    @AdminBotScope
    Context provideApplicationContext() {

        return mContext;

    }

}
