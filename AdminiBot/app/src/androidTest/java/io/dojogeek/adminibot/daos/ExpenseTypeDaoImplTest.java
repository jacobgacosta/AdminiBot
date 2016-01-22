package io.dojogeek.adminibot.daos;

import android.content.Context;
import android.support.test.runner.AndroidJUnit4;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.List;

import io.dojogeek.adminibot.models.ExpenseModel;
import io.dojogeek.adminibot.models.ExpenseTypeModel;
import io.dojogeek.adminibot.sqlite.AdminiBotSQLiteOpenHelper;

import static android.support.test.InstrumentationRegistry.getTargetContext;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

@RunWith(AndroidJUnit4.class)
public class ExpenseTypeDaoImplTest {

    private ExpenseTypeDao mExpenseTypeDao;

    @Before
    public void setup() {
        Context context = getTargetContext();
        context.deleteDatabase(AdminiBotSQLiteOpenHelper.DATABASE_NAME);
        mExpenseTypeDao = new ExpenseTypeDaoImpl(context);
        mExpenseTypeDao.openConection();
    }

    @After
    public void finishTest() {
        mExpenseTypeDao.closeConection();
    }

    @Test
    public void expenseTypeDao_numberInsertedExpensesTypes_isTrue() {

        int numberOfinitialInsertedValues =  5;

        List<ExpenseTypeModel> expenseTypeModelList =  mExpenseTypeDao.getExpensesTypes();

        assertNotNull(expenseTypeModelList);
        assertTrue(!expenseTypeModelList.isEmpty());
        assertEquals(numberOfinitialInsertedValues, expenseTypeModelList.size());

    }
}
