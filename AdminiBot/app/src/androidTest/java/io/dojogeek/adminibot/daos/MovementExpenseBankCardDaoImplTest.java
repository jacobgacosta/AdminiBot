package io.dojogeek.adminibot.daos;

import android.content.Context;
import android.support.test.runner.AndroidJUnit4;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;
import java.util.List;

import io.dojogeek.adminibot.exceptions.DataException;
import io.dojogeek.adminibot.models.MovementExpenseBankCardModel;
import io.dojogeek.adminibot.sqlite.AdminiBotSQLiteOpenHelper;
import io.dojogeek.adminibot.utils.DateUtils;
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

    @Test(expected = NullPointerException.class)
    public void movementExpenseBankCardDao_creationMovementExpenseBankCardWithNullModel_isException() {

        mMovementExpenseBankCardDao.createMovementExpenseBankCard(null);

    }

    @Test
    public void movementExpenseBankCardDao_creationMovementExpenseBankCardWithNullRequiredField_isFalse() {

        MovementExpenseBankCardModel movementExpenseBankCardModel = CreatorModels.createMovementExpenseBankCardModel();
        movementExpenseBankCardModel.description = null;

        long insertedRecordId = mMovementExpenseBankCardDao.createMovementExpenseBankCard(movementExpenseBankCardModel);

        assertThat(insertedRecordId, is(OPERATIONAL_ERROR));
    }

    @Test
    public void movementExpenseBankCardDao_creationAndObtainingMovementExpenseBankCardById_isTrue() throws DataException {

        MovementExpenseBankCardModel expectedMovementExpenseBankCardModel = CreatorModels.createMovementExpenseBankCardModel();

        long insertedRecordId = mMovementExpenseBankCardDao.createMovementExpenseBankCard(expectedMovementExpenseBankCardModel);

        MovementExpenseBankCardModel actualMovementExpenseBankCardModel = mMovementExpenseBankCardDao.
                getMovementExpenseBankCardById(insertedRecordId);

        assertThat(actualMovementExpenseBankCardModel, notNullValue());
        assertThat(actualMovementExpenseBankCardModel.amount, is(expectedMovementExpenseBankCardModel.amount));
        assertThat(actualMovementExpenseBankCardModel.bankCardId, is(expectedMovementExpenseBankCardModel.bankCardId));
        assertThat(actualMovementExpenseBankCardModel.date, is(expectedMovementExpenseBankCardModel.date));
        assertThat(actualMovementExpenseBankCardModel.description, is(expectedMovementExpenseBankCardModel.description));
        assertThat(actualMovementExpenseBankCardModel.expenseId, is(expectedMovementExpenseBankCardModel.expenseId));
    }

    @Test(expected = DataException.class)
    public void movementExpenseBankCardDao_obtainingMovementExpenseBankCardWithInexistentId_isFalse() throws DataException {

        int movementExpenseBankCardId = 2;
        mMovementExpenseBankCardDao.getMovementExpenseBankCardById(movementExpenseBankCardId);

    }


}
