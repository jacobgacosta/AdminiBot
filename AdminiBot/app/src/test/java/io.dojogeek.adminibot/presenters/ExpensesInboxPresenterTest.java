package io.dojogeek.adminibot.presenters;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;

import io.dojogeek.adminibot.R;
import io.dojogeek.adminibot.daos.ExpenseDaoImpl;
import io.dojogeek.adminibot.models.ExpenseModel;
import io.dojogeek.adminibot.utils.DateUtils;
import io.dojogeek.adminibot.views.ExpensesInbox;

import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
    public class ExpensesInboxPresenterTest {

    @Mock
    private ExpensesInbox mExpensesInboxActivity;

    @Mock
    private ExpenseDaoImpl mExpenseDao;

    @InjectMocks
    private ExpensesInboxPresenter mExpensesInboxPresenter = new ExpensesInboxPresenterImpl(mExpensesInboxActivity, mExpenseDao);

    @Test
    public void testGetExpenses_successfulObtainingExpensesList() {

        List<ExpenseModel> expenseModelList = getExpensesList();

        when(mExpenseDao.getExpenses()).thenReturn(expenseModelList);

        mExpensesInboxPresenter.getExpenses();

        verify(mExpenseDao).getExpenses();
        verify(mExpensesInboxActivity).listExpenses(expenseModelList);

    }

    @Test
    public void testGetExpenses_obtainingEmptyExpensesList() {

        List<ExpenseModel> emptyExpenseModelList =  new ArrayList<>();

        when(mExpenseDao.getExpenses()).thenReturn(emptyExpenseModelList);

        mExpensesInboxPresenter.getExpenses();

        verify(mExpenseDao).getExpenses();
        verify(mExpensesInboxActivity).listExpenses(emptyExpenseModelList);
    }

    @Test
    public void testDeleteExpense_successfulDeletion() {

        long deletedRows = 1;
        long expenseId = 4;

        when(mExpenseDao.deleteExpense(anyLong())).thenReturn(deletedRows);

        mExpensesInboxPresenter.deleteExpense(expenseId);

        verify(mExpenseDao).deleteExpense(anyLong());
        verify(mExpensesInboxActivity).successfulDeletion();
    }

    @Test
    public void testDeleteExpense_deletionError() {

        long deletedRows = 0;
        long expenseId = 1;

        when(mExpenseDao.deleteExpense(anyLong())).thenReturn(deletedRows);

        mExpensesInboxPresenter.deleteExpense(expenseId);

        verify(mExpenseDao).deleteExpense(anyLong());
        verify(mExpensesInboxActivity).errorDeletion();
    }

    @Test
    public void testCloseConnection_successfulInvocationOfUnusedView() {

        mExpensesInboxPresenter.unusedView();

        verify(mExpenseDao).closeConnection();

    }

    private List<ExpenseModel> getExpensesList() {

        ExpenseModel expenseModel = createExpenseModel(1, "Payment camera", 7800,
                DateUtils.getCurrentData(), "", 1, 1);

        List<ExpenseModel> expenseModelList = new ArrayList<>();
        expenseModelList.add(expenseModel);
        expenseModelList.add(expenseModel);
        expenseModelList.add(expenseModel);
        expenseModelList.add(expenseModel);

        return expenseModelList;
    }

    private ExpenseModel createExpenseModel(int id, String description, double amount,
                                            String dateExpediture, String nextExit, long expenseTypeId,
                                            long userId) {

        ExpenseModel expenseModel = new ExpenseModel();
        expenseModel.setId(id);
        expenseModel.setDescription(description);
        expenseModel.setAmount(amount);
        expenseModel.setDateExpediture(dateExpediture);
        expenseModel.setNextExit(nextExit);
        expenseModel.setExpenseTypeId(expenseTypeId);
        expenseModel.setUserId(userId);

        return expenseModel;
    }

}
