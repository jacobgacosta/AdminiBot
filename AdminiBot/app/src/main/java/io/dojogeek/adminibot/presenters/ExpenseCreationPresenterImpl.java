package io.dojogeek.adminibot.presenters;

import java.util.ArrayList;
import java.util.List;

import io.dojogeek.adminibot.R;
import io.dojogeek.adminibot.daos.BankCardDao;
import io.dojogeek.adminibot.daos.ExpenseDao;
import io.dojogeek.adminibot.daos.OtherPaymentMethodDao;
import io.dojogeek.adminibot.enums.CardTypeEnum;
import io.dojogeek.adminibot.enums.TypePaymentMethodEnum;
import io.dojogeek.adminibot.models.BankCardModel;
import io.dojogeek.adminibot.models.ExpenseModel;
import io.dojogeek.adminibot.models.OtherPaymentMethodModel;
import io.dojogeek.adminibot.views.ExpenseCreation;

public class ExpenseCreationPresenterImpl implements ExpenseCreationPresenter {

    private static final long OPERATIONAL_ERROR = -1;
    private ExpenseCreation mExpenseCreation;
    private ExpenseDao mExpenseDao;
    private OtherPaymentMethodDao mOtherPaymentMethodDao;
    private BankCardDao mBankCardDao;

    public ExpenseCreationPresenterImpl(ExpenseCreation expenseCreation, ExpenseDao expenseDao,
                                        OtherPaymentMethodDao otherPaymentMethodDao, BankCardDao bankCardDao) {
        mExpenseCreation = expenseCreation;
        mExpenseDao = expenseDao;
        mOtherPaymentMethodDao = otherPaymentMethodDao;
        mBankCardDao = bankCardDao;
    }

    @Override
    public void createExpense(ExpenseModel expenseModel) {

        long insertedRecordId = mExpenseDao.createExpense(expenseModel);

        boolean isError = isCreationError(insertedRecordId);

        if (isError) {
            showMessage(R.string.expenses_error_creation);
        } else {
            showMessage(R.string.expenses_success_creation);
        }
    }

    @Override
    public void loadOrCreateBankCardsByCardType(CardTypeEnum cardTypeEnum) {

        List<BankCardModel> bankCardModelList = mBankCardDao.getBankCardByCartType(cardTypeEnum);

        if (bankCardModelList.isEmpty()) {

            mExpenseCreation.registerBankCard(cardTypeEnum);

        } else {

            mExpenseCreation.selectCard(bankCardModelList);
        }
    }

    @Override
    public void loadOrCreateOtherPaymentMethodByType(TypePaymentMethodEnum typePaymentMethodEnum) {

        List<OtherPaymentMethodModel> otherPaymentMethodModelList =
                mOtherPaymentMethodDao.getOtherPaymentMethodByType(typePaymentMethodEnum);

        if (otherPaymentMethodModelList.isEmpty()) {
            mExpenseCreation.registerOtherPaymentMethod(typePaymentMethodEnum);
        } else {
            mExpenseCreation.selectOtherPaymentMethod(otherPaymentMethodModelList);
        }

    }

    private boolean isCreationError(long creationCode) {

        if (creationCode == OPERATIONAL_ERROR) {
            return true;
        }

        return false;
    }

    private void showMessage(int stringResource) {
        mExpenseCreation.showNotification(stringResource);
    }

}
