package io.dojogeek.adminibot.presenters;

import java.util.List;

import io.dojogeek.adminibot.R;
import io.dojogeek.adminibot.daos.ConectionDao;
import io.dojogeek.adminibot.daos.ExpenseDao;
import io.dojogeek.adminibot.daos.ExpenseTypeDao;
import io.dojogeek.adminibot.daos.PaymentMethodsDao;
import io.dojogeek.adminibot.daos.UserDao;
import io.dojogeek.adminibot.models.ExpenseModel;
import io.dojogeek.adminibot.models.ExpenseTypeModel;
import io.dojogeek.adminibot.models.PaymentMethodModel;
import io.dojogeek.adminibot.models.UserModel;
import io.dojogeek.adminibot.views.RegisterExpense;

public class RegisterExpensePresenterImpl implements RegisterExpensePresenter {

    private RegisterExpense mRegisterExpense;
    private ExpenseTypeDao mExpenseTypeDao;
    private ExpenseDao mExpenseDao;
    private UserDao mUserDao;
    private PaymentMethodsDao mPaymentMethodsDao;

    public RegisterExpensePresenterImpl(RegisterExpense registerExpense,
                                        ExpenseTypeDao expenseTypeDao, ExpenseDao expenseDao,
                                        UserDao userDao, PaymentMethodsDao paymentMethodsDao) {

        mRegisterExpense = registerExpense;
        mExpenseTypeDao = expenseTypeDao;
        mExpenseDao = expenseDao;
        mUserDao = userDao;
        mPaymentMethodsDao = paymentMethodsDao;
    }

    @Override
    public void getExpensesTypes() {

        mExpenseTypeDao.openConection();

        List<ExpenseTypeModel> expenseTypeModelList = mExpenseTypeDao.getExpensesTypes();

        mRegisterExpense.deployExpensesTypes(expenseTypeModelList);
    }

    @Override
    public void addExpense(ExpenseModel expenseModel) {

        mExpenseDao.openConection();

        mUserDao.openConection();

        UserModel userModel = mUserDao.getUser();
        expenseModel.userId = userModel.id;

        boolean isCreated = mExpenseDao.createExpense(expenseModel);

        if (isCreated) {
            mRegisterExpense.showNotification(R.string.expense_registered_ok);
        } else {
            mRegisterExpense.showNotification(R.string.register_failed);
        }
    }

    @Override
    public void unnusedView() {
        closeConnection(mExpenseTypeDao);
        closeConnection(mUserDao);
        closeConnection(mExpenseDao);
        closeConnection(mPaymentMethodsDao);
    }

    @Override
    public void getPaymentMethods() {

        mPaymentMethodsDao.openConection();

        List<PaymentMethodModel> paymentMethodModels = mPaymentMethodsDao.getPaymentMethods();

        mRegisterExpense.deployPaymentMethods(paymentMethodModels);
    }

    private void closeConnection(ConectionDao conectionDao) {
        conectionDao.closeConection();
    }
}
