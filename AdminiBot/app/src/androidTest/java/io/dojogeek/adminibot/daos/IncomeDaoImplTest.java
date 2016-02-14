package io.dojogeek.adminibot.daos;

import android.content.Context;
import android.support.test.runner.AndroidJUnit4;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import io.dojogeek.adminibot.models.IncomeModel;
import io.dojogeek.adminibot.sqlite.AdminiBotSQLiteOpenHelper;
import io.dojogeek.adminibot.utiltest.CreatorModels;

import static android.support.test.InstrumentationRegistry.getTargetContext;

import static org.junit.Assert.*;

@RunWith(AndroidJUnit4.class)
public class IncomeDaoImplTest {

    private static final int OPERATIONAL_ERROR = -1;
    private static final int NO_OPERATION = 0;

    private Context mContext;
    private IncomeDao mIncomeDao;

    @Before
    public void setup() {
        mContext = getTargetContext();
        mIncomeDao = new IncomeDaoImpl(mContext);
        mIncomeDao.openConection();
    }

    @After
    public void finishTest() {
        mIncomeDao.closeConection();
        mContext.deleteDatabase(AdminiBotSQLiteOpenHelper.DATABASE_NAME);
    }

    @Test
    public void incomeDao_createIncome_isTrue() {

        IncomeModel incomeModel = CreatorModels.createIncomeModel();

        long insertedRecordId = mIncomeDao.createIncome(incomeModel);

        assertNotEquals(NO_OPERATION, insertedRecordId);
        assertNotEquals(OPERATIONAL_ERROR, insertedRecordId);

    }

    @Test
    public void incomeDao_creationAndObtainingIncomeById_isTrue() {

        IncomeModel expectedIncomeModel = CreatorModels.createIncomeModel();

        long insertedRecordId = mIncomeDao.createIncome(expectedIncomeModel);

        IncomeModel actualIncome = mIncomeDao.getIncomeById(insertedRecordId);

        assertNotNull(actualIncome);
        assertEquals(expectedIncomeModel.description, actualIncome.description);
        assertEquals(expectedIncomeModel.amount, actualIncome.amount, 0);
        assertEquals(expectedIncomeModel.date, actualIncome.date);
        assertEquals(expectedIncomeModel.nextDate, actualIncome.nextDate);
        assertEquals(expectedIncomeModel.userId, actualIncome.userId);
    }

}
