package io.dojogeek.adminibot.daos;

import android.content.Context;
import android.support.test.runner.AndroidJUnit4;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import io.dojogeek.adminibot.models.MovementExpenseBankCardModel;
import io.dojogeek.adminibot.sqlite.AdminiBotSQLiteOpenHelper;
import io.dojogeek.adminibot.utiltest.CreatorModels;

import static android.support.test.InstrumentationRegistry.getTargetContext;

import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;

@RunWith(AndroidJUnit4.class)
public class MovementExpenseBankCardDaoImplTest {

    private static final int SUCCESS_OPERATION = 1;
    private static final long OPERATIONAL_ERROR = -1;
    private static final long NO_OPERATION = 0;

    private Context mContext;
    private MovementExpenseBankCardDao mMovementExpenseBankCardDao;

    @Before
    public void setup() {
        mContext = getTargetContext();
        mMovementExpenseBankCardDao = new MovementExpenseBankCardDaoImpl(mContext);
        mMovementExpenseBankCardDao.openConection();
    }

    @After
    public void finishTest() {
        mMovementExpenseBankCardDao.closeConection();
        mContext.deleteDatabase(AdminiBotSQLiteOpenHelper.DATABASE_NAME);
    }

    @Test
    public void movementExpenseBankCardDao_creationMovementExpenseBankCard_isTrue() {

        MovementExpenseBankCardModel movementExpenseBankCardModel = CreatorModels.createMovementExpenseBankCardModel();

        long insertedRecordId = mMovementExpenseBankCardDao.createMovementExpenseBankCard(movementExpenseBankCardModel);

        assertThat(insertedRecordId, is(not(NO_OPERATION)));
        assertThat(insertedRecordId, is(not(OPERATIONAL_ERROR)));
    }

}
