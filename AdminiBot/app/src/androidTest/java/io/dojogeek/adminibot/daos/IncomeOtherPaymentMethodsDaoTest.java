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
import io.dojogeek.adminibot.models.IncomeOtherPaymentMethodModel;
import io.dojogeek.adminibot.sqlite.AdminiBotSQLiteOpenHelper;
import io.dojogeek.adminibot.utiltest.CreatorModels;

import static android.support.test.InstrumentationRegistry.getTargetContext;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;

@RunWith(AndroidJUnit4.class)
public class IncomeOtherPaymentMethodsDaoTest {

    private static final long SUCCESS_OPERATION = 1;
    private static final long NO_OPERATION = 0;
    private static final long OPERATIONAL_ERROR = -1;
    private IncomeOtherPaymentMethodsDao mIncomeOtherPaymentMethodsDao;
    private Context mContext;

    @Before
    public void setup() {
        mContext = getTargetContext();
        mIncomeOtherPaymentMethodsDao = new IncomeOtherPaymentMethodsDaoImpl(mContext);
    }

    @After
    public void finishTest() {
        ((IncomeOtherPaymentMethodsDaoImpl) mIncomeOtherPaymentMethodsDao).closeConnection();
        mContext.deleteDatabase(AdminiBotSQLiteOpenHelper.DATABASE_NAME);
    }

    @Test
    public void testCreateIncomeOtherPaymentMethod_successInsertion() {

        IncomeOtherPaymentMethodModel incomeOtherPaymentMethodModel =
                CreatorModels.createIncomeOtherPaymentMethodModel();

        long insertedRecordId = mIncomeOtherPaymentMethodsDao.createIncomeOtherPaymentMethod(incomeOtherPaymentMethodModel);

        assertThat(insertedRecordId, is(not(NO_OPERATION)));
        assertThat(insertedRecordId, is(not(OPERATIONAL_ERROR)));
    }

    @Test(expected = NullPointerException.class)
    public void testCreateIncomeOtherPaymentMethod_withNullModel_isException() {

        IncomeOtherPaymentMethodModel incomeOtherPaymentMethodModel = null;

        mIncomeOtherPaymentMethodsDao.createIncomeOtherPaymentMethod(incomeOtherPaymentMethodModel);

    }

    @Test
    public void testGetIncomeOtherPaymentMethodById_successObtaining() throws DataException {

        IncomeOtherPaymentMethodModel expectedIncomeOtherPaymentMethodModel =
                CreatorModels.createIncomeOtherPaymentMethodModel();

        long insertedRecordId = mIncomeOtherPaymentMethodsDao.createIncomeOtherPaymentMethod(expectedIncomeOtherPaymentMethodModel);

        IncomeOtherPaymentMethodModel actualIncomeOtherPaymentMethodModel =
                mIncomeOtherPaymentMethodsDao.getIncomeOtherPaymentMethodById(insertedRecordId);

        assertThat(actualIncomeOtherPaymentMethodModel, is(notNullValue()));

        assertThat(actualIncomeOtherPaymentMethodModel.getAmount(),
                is(expectedIncomeOtherPaymentMethodModel.getAmount()));
        assertThat(actualIncomeOtherPaymentMethodModel.getIncomeId(),
                is(expectedIncomeOtherPaymentMethodModel.getIncomeId()));
        assertThat(actualIncomeOtherPaymentMethodModel.getOtherPaymentMethodId(),
                is(expectedIncomeOtherPaymentMethodModel.getOtherPaymentMethodId()));

    }

    @Test(expected = DataException.class)
    public void testGetIncomeOtherPaymentMethodById_withNonExistentId_isException() throws DataException {

        long nonExistentId = 4;

        mIncomeOtherPaymentMethodsDao.getIncomeOtherPaymentMethodById(nonExistentId);
    }

    @Test
    public void testGetIncomesOthersPaymentMethods_successObtainingList() {

        int numberOfInsertions = 5;

        List<IncomeOtherPaymentMethodModel> expectedIncomeOtherPaymentMethodModelList =
                createIncomeOtherPaymentMethodModel(numberOfInsertions);

        List<IncomeOtherPaymentMethodModel> actualIncomeOtherPaymentMethodModelList =
                mIncomeOtherPaymentMethodsDao.getIncomesOthersPaymentMethods();

        assertThat(actualIncomeOtherPaymentMethodModelList.size(),
                is(expectedIncomeOtherPaymentMethodModelList.size()));

        compareIncomesOthersPaymentMethodsModelsList(expectedIncomeOtherPaymentMethodModelList,
                actualIncomeOtherPaymentMethodModelList);
    }

    @Test
    public void testGetIncomesOthersPaymentMethods_withNoRecords_isEmptyList() {

        List<IncomeOtherPaymentMethodModel> actualIncomeOtherPaymentMethodModelList =
                mIncomeOtherPaymentMethodsDao.getIncomesOthersPaymentMethods();

        assertThat(actualIncomeOtherPaymentMethodModelList.isEmpty(), is(true));
    }

    @Test
    public void testGetIncomeOtherPaymentMethodByIncomeId_successObtainingList() {

        IncomeOtherPaymentMethodModel expectedIncomeOtherPaymentMethodModel =
                CreatorModels.createIncomeOtherPaymentMethodModel(8888.88, 1, 1);

        mIncomeOtherPaymentMethodsDao.createIncomeOtherPaymentMethod(expectedIncomeOtherPaymentMethodModel);

        List<IncomeOtherPaymentMethodModel> incomeOtherPaymentMethodModelList =
                mIncomeOtherPaymentMethodsDao.
                        getIncomeOtherPaymentMethodByIncomeId(expectedIncomeOtherPaymentMethodModel.getIncomeId());

        assertThat(incomeOtherPaymentMethodModelList.isEmpty(), is(false));
        assertThat(incomeOtherPaymentMethodModelList.size(), is(1));
        assertThat(incomeOtherPaymentMethodModelList.get(0), is(notNullValue()));
        compareIncomesOthersPaymentMethodsModels(incomeOtherPaymentMethodModelList.get(0),
                expectedIncomeOtherPaymentMethodModel);

    }

    @Test
    public void testGetIncomeOtherPaymentMethodByIncomeId_withNonExistentIncomeId_isEmptyList() {

        long nonExistentId = 3;

        List<IncomeOtherPaymentMethodModel> incomeOtherPaymentMethodModelList =
                mIncomeOtherPaymentMethodsDao.getIncomeOtherPaymentMethodByIncomeId(nonExistentId);

        assertThat(incomeOtherPaymentMethodModelList.isEmpty(), is(true));
    }

    @Test
    public void testGetIncomeOtherPaymentMethodByPaymentMethodId_successObtainingList() {

        IncomeOtherPaymentMethodModel expectedIncomeOtherPaymentMethodModel =
                CreatorModels.createIncomeOtherPaymentMethodModel(93872.99, 1, 1);

        mIncomeOtherPaymentMethodsDao.createIncomeOtherPaymentMethod(expectedIncomeOtherPaymentMethodModel);

        List<IncomeOtherPaymentMethodModel> incomeOtherPaymentMethodModelList =
                mIncomeOtherPaymentMethodsDao.
                        getIncomeOtherPaymentMethodByPaymentMethodId(expectedIncomeOtherPaymentMethodModel.getOtherPaymentMethodId());

        assertThat(incomeOtherPaymentMethodModelList.isEmpty(), is(false));
        assertThat(incomeOtherPaymentMethodModelList.size(), is(1));
        assertThat(incomeOtherPaymentMethodModelList.get(0), is(notNullValue()));
        compareIncomesOthersPaymentMethodsModels(incomeOtherPaymentMethodModelList.get(0),
                expectedIncomeOtherPaymentMethodModel);

    }

    @Test
    public void testGetIncomeOtherPaymentMethodByPaymentMethodId_withNonExistentPaymentMethodId_isEmptyList() {

        long nonExistentId = 3;

        List<IncomeOtherPaymentMethodModel> incomeOtherPaymentMethodModelList =
                mIncomeOtherPaymentMethodsDao.getIncomeOtherPaymentMethodByPaymentMethodId(nonExistentId);

        assertThat(incomeOtherPaymentMethodModelList.isEmpty(), is(true));

    }

    @Test
    public void testDeleteIncomeOtherPaymentMethod_successDeletion() {

        IncomeOtherPaymentMethodModel incomeOtherPaymentMethodModel =
                CreatorModels.createIncomeOtherPaymentMethodModel();

        long insertedRecordId =
                mIncomeOtherPaymentMethodsDao.createIncomeOtherPaymentMethod(incomeOtherPaymentMethodModel);

        long deletedRows =
                mIncomeOtherPaymentMethodsDao.deleteIncomeOtherPaymentMethod(insertedRecordId);

        assertThat(deletedRows, is(SUCCESS_OPERATION));
    }

    @Test
    public void testDeleteIncomeOtherPaymentMethod_withNonExistentId() {

        long nonExistentId = 5;

        long deletedRows =
                mIncomeOtherPaymentMethodsDao.deleteIncomeOtherPaymentMethod(nonExistentId);

        assertThat(deletedRows, is(NO_OPERATION));
    }

    private List<IncomeOtherPaymentMethodModel> createIncomeOtherPaymentMethodModel(int numberOfInsertions) {

        List<IncomeOtherPaymentMethodModel> incomeOtherPaymentMethodModelList = new ArrayList<>();

        for (int index = 0; index < numberOfInsertions; index++) {

            IncomeOtherPaymentMethodModel incomeOtherPaymentMethodModel =
                    CreatorModels.createIncomeOtherPaymentMethodModel(index, index, index);

            mIncomeOtherPaymentMethodsDao.createIncomeOtherPaymentMethod(incomeOtherPaymentMethodModel);
            incomeOtherPaymentMethodModelList.add(incomeOtherPaymentMethodModel);
        }

        return incomeOtherPaymentMethodModelList;
    }

    private void compareIncomesOthersPaymentMethodsModelsList(List<IncomeOtherPaymentMethodModel> expectedIncomeOtherPaymentMethodModelList,
                                                              List<IncomeOtherPaymentMethodModel> actualIncomeOtherPaymentMethodModelList) {

        assertThat(actualIncomeOtherPaymentMethodModelList, is(notNullValue()));
        assertThat(actualIncomeOtherPaymentMethodModelList.isEmpty(), is(false));

        for (int index = 0; index < actualIncomeOtherPaymentMethodModelList.size(); index++) {

            IncomeOtherPaymentMethodModel actualIncomeOtherPaymentMethodModel =
                    actualIncomeOtherPaymentMethodModelList.get(index);

            IncomeOtherPaymentMethodModel expectedIncomeOtherPaymentMethodModel =
                    expectedIncomeOtherPaymentMethodModelList.get(index);

            compareIncomesOthersPaymentMethodsModels(expectedIncomeOtherPaymentMethodModel,
                    actualIncomeOtherPaymentMethodModel);
        }

    }

    private void compareIncomesOthersPaymentMethodsModels(IncomeOtherPaymentMethodModel expectedIncomeOtherPaymentMethodModel,
                                                          IncomeOtherPaymentMethodModel actualIncomeOtherPaymentMethodModel) {


        assertThat(actualIncomeOtherPaymentMethodModel, is(notNullValue()));
        assertThat(actualIncomeOtherPaymentMethodModel.getOtherPaymentMethodId(),
                is(expectedIncomeOtherPaymentMethodModel.getOtherPaymentMethodId()));
        assertThat(actualIncomeOtherPaymentMethodModel.getIncomeId(),
                is(expectedIncomeOtherPaymentMethodModel.getIncomeId()));
        assertThat(actualIncomeOtherPaymentMethodModel.getAmount(),
                is(expectedIncomeOtherPaymentMethodModel.getAmount()));

    }

}
