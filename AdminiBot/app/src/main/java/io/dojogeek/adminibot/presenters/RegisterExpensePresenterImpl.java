package io.dojogeek.adminibot.presenters;

import java.util.List;

import io.dojogeek.adminibot.R;
import io.dojogeek.adminibot.daos.ConectionDao;
import io.dojogeek.adminibot.daos.ExpenseDao;
import io.dojogeek.adminibot.daos.ExpenseTypeDao;
import io.dojogeek.adminibot.daos.TypesPaymentMethodsDao;
import io.dojogeek.adminibot.daos.UserDao;
import io.dojogeek.adminibot.models.ExpenseModel;
import io.dojogeek.adminibot.models.ExpenseTypeModel;
import io.dojogeek.adminibot.models.TypesPaymentMethodsModel;
import io.dojogeek.adminibot.models.UserModel;
import io.dojogeek.adminibot.views.RegisterExpense;

public class RegisterExpensePresenterImpl implements RegisterExpensePresenter {

    private static int INDEX_UNIQUE_USER = 0;
    private RegisterExpense mRegisterExpense;
    private ExpenseTypeDao mExpenseTypeDao;
    private ExpenseDao mExpenseDao;
    private UserDao mUserDao;
    private TypesPaymentMethodsDao mTypesPaymentMethodsDao;

    public RegisterExpensePresenterImpl(RegisterExpense registerExpense,
                                        ExpenseTypeDao expenseTypeDao, ExpenseDao expenseDao,
                                        UserDao userDao, TypesPaymentMethodsDao typesPaymentMethodsDao) {

        mRegisterExpense = registerExpense;
        mExpenseTypeDao = expenseTypeDao;
        mExpenseDao = expenseDao;
        mUserDao = userDao;
        mTypesPaymentMethodsDao = typesPaymentMethodsDao;
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

        UserModel userModel = mUserDao.getUsers().get(INDEX_UNIQUE_USER);
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
        closeConnection(mTypesPaymentMethodsDao);
    }

    @Override
    public void getPaymentMethods() {

        mTypesPaymentMethodsDao.openConection();

        List<TypesPaymentMethodsModel> typePaymentMethodsModels = mTypesPaymentMethodsDao.getPaymentMethods();

        mRegisterExpense.deployPaymentMethods(typePaymentMethodsModels);
    }

    private void closeConnection(ConectionDao conectionDao) {
        conectionDao.closeConection();
    }
}
