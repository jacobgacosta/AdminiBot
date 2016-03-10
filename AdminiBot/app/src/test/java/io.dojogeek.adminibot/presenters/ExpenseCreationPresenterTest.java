package io.dojogeek.adminibot.presenters;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;

import io.dojogeek.adminibot.R;
import io.dojogeek.adminibot.daos.BankCardDao;
import io.dojogeek.adminibot.daos.ExpenseDao;
import io.dojogeek.adminibot.daos.ExpenseDaoImpl;
import io.dojogeek.adminibot.daos.OtherPaymentMethodDao;
import io.dojogeek.adminibot.enums.CardTypeEnum;
import io.dojogeek.adminibot.enums.TypePaymentMethodEnum;
import io.dojogeek.adminibot.exceptions.DataException;
import io.dojogeek.adminibot.models.BankCardModel;
import io.dojogeek.adminibot.models.ExpenseModel;
import io.dojogeek.adminibot.models.OtherPaymentMethodModel;
import io.dojogeek.adminibot.views.ExpenseCreation;

import static org.junit.Assert.*;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class ExpenseCreationPresenterTest {

    @Mock
    private ExpenseCreation mExpenseCreationActivity;

    @Mock
    private ExpenseDaoImpl mExpenseDao;

    @Mock
    private OtherPaymentMethodDao mOtherPaymentMethodDao;

    @Mock
    private BankCardDao mBankCardDao;

    @InjectMocks
    private ExpenseCreationPresenter mExpenseCreationPresenter =
            new ExpenseCreationPresenterImpl(mExpenseCreationActivity, mExpenseDao,
                    mOtherPaymentMethodDao, mBankCardDao);

    @Test
    public void testCreateExpense_successfulCreation() {

        long insertedRecordId = 1;
        ExpenseModel expenseModel = new ExpenseModel();

        when(mExpenseDao.createExpense(expenseModel)).thenReturn(insertedRecordId);

        mExpenseCreationPresenter.createExpense(expenseModel);

        verify(mExpenseDao).createExpense(expenseModel);
        verify(mExpenseCreationActivity).showNotification(R.string.expenses_success_creation);

    }

    @Test
    public void testCreateExpense_creationError() {

        long insertionError = -1;
        ExpenseModel expenseModel = new ExpenseModel();

        when(mExpenseDao.createExpense(expenseModel)).thenReturn(insertionError);

        mExpenseCreationPresenter.createExpense(expenseModel);

        verify(mExpenseDao).createExpense(expenseModel);
        verify(mExpenseCreationActivity).showNotification(R.string.expenses_error_creation);
    }

    @Test
    public void testGetBankCardByCartType_successfulObtainingCreditCardsList() {

        List<BankCardModel> expectedBankCardModels = new ArrayList<>();
        expectedBankCardModels.add(new BankCardModel());
        expectedBankCardModels.add(new BankCardModel());

        CardTypeEnum cardTypeEnum = CardTypeEnum.CREDIT_CARD;

        when(mBankCardDao.getBankCardByCartType(eq(cardTypeEnum))).thenReturn(expectedBankCardModels);

        mExpenseCreationPresenter.loadOrCreateBankCardsByCardType(cardTypeEnum);

        verify(mBankCardDao).getBankCardByCartType(cardTypeEnum);
        verify(mExpenseCreationActivity).selectCard(expectedBankCardModels);
    }

    @Test
    public void testGetBankCardByCartType_obtainingEmptyCreditCardsList() {

        List<BankCardModel> expectedBankCardModels = new ArrayList<>();

        CardTypeEnum cardTypeEnum = CardTypeEnum.CREDIT_CARD;

        when(mBankCardDao.getBankCardByCartType(eq(cardTypeEnum))).thenReturn(expectedBankCardModels);

        mExpenseCreationPresenter.loadOrCreateBankCardsByCardType(cardTypeEnum);

        verify(mBankCardDao).getBankCardByCartType(cardTypeEnum);
        verify(mExpenseCreationActivity).registerBankCard(cardTypeEnum);

    }

    @Test
    public void testGetOtherPaymentMethodsByType_successfulObtainingCashList() {

        List<OtherPaymentMethodModel> otherPaymentMethodModelList = new ArrayList<>();
        otherPaymentMethodModelList.add(new OtherPaymentMethodModel());
        otherPaymentMethodModelList.add(new OtherPaymentMethodModel());

        TypePaymentMethodEnum typePaymentMethodEnum = TypePaymentMethodEnum.CASH;

        when(mOtherPaymentMethodDao.getOtherPaymentMethodByType(typePaymentMethodEnum)).
                thenReturn(otherPaymentMethodModelList);

        mExpenseCreationPresenter.loadOrCreateOtherPaymentMethodByType(typePaymentMethodEnum);

        verify(mOtherPaymentMethodDao).getOtherPaymentMethodByType(typePaymentMethodEnum);
        verify(mExpenseCreationActivity).selectOtherPaymentMethod(otherPaymentMethodModelList);

    }

    @Test
    public void testGetOtherPaymentMethodsByType_obtainingEmptyOtherPaymentMethodsList() {

        List<OtherPaymentMethodModel> otherPaymentMethodModelList = new ArrayList<>();

        TypePaymentMethodEnum typePaymentMethodEnum = TypePaymentMethodEnum.CASH;

        when(mOtherPaymentMethodDao.getOtherPaymentMethodByType(typePaymentMethodEnum)).
                thenReturn(otherPaymentMethodModelList);

        mExpenseCreationPresenter.loadOrCreateOtherPaymentMethodByType(typePaymentMethodEnum);

        verify(mOtherPaymentMethodDao).getOtherPaymentMethodByType(typePaymentMethodEnum);
        verify(mExpenseCreationActivity).registerOtherPaymentMethod(typePaymentMethodEnum);
    }

}
