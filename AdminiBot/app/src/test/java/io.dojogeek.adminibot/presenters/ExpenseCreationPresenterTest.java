package io.dojogeek.adminibot.presenters;

import android.database.SQLException;
import android.util.Log;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.powermock.api.support.membermodification.MemberModifier;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import io.dojogeek.adminibot.daos.BankCardDaoImpl;
import io.dojogeek.adminibot.daos.ExpenseBankCardDaoImpl;
import io.dojogeek.adminibot.daos.ExpenseDaoImpl;
import io.dojogeek.adminibot.daos.ExpenseOtherPaymentMethodDaoImpl;
import io.dojogeek.adminibot.daos.OtherPaymentMethodDaoImpl;
import io.dojogeek.adminibot.enums.TypePaymentMethodEnum;
import io.dojogeek.adminibot.exceptions.DataException;
import io.dojogeek.adminibot.factory.ModelsFactory;
import io.dojogeek.adminibot.matchers.IsAList;
import io.dojogeek.adminibot.models.BankCardModel;
import io.dojogeek.adminibot.models.ExpenseBankCardModel;
import io.dojogeek.adminibot.models.ExpenseModel;
import io.dojogeek.adminibot.models.ExpenseOtherPaymentMethodModel;
import io.dojogeek.adminibot.models.OtherPaymentMethodModel;
import io.dojogeek.adminibot.views.ExpenseCreation;

import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class ExpenseCreationPresenterTest {


    @Mock
    private BankCardModel mBankCardModel;

    @Mock
    private OtherPaymentMethodModel mOtherPaymentMethodModel;

    @Mock
    private ExpenseModel mExpenseModel;

    @Mock
    private ExpenseCreation mExpenseCreationActivity;

    @Mock
    private ExpenseDaoImpl mExpenseDao;

    @Mock
    private ExpenseOtherPaymentMethodModel mExpenseOtherPaymentMethodModel;

    @Mock
    private ExpenseBankCardModel mExpenseBankCardModel;

    @Mock
    private ExpenseOtherPaymentMethodDaoImpl mExpenseOtherPaymentMethodDao;

    @Mock
    private ExpenseBankCardDaoImpl mExpenseBankCardDao;

    @Mock
    private OtherPaymentMethodDaoImpl mOtherPaymentMethodDao;

    @Mock
    private BankCardDaoImpl mBankCardDao;

    @InjectMocks
    private ExpenseCreationPresenter mExpenseCreationPresenter =
            new ExpenseCreationPresenterImpl(mExpenseCreationActivity, mExpenseDao,
                    mOtherPaymentMethodDao, mBankCardDao, mExpenseOtherPaymentMethodDao, mExpenseBankCardDao);

    @Test
    public void testCreateExpense_successfulCreationWithBankCarAndOtherPaymentMethods() throws DataException {

        long updatedRows = 1;
        long insertedRecordId = 1;
        BigDecimal availableCredit = new BigDecimal(546.983);
        double amounSpent = 120;

        List<ExpenseOtherPaymentMethodModel> expenseOtherPaymentMethodModels =  new ArrayList<>();
        expenseOtherPaymentMethodModels.add(mExpenseOtherPaymentMethodModel);

        List<ExpenseBankCardModel> expenseBankCardModels = new ArrayList<>();
        expenseBankCardModels.add(mExpenseBankCardModel);


        when(mExpenseDao.createExpense(mExpenseModel)).thenReturn(insertedRecordId);
        when(mExpenseOtherPaymentMethodModel.getAmount()).thenReturn(amounSpent);
        when(mExpenseBankCardModel.getAmount()).thenReturn(amounSpent);
        when(mExpenseModel.getOtherPaymentMethodModels()).thenReturn(expenseOtherPaymentMethodModels);
        when(mExpenseModel.getExpenseBankCardModels()).thenReturn(expenseBankCardModels);
        when(mOtherPaymentMethodDao.getOtherPaymentMethodById(anyLong())).thenReturn(mOtherPaymentMethodModel);
        when(mOtherPaymentMethodModel.getAvailableCredit()).thenReturn(availableCredit);
        when(mOtherPaymentMethodDao.updateOtherPaymentMethod(mOtherPaymentMethodModel, mOtherPaymentMethodModel.getId())).
                thenReturn(updatedRows);
        when(mBankCardDao.getBankCardById(anyLong())).thenReturn(mBankCardModel);
        when(mBankCardModel.getAvailableCredit()).thenReturn(availableCredit.doubleValue());
        when(mBankCardDao.updateBankCard(mBankCardModel, mBankCardModel.getId())).thenReturn(updatedRows);

        mExpenseCreationPresenter.createExpense(mExpenseModel);

        double totalOtherPaymentMethod = availableCredit.doubleValue() - amounSpent;
        double totalBankCard = availableCredit.doubleValue() + amounSpent;

        verifyBeginTransactions();
        verify(mExpenseDao).createExpense(mExpenseModel);

        verify(mExpenseModel, times(2)).getOtherPaymentMethodModels();
        verify(mExpenseOtherPaymentMethodModel).setExpenseId(insertedRecordId);
        verify(mExpenseOtherPaymentMethodDao, times(mExpenseModel.getOtherPaymentMethodModels().size())).
                createExpenseOtherPaymentMethod((ExpenseOtherPaymentMethodModel) notNull());
        verify(mOtherPaymentMethodDao, times(mExpenseModel.getOtherPaymentMethodModels().size())).
                getOtherPaymentMethodById(anyLong());
        verify(mOtherPaymentMethodModel).getAvailableCredit();
        verify(mOtherPaymentMethodModel).setAvailableCredit(new BigDecimal(totalOtherPaymentMethod));
        verify(mOtherPaymentMethodDao, times(mExpenseModel.getOtherPaymentMethodModels().size())).
                updateOtherPaymentMethod(mOtherPaymentMethodModel, mOtherPaymentMethodModel.getId());

        verify(mExpenseModel, times(2)).getExpenseBankCardModels();
        verify(mExpenseBankCardModel).setExpenseId(insertedRecordId);
        verify(mExpenseBankCardDao, times(mExpenseModel.getExpenseBankCardModels().size())).
                createMovementExpenseBankCard((ExpenseBankCardModel) notNull());
        verify(mBankCardDao, times(mExpenseModel.getExpenseBankCardModels().size())).
                getBankCardById(anyLong());
        verify(mBankCardModel).getAvailableCredit();
        verify(mBankCardModel).setAvailableCredit(totalBankCard);
        verify(mBankCardDao, times(mExpenseModel.getExpenseBankCardModels().size())).
                updateBankCard(mBankCardModel, mBankCardModel.getId());
        verify(mExpenseCreationActivity).successfulExpenseCreation();
        verifySetTransactionsSuccessful();
        verifyEndTransactions();

    }

    @Test(expected = NullPointerException.class)
    public void testCreateExpense_withNullExpense_isException() throws DataException {
        mExpenseCreationPresenter.createExpense(null);


        verify(mExpenseOtherPaymentMethodDao, never()).
                createExpenseOtherPaymentMethod((ExpenseOtherPaymentMethodModel) anyObject());
        verify(mOtherPaymentMethodDao, never()).getOtherPaymentMethodById(anyLong());
        verify(mOtherPaymentMethodDao, never()).updateOtherPaymentMethod((OtherPaymentMethodModel) anyObject(), anyLong());

        verify(mExpenseBankCardDao, never()).
                createMovementExpenseBankCard((ExpenseBankCardModel) anyObject());
        verify(mBankCardDao, never()).getBankCardById(anyLong());
        verify(mBankCardDao, never()).updateBankCard((BankCardModel) anyObject(), anyLong());

        verify(mExpenseCreationActivity).errorExpenseCreation();

        verifyNeverInvokeSetTransactionsSuccessful();

        verifyEndTransactions();
    }

    @Test
    public void testCreateExpense_withExpenseInsertionError() throws DataException {

        when(mExpenseDao.createExpense(mExpenseModel)).thenThrow(new SQLException());

        mExpenseCreationPresenter.createExpense(mExpenseModel);

        verify(mExpenseDao).createExpense(mExpenseModel);
        verify(mExpenseOtherPaymentMethodDao, never()).createExpenseOtherPaymentMethod((ExpenseOtherPaymentMethodModel) anyObject());
        verify(mOtherPaymentMethodDao, never()).getOtherPaymentMethodById(anyLong());
        verify(mOtherPaymentMethodDao, never()).updateOtherPaymentMethod((OtherPaymentMethodModel) anyObject(), anyLong());
        verify(mExpenseBankCardDao, never()).createMovementExpenseBankCard((ExpenseBankCardModel) anyObject());
        verify(mBankCardDao, never()).getBankCardById(anyLong());
        verify(mBankCardDao, never()).updateBankCard((BankCardModel) anyObject(), anyLong());
        verify(mExpenseCreationActivity).errorExpenseCreation();
        verifyNeverInvokeSetTransactionsSuccessful();
        verifyEndTransactions();
    }

    @Test
    public void testCreateExpense_withExpenseBankCardInsertionError() throws DataException {

        List<ExpenseBankCardModel> expenseBankCardModels = new ArrayList<>();
        expenseBankCardModels.add(mExpenseBankCardModel);

        when(mExpenseModel.getExpenseBankCardModels()).thenReturn(expenseBankCardModels);
        when(mExpenseModel.getOtherPaymentMethodModels()).thenReturn(new ArrayList<ExpenseOtherPaymentMethodModel>());

        when(mExpenseBankCardDao.createMovementExpenseBankCard((ExpenseBankCardModel) notNull())).
                thenThrow(SQLException.class);

        mExpenseCreationPresenter.createExpense(mExpenseModel);

        verify(mExpenseDao).createExpense(mExpenseModel);
        verify(mExpenseOtherPaymentMethodDao, never()).
                        createExpenseOtherPaymentMethod((ExpenseOtherPaymentMethodModel) anyObject());
        verify(mOtherPaymentMethodDao, never()).getOtherPaymentMethodById(anyLong());
        verify(mOtherPaymentMethodDao, never()).updateOtherPaymentMethod((OtherPaymentMethodModel) anyObject(), anyLong());
        verify(mExpenseBankCardDao).createMovementExpenseBankCard((ExpenseBankCardModel) notNull());
        verify(mBankCardDao, never()).getBankCardById(anyLong());
        verify(mBankCardDao, never()).updateBankCard((BankCardModel) anyObject(), anyLong());
        verify(mExpenseCreationActivity, never()).successfulExpenseCreation();
        verify(mExpenseCreationActivity).errorExpenseCreation();
        verifyNeverInvokeSetTransactionsSuccessful();
        verifyEndTransactions();

    }

    @Test
    public void testCreateExpense_withBankCardObtainingError() throws DataException {

        List<ExpenseBankCardModel> expenseBankCardModels = new ArrayList<>();
        expenseBankCardModels.add(mExpenseBankCardModel);

        when(mExpenseModel.getExpenseBankCardModels()).thenReturn(expenseBankCardModels);
        when(mBankCardDao.getBankCardById(anyLong())).thenThrow(SQLException.class);

        mExpenseCreationPresenter.createExpense(mExpenseModel);

        verify(mExpenseDao).createExpense(mExpenseModel);
        verify(mExpenseOtherPaymentMethodDao,never()).
                createExpenseOtherPaymentMethod((ExpenseOtherPaymentMethodModel) anyObject());
        verify(mOtherPaymentMethodDao, never()).getOtherPaymentMethodById(anyLong());
        verify(mOtherPaymentMethodDao, never()).
                updateOtherPaymentMethod((OtherPaymentMethodModel) anyObject(), anyLong());
        verify(mExpenseBankCardDao).createMovementExpenseBankCard((ExpenseBankCardModel) anyObject());
        verify(mBankCardDao).getBankCardById(anyLong());
        verify(mBankCardDao, never()).updateBankCard((BankCardModel) anyObject(), anyLong());
        verify(mExpenseCreationActivity, never()).successfulExpenseCreation();
        verify(mExpenseCreationActivity).errorExpenseCreation();
        verifyNeverInvokeSetTransactionsSuccessful();
        verifyEndTransactions();

    }

    @Test
    public void testCreateExpense_withBankCardUpdatingError() throws DataException {

        List<ExpenseBankCardModel> expenseBankCardModels = new ArrayList<>();
        expenseBankCardModels.add(mExpenseBankCardModel);

        when(mExpenseModel.getExpenseBankCardModels()).thenReturn(expenseBankCardModels);
        when(mBankCardDao.getBankCardById(anyLong())).thenReturn(mBankCardModel);
        when(mBankCardDao.updateBankCard((BankCardModel) anyObject(),
                anyLong())).thenThrow(SQLException.class);

        mExpenseCreationPresenter.createExpense(mExpenseModel);

        verify(mExpenseDao).createExpense(mExpenseModel);
        verify(mExpenseOtherPaymentMethodDao, never()).
                createExpenseOtherPaymentMethod((ExpenseOtherPaymentMethodModel) anyObject());
        verify(mOtherPaymentMethodDao, never()).getOtherPaymentMethodById(anyLong());
        verify(mOtherPaymentMethodDao, never()).
                updateOtherPaymentMethod((OtherPaymentMethodModel) anyObject(), anyLong());
        verify(mExpenseBankCardDao).createMovementExpenseBankCard((ExpenseBankCardModel) anyObject());
        verify(mBankCardDao).getBankCardById(anyLong());
        verify(mBankCardDao).updateBankCard((BankCardModel) anyObject(), anyLong());
        verify(mExpenseCreationActivity, never()).successfulExpenseCreation();
        verify(mExpenseCreationActivity).errorExpenseCreation();
        verifyNeverInvokeSetTransactionsSuccessful();
        verifyEndTransactions();
    }

    @Test
    public void testCreateExpense_withExpenseOtherPaymentMethodsInsertionError() throws DataException {

        List<ExpenseOtherPaymentMethodModel> expenseOtherPaymentMethodModels = new ArrayList<>();
        expenseOtherPaymentMethodModels.add(mExpenseOtherPaymentMethodModel);

        when(mExpenseModel.getOtherPaymentMethodModels()).thenReturn(expenseOtherPaymentMethodModels);
        when(mExpenseOtherPaymentMethodDao.
                createExpenseOtherPaymentMethod((ExpenseOtherPaymentMethodModel) anyObject())).thenThrow(SQLException.class);

        mExpenseCreationPresenter.createExpense(mExpenseModel);

        verify(mExpenseDao).createExpense(mExpenseModel);
        verify(mExpenseOtherPaymentMethodDao).
                createExpenseOtherPaymentMethod((ExpenseOtherPaymentMethodModel) anyObject());
        verify(mOtherPaymentMethodDao, never()).getOtherPaymentMethodById(anyLong());
        verify(mOtherPaymentMethodDao, never()).
                updateOtherPaymentMethod((OtherPaymentMethodModel) anyObject(), anyLong());
        verify(mExpenseBankCardDao, never()).
                createMovementExpenseBankCard((ExpenseBankCardModel) anyObject());
        verify(mBankCardDao, never()).getBankCardById(anyLong());
        verify(mBankCardDao, never()).updateBankCard((BankCardModel) anyObject(), anyLong());
        verify(mExpenseCreationActivity, never()).successfulExpenseCreation();
        verify(mExpenseCreationActivity).errorExpenseCreation();
        verifyNeverInvokeSetTransactionsSuccessful();
        verifyEndTransactions();

    }

    @Test
    public void testCreateExpense_withOtherPaymentMethodObtainingError() throws DataException {

        List<ExpenseOtherPaymentMethodModel> expenseOtherPaymentMethodModels = new ArrayList<>();
        expenseOtherPaymentMethodModels.add(mExpenseOtherPaymentMethodModel);

        when(mExpenseModel.getOtherPaymentMethodModels()).thenReturn(expenseOtherPaymentMethodModels);
        when(mOtherPaymentMethodDao.getOtherPaymentMethodById(anyLong())).thenThrow(SQLException.class);

        mExpenseCreationPresenter.createExpense(mExpenseModel);

        verify(mExpenseDao).createExpense(mExpenseModel);
        verify(mExpenseOtherPaymentMethodDao).createExpenseOtherPaymentMethod((ExpenseOtherPaymentMethodModel) anyObject());
        verify(mOtherPaymentMethodDao).getOtherPaymentMethodById(anyLong());
        verify(mOtherPaymentMethodDao, never()).
                updateOtherPaymentMethod((OtherPaymentMethodModel) anyObject(), anyLong());
        verify(mExpenseBankCardDao, never()).
                createMovementExpenseBankCard((ExpenseBankCardModel) anyObject());
        verify(mBankCardDao, never()).getBankCardById(anyLong());
        verify(mBankCardDao, never()).updateBankCard((BankCardModel) anyObject(), anyLong());
        verify(mExpenseCreationActivity, never()).successfulExpenseCreation();
        verify(mExpenseCreationActivity).errorExpenseCreation();
        verifyNeverInvokeSetTransactionsSuccessful();
        verifyEndTransactions();
    }

    @Test
    public void testCreateExpense_withOtherPaymentMethodUpdatingError() throws DataException {

        List<ExpenseOtherPaymentMethodModel> expenseOtherPaymentMethodModels = new ArrayList<>();
        expenseOtherPaymentMethodModels.add(mExpenseOtherPaymentMethodModel);

        when(mExpenseModel.getOtherPaymentMethodModels()).thenReturn(expenseOtherPaymentMethodModels);
        when(mOtherPaymentMethodDao.getOtherPaymentMethodById(anyLong())).thenReturn(mOtherPaymentMethodModel);
        when(mOtherPaymentMethodDao.updateOtherPaymentMethod((OtherPaymentMethodModel) anyObject(),
                anyLong())).thenThrow(SQLException.class);

        mExpenseCreationPresenter.createExpense(mExpenseModel);

        verify(mExpenseDao).createExpense(mExpenseModel);
        verify(mExpenseOtherPaymentMethodDao).createExpenseOtherPaymentMethod((ExpenseOtherPaymentMethodModel) anyObject());
        verify(mOtherPaymentMethodDao).getOtherPaymentMethodById(anyLong());
        verify(mOtherPaymentMethodDao).updateOtherPaymentMethod((OtherPaymentMethodModel) anyObject(), anyLong());
        verify(mExpenseBankCardDao, never()).
                createMovementExpenseBankCard((ExpenseBankCardModel) anyObject());
        verify(mBankCardDao, never()).getBankCardById(anyLong());
        verify(mBankCardDao, never()).updateBankCard((BankCardModel) anyObject(), anyLong());
        verify(mExpenseCreationActivity, never()).successfulExpenseCreation();
        verify(mExpenseCreationActivity).errorExpenseCreation();
        verifyNeverInvokeSetTransactionsSuccessful();
        verifyEndTransactions();
    }

    private void verifyBeginTransactions() {

        verify(mExpenseDao).beginTransaction();
        verify(mExpenseOtherPaymentMethodDao).beginTransaction();
        verify(mExpenseBankCardDao).beginTransaction();
        verify(mOtherPaymentMethodDao).beginTransaction();
        verify(mBankCardDao).beginTransaction();

    }

    private void verifySetTransactionsSuccessful() {

        verify(mExpenseDao).setTransactionSuccessful();
        verify(mExpenseOtherPaymentMethodDao).setTransactionSuccessful();
        verify(mExpenseBankCardDao).setTransactionSuccessful();
        verify(mOtherPaymentMethodDao).setTransactionSuccessful();
        verify(mBankCardDao).setTransactionSuccessful();

    }

    private void verifyEndTransactions() {

        verify(mExpenseDao).endTransaction();
        verify(mExpenseOtherPaymentMethodDao).endTransaction();
        verify(mExpenseBankCardDao).endTransaction();
        verify(mOtherPaymentMethodDao).endTransaction();
        verify(mBankCardDao).endTransaction();

    }

    private void verifyNeverInvokeSetTransactionsSuccessful() {
        verify(mExpenseDao, never()).setTransactionSuccessful();
        verify(mExpenseOtherPaymentMethodDao, never()).setTransactionSuccessful();
        verify(mExpenseBankCardDao, never()).setTransactionSuccessful();
        verify(mOtherPaymentMethodDao, never()).setTransactionSuccessful();
        verify(mBankCardDao, never()).setTransactionSuccessful();
    }

}
