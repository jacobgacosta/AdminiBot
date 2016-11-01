package dagger;

import android.content.Context;

import io.dojogeek.adminibot.daos.BankCardDaoImpl;
import io.dojogeek.adminibot.daos.BankDaoImpl;
import io.dojogeek.adminibot.daos.CardDetailDaoImpl;
import io.dojogeek.adminibot.daos.ExpenseDaoImpl;
import io.dojogeek.adminibot.daos.ExpenseTypeDaoImpl;
import io.dojogeek.adminibot.daos.OtherPaymentMethodDaoImpl;
import io.dojogeek.adminibot.daos.TypesPaymentMethodsDaoImpl;
import io.dojogeek.adminibot.daos.UserDaoImpl;
import io.dojogeek.adminibot.presenters.CashPresenter;
import io.dojogeek.adminibot.presenters.CashPresenterImpl;
import io.dojogeek.adminibot.presenters.CheckPresenter;
import io.dojogeek.adminibot.presenters.CheckPresenterImpl;
import io.dojogeek.adminibot.presenters.CreditCardDetailPresenter;
import io.dojogeek.adminibot.presenters.CreditCardDetailPresenterImpl;
import io.dojogeek.adminibot.presenters.CreditCardPresenter;
import io.dojogeek.adminibot.presenters.CreditCardPresenterImpl;
import io.dojogeek.adminibot.presenters.FoodCouponPresenter;
import io.dojogeek.adminibot.presenters.FoodCouponPresenterImpl;
import io.dojogeek.adminibot.presenters.MyCreditCardsPresenter;
import io.dojogeek.adminibot.presenters.MyCreditCardsPresenterImpl;
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
import io.dojogeek.adminibot.views.AddNewPaymentMethod;
import io.dojogeek.adminibot.views.AddNewPaymentMethodActivity;
import io.dojogeek.adminibot.views.AddPaymentMethod;
import io.dojogeek.adminibot.views.AddPaymentMethodActivity;
import io.dojogeek.adminibot.views.CardCreation;
import io.dojogeek.adminibot.views.CardCreationActivity;
import io.dojogeek.adminibot.views.Cash;
import io.dojogeek.adminibot.views.CashActivity;
import io.dojogeek.adminibot.views.Check;
import io.dojogeek.adminibot.views.CheckActivity;
import io.dojogeek.adminibot.views.CreditCard;
import io.dojogeek.adminibot.views.CreditCardActivity;
import io.dojogeek.adminibot.views.CreditCardDetail;
import io.dojogeek.adminibot.views.CreditCardDetailActivity;
import io.dojogeek.adminibot.views.FoodCoupons;
import io.dojogeek.adminibot.views.FoodCouponsActivity;
import io.dojogeek.adminibot.views.MyCreditCards;
import io.dojogeek.adminibot.views.MyCreditCardsActivity;
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
    private CardCreation mCardCreation;
    private CreditCard mCreditCard;
    private Cash mCash;
    private Check mCheck;
    private FoodCoupons mFoodCoupons;
    private AddNewPaymentMethod mAddNewPaymentMethod;
    private MyCreditCards mMyCreditCards;
    private CreditCardDetail mCreditCardDetail;

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

    public AdminiBotModule(CardCreation cardCreation) {
        mCardCreation = cardCreation;
        mContext = (CardCreationActivity) cardCreation;
    }

    public AdminiBotModule(CreditCard creditCard) {
        mCreditCard = creditCard;
        mContext = (CreditCardActivity) creditCard;
    }

    public AdminiBotModule(Cash cash) {
        mCash = cash;
        mContext = (CashActivity) cash;
    }

    public AdminiBotModule(Check check) {
        mCheck = check;
        mContext = (CheckActivity) check;
    }

    public AdminiBotModule(FoodCoupons foodCoupons) {
        mFoodCoupons = foodCoupons;
        mContext = (FoodCouponsActivity) foodCoupons;
    }

    public AdminiBotModule(AddNewPaymentMethod addNewPaymentMethod) {
        mAddNewPaymentMethod = addNewPaymentMethod;
        mContext = (AddNewPaymentMethodActivity) addNewPaymentMethod;
    }

    public AdminiBotModule(MyCreditCards myCreditCards) {
        mMyCreditCards = myCreditCards;
        mContext = (MyCreditCardsActivity) myCreditCards;
    }

    public AdminiBotModule(CreditCardDetail creditCardDetail) {
        mCreditCardDetail = creditCardDetail;
        mContext = (CreditCardDetailActivity) creditCardDetail;
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
    CashPresenter provideCashPresenter(OtherPaymentMethodDaoImpl otherPaymentMethodDao) {

        return new CashPresenterImpl(mCash, otherPaymentMethodDao);
    }

    @Provides
    @AdminBotScope
    CheckPresenter provideCheckPresenter(OtherPaymentMethodDaoImpl otherPaymentMethodDao) {

        return new CheckPresenterImpl(mCheck, otherPaymentMethodDao);
    }

    @Provides
    @AdminBotScope
    FoodCouponPresenter provideFoodCouponPresenter(OtherPaymentMethodDaoImpl otherPaymentMethodDao) {
        return new FoodCouponPresenterImpl(mFoodCoupons, otherPaymentMethodDao);
    }

    @Provides
    @AdminBotScope
    CreditCardPresenter provideCreditCardPresenter(BankCardDaoImpl bankCardDao, CardDetailDaoImpl cardDetailDao,
                                                   BankDaoImpl bankDao) {
        return new CreditCardPresenterImpl(mCreditCard, bankCardDao, cardDetailDao, bankDao);
    }

    @Provides
    @AdminBotScope
    MyCreditCardsPresenter provideMyPaymentMethodsPresenter(BankCardDaoImpl bankCardDao,
                                                            CardDetailDaoImpl cardDetailDao,
                                                            BankDaoImpl bankDao) {
        return new MyCreditCardsPresenterImpl(mMyCreditCards, bankCardDao, cardDetailDao, bankDao);
    }

    @Provides
    @AdminBotScope
    CreditCardDetailPresenter provideCreditCardDetailPresenter(BankCardDaoImpl bankCardDao,
                                                               CardDetailDaoImpl cardDetailDao,
                                                               BankDaoImpl bankDao) {
        return new CreditCardDetailPresenterImpl(mCreditCardDetail, bankCardDao, cardDetailDao,
                bankDao);
    }

    @Provides
    @AdminBotScope
    Context provideApplicationContext() {

        return mContext;

    }

}
