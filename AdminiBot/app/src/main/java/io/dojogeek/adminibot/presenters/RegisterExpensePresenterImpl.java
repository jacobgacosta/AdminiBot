package io.dojogeek.adminibot.presenters;

import java.util.List;

import io.dojogeek.adminibot.daos.ExpenseDao;
import io.dojogeek.adminibot.daos.ExpenseTypeDao;
import io.dojogeek.adminibot.daos.PaymentMethodDao;
import io.dojogeek.adminibot.daos.UserDao;
import io.dojogeek.adminibot.models.ExpenseModel;
import io.dojogeek.adminibot.models.ExpenseTypeModel;
import io.dojogeek.adminibot.models.TypePaymentMethodModel;
import io.dojogeek.adminibot.models.UserModel;
import io.dojogeek.adminibot.views.RegisterExpense;

public class RegisterExpensePresenterImpl implements RegisterExpensePresenter {

    private static int INDEX_UNIQUE_USER = 0;
    private RegisterExpense mRegisterExpense;
    private ExpenseTypeDao mExpenseTypeDao;
    private ExpenseDao mExpenseDao;
    private UserDao mUserDao;
    private PaymentMethodDao mPaymentMethodDao;

    public RegisterExpensePresenterImpl(RegisterExpense registerExpense,
                                        ExpenseTypeDao expenseTypeDao, ExpenseDao expenseDao,
                                        UserDao userDao, PaymentMethodDao paymentMethodDao) {

        mRegisterExpense = registerExpense;
        mExpenseTypeDao = expenseTypeDao;
        mExpenseDao = expenseDao;
        mUserDao = userDao;
        mPaymentMethodDao = paymentMethodDao;
    }

    @Override
    public void getExpensesTypes() {

        List<ExpenseTypeModel> expenseTypeModelList = mExpenseTypeDao.getExpensesTypes();

        mRegisterExpense.deployExpensesTypes(expenseTypeModelList);
    }

    @Override
    public void addExpense(ExpenseModel expenseModel) {

        UserModel userModel = mUserDao.getUsers().get(INDEX_UNIQUE_USER);
        expenseModel.setUserId(userModel.getId());

        long isCreated = mExpenseDao.createExpense(expenseModel);

    }

    @Override
    public void unnusedView() {
    }

    @Override
    public void getPaymentMethods() {


        List<TypePaymentMethodModel> typePaymentMethodsModels = null;

        mRegisterExpense.deployPaymentMethods(typePaymentMethodsModels);
    }

}
