package io.dojogeek.adminibot.presenters;

import java.util.List;

import io.dojogeek.adminibot.R;
import io.dojogeek.adminibot.daos.ExpenseDao;
import io.dojogeek.adminibot.daos.ExpenseDaoImpl;
import io.dojogeek.adminibot.models.ExpenseModel;
import io.dojogeek.adminibot.views.ExpensesInbox;

public class ExpensesInboxPresenterImpl implements ExpensesInboxPresenter {

    private static final long SINGLE_OPERATION = 1;
    private ExpensesInbox mExpensesInbox;
    private ExpenseDao mExpenseDao;


    public ExpensesInboxPresenterImpl(ExpensesInbox expensesInbox, ExpenseDao expenseDao) {
        mExpensesInbox = expensesInbox;
        mExpenseDao = expenseDao;
    }

    @Override
    public void getExpenses() {

        List<ExpenseModel> expenseModelList =  mExpenseDao.getExpenses();

        mExpensesInbox.listExpenses(expenseModelList);

    }

    @Override
    public void deleteExpense(long expenseId) {
        long deletedRows = mExpenseDao.deleteExpense(expenseId);

        if (deletedRows == SINGLE_OPERATION) {
            mExpensesInbox.showNotification(R.string.success_deletion);
        } else {
            mExpensesInbox.showNotification(R.string.error_deletion);
        }
    }

    @Override
    public void unusedView() {
        closeConnection(mExpenseDao);
    }

    private void closeConnection(ExpenseDao expenseDao) {
        ((ExpenseDaoImpl) expenseDao).closeConnection();
    }
}
