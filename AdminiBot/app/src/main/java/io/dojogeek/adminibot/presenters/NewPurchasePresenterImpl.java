package io.dojogeek.adminibot.presenters;

import java.util.List;

import io.dojogeek.adminibot.daos.ExpenseDao;
import io.dojogeek.adminibot.daos.ExpenseDaoImpl;
import io.dojogeek.adminibot.models.ExpenseModel;
import io.dojogeek.adminibot.views.Inbox;

public class NewPurchasePresenterImpl implements NewPurchasePresenter {

    private Inbox mInbox;
    private ExpenseDao mExpenseDao;

    public NewPurchasePresenterImpl(Inbox inbox, ExpenseDao expenseDao) {
        mInbox = inbox;
        mExpenseDao = expenseDao;
    }

    @Override
    public void loadExpenses() {

        List<ExpenseModel> expenseModels = mExpenseDao.getExpenses();

        mInbox.listExpenses(expenseModels);

    }

    @Override
    public void unnusedView() {
        ((ExpenseDaoImpl) mExpenseDao).closeConnection();
    }
}
