package io.dojogeek.adminibot.daos;

import android.content.Context;
import android.database.sqlite.SQLiteConstraintException;
import android.support.test.runner.AndroidJUnit4;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;
import java.util.List;

import io.dojogeek.adminibot.enums.TypePaymentMethodEnum;
import io.dojogeek.adminibot.exceptions.DataException;
import io.dojogeek.adminibot.models.OtherPaymentMethodModel;
import io.dojogeek.adminibot.sqlite.AdminiBotSQLiteOpenHelper;
import io.dojogeek.adminibot.sqlite.PaymentMethodsContract;
import io.dojogeek.adminibot.utiltest.CreatorModels;

import static android.support.test.InstrumentationRegistry.getTargetContext;

import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;

@RunWith(AndroidJUnit4.class)
public class OtherPaymentMethodDaoImplTest {

    private static final int SUCCESS_OPERATION = 1;
    private static final long OPERATIONAL_ERROR = -1;
    private static final long NO_OPERATION = 0;

    private Context mContext;
    private OtherPaymentMethodDao mOtherPaymentMethodDao;

    @Before
    public void setup() {
        mContext = getTargetContext();
        mOtherPaymentMethodDao = new OtherPaymentMethodDaoImpl(mContext);
//        ((OtherPaymentMethodDaoImpl) mOtherPaymentMethodDao).openConnection();
    }

    @After
    public void finishTest() {
        ((OtherPaymentMethodDaoImpl) mOtherPaymentMethodDao).closeConnection();
        mContext.deleteDatabase(AdminiBotSQLiteOpenHelper.DATABASE_NAME);
    }

    @Test
    public void testCreateOtherPaymentMethod_successInsertion() {

        OtherPaymentMethodModel otherPaymentMethodModel = CreatorModels.createOtherPaymentMethodModel();

        long insertedRecordId = mOtherPaymentMethodDao.createOtherPaymentMethod(otherPaymentMethodModel);

        assertNotEquals(NO_OPERATION, insertedRecordId);
        assertNotEquals(OPERATIONAL_ERROR, insertedRecordId);
    }

    @Test(expected = NullPointerException.class)
    public void testCreateOtherPaymentMethod_withNullModel_isException() {

        OtherPaymentMethodModel otherPaymentMethodModel = null;

        mOtherPaymentMethodDao.createOtherPaymentMethod(otherPaymentMethodModel);

    }

    @Test
    public void testCreateOtherPaymentMethod_withNullRequieredField_noInsertion() {

        OtherPaymentMethodModel otherPaymentMethodModel = CreatorModels.createOtherPaymentMethodModel();
        otherPaymentMethodModel.setName(null);

        long insertedRecordId = mOtherPaymentMethodDao.createOtherPaymentMethod(otherPaymentMethodModel);

        assertThat(insertedRecordId, is(OPERATIONAL_ERROR));
    }

    @Test
    public void testGetOtherPaymentMethodById_successObtaining() throws DataException {

        OtherPaymentMethodModel expectedOtherPaymentMethodModel = CreatorModels.createOtherPaymentMethodModel();

        long insertedRecordId = mOtherPaymentMethodDao.createOtherPaymentMethod(expectedOtherPaymentMethodModel);

        OtherPaymentMethodModel actualoOtherPaymentMethodModel = mOtherPaymentMethodDao.
                getOtherPaymentMethodById(insertedRecordId);

        assertNotNull(actualoOtherPaymentMethodModel);
        assertEquals(expectedOtherPaymentMethodModel.getName(), actualoOtherPaymentMethodModel.getName());
        assertEquals(expectedOtherPaymentMethodModel.getAvailableCredit(), actualoOtherPaymentMethodModel.getAvailableCredit(), 0);
        assertEquals(expectedOtherPaymentMethodModel.getReferenceNumber(), actualoOtherPaymentMethodModel.getReferenceNumber());
        assertEquals(expectedOtherPaymentMethodModel.getTypePaymentMethod(), actualoOtherPaymentMethodModel.getTypePaymentMethod());
        assertEquals(expectedOtherPaymentMethodModel.getUserId(), actualoOtherPaymentMethodModel.getUserId());
    }

    @Test(expected = DataException.class)
    public void testGetOtherPaymentMethodById_withNonExistentId_isException() throws DataException {

        long nonExistentId = 4;

        mOtherPaymentMethodDao.getOtherPaymentMethodById(nonExistentId);

    }

    @Test
    public void testGetOtherPaymentMethods_successObtainingList() {

        int numberOfInsertions = 5;

        List<OtherPaymentMethodModel> expectedOtherPaymentMethodModelList = createOtherPaymentMethods(numberOfInsertions);

        List<OtherPaymentMethodModel> actualOtherPaymentMethodModelList = mOtherPaymentMethodDao.getOtherPaymentMethods();

        compareOtherPaymentMethodModelList(expectedOtherPaymentMethodModelList, actualOtherPaymentMethodModelList);

    }

    @Test
    public void testGetOtherPaymentMethods_withNoRecords_isEmptyList() {

        List<OtherPaymentMethodModel> actualOtherPaymentMethodModelList = mOtherPaymentMethodDao.getOtherPaymentMethods();

        assertThat(actualOtherPaymentMethodModelList.isEmpty(), is(true));
    }

    @Test
    public void testGetOtherPaymentMethodByType_successObtainingList() {

        TypePaymentMethodEnum typePaymentMethodEnum = TypePaymentMethodEnum.FOOD_COUPONS;

        int numberOfInsertions = 5;

        List<OtherPaymentMethodModel> expectedOtherPaymentMethodModelList = createOtherPaymentMethods(numberOfInsertions);

        List<OtherPaymentMethodModel> actualOtherPaymentMethodModelList =
                mOtherPaymentMethodDao.getOtherPaymentMethodByType(typePaymentMethodEnum);

        compareOtherPaymentMethodModelList(expectedOtherPaymentMethodModelList, actualOtherPaymentMethodModelList);

    }

    @Test
    public void testGetOtherPaymentMethodByType_withNoExistentType_isEmptyList() {

        TypePaymentMethodEnum typePaymentMethodEnum = TypePaymentMethodEnum.CHEQUE;

        List<OtherPaymentMethodModel> actualOtherPaymentMethodModelList =
                mOtherPaymentMethodDao.getOtherPaymentMethodByType(typePaymentMethodEnum);


        assertThat(actualOtherPaymentMethodModelList, is(notNullValue()));
        assertThat(actualOtherPaymentMethodModelList.isEmpty(), is(true));
    }

    @Test
    public void testUpdateOtherPaymentMethod_successUpdating() throws DataException {

        OtherPaymentMethodModel otherPaymentMethodModel = CreatorModels.createOtherPaymentMethodModel();

        long insertedRecordId = mOtherPaymentMethodDao.createOtherPaymentMethod(otherPaymentMethodModel);

        OtherPaymentMethodModel expectedUpdatedOtherPaymentMethodModel = changeOtherPaymentMethodValues(otherPaymentMethodModel);

        long updatedRows = mOtherPaymentMethodDao.updateOtherPaymentMethod(expectedUpdatedOtherPaymentMethodModel, insertedRecordId);

        assertEquals(SUCCESS_OPERATION, updatedRows);

        OtherPaymentMethodModel actualOtherPaymentMethodModel = mOtherPaymentMethodDao.getOtherPaymentMethodById(insertedRecordId);

        comparePaymentMethodModels(expectedUpdatedOtherPaymentMethodModel, actualOtherPaymentMethodModel);

    }

    @Test(expected = NullPointerException.class)
    public void testUpdateOtherPaymentMethod_withNullModel_isException() {

        OtherPaymentMethodModel otherPaymentMethodModel = CreatorModels.createOtherPaymentMethodModel();

        long insertedRecordId = mOtherPaymentMethodDao.createOtherPaymentMethod(otherPaymentMethodModel);

        mOtherPaymentMethodDao.updateOtherPaymentMethod(null, insertedRecordId);

    }

    @Test
    public void testUpdateOtherPaymentMethod_withNonExistentId_noUpdating() {

        OtherPaymentMethodModel otherPaymentMethodModel = CreatorModels.createOtherPaymentMethodModel();

        mOtherPaymentMethodDao.createOtherPaymentMethod(otherPaymentMethodModel);

        long nonExistentId = 5;

        long updatedRows = mOtherPaymentMethodDao.updateOtherPaymentMethod(otherPaymentMethodModel, nonExistentId);

        assertThat(updatedRows, is(NO_OPERATION));
    }

    @Test(expected = SQLiteConstraintException.class)
    public void testUpdateOtherPaymentMethod_withNullRequiredField_isException() {

        OtherPaymentMethodModel otherPaymentMethodModel = CreatorModels.createOtherPaymentMethodModel();

        long insertedRecordId = mOtherPaymentMethodDao.createOtherPaymentMethod(otherPaymentMethodModel);

        otherPaymentMethodModel.setName(null);

        long updatedRows = mOtherPaymentMethodDao.updateOtherPaymentMethod(otherPaymentMethodModel, insertedRecordId);

        assertThat(updatedRows, is(NO_OPERATION));
    }

    @Test
    public void testDeleteOtherPaymentMethod_successDeletion() {

        OtherPaymentMethodModel otherPaymentMethodModel = CreatorModels.createOtherPaymentMethodModel();

        long insertedRecordId = mOtherPaymentMethodDao.createOtherPaymentMethod(otherPaymentMethodModel);

        long deletedRows = mOtherPaymentMethodDao.deleteOtherPaymentMethod(insertedRecordId);

        assertEquals(SUCCESS_OPERATION, deletedRows);
    }

    @Test
    public void testDeleteOtherPaymentMethod_withNonExistentId_noDeletion() {

        OtherPaymentMethodModel otherPaymentMethodModel = CreatorModels.createOtherPaymentMethodModel();

        mOtherPaymentMethodDao.createOtherPaymentMethod(otherPaymentMethodModel);

        long nonExistentId = 5;

        long deletedRows = mOtherPaymentMethodDao.deleteOtherPaymentMethod(nonExistentId);

        assertThat(deletedRows, is(NO_OPERATION));

    }

    private List<OtherPaymentMethodModel> createOtherPaymentMethods(int numberOfInsertions) {

        List<OtherPaymentMethodModel> otherPaymentMethodModelList = new ArrayList<>();

        for (int index = 1; index <= numberOfInsertions;index++) {

            OtherPaymentMethodModel otherPaymentMethodModel = CreatorModels.
                    createOtherPaymentMethodModel(24000.00 + index,
                            "test other payment method " + index, "4567AKI9084" + index, TypePaymentMethodEnum.FOOD_COUPONS, 1);

            mOtherPaymentMethodDao.createOtherPaymentMethod(otherPaymentMethodModel);
            otherPaymentMethodModelList.add(otherPaymentMethodModel);
        }

        return otherPaymentMethodModelList;
    }

    private void compareOtherPaymentMethodModelList(List<OtherPaymentMethodModel> expectedOtherPaymentMethodModelList,
                                                    List<OtherPaymentMethodModel> actualOtherPaymentMethodModelList) {

        assertNotNull(actualOtherPaymentMethodModelList);
        assertTrue(!actualOtherPaymentMethodModelList.isEmpty());
        assertEquals(expectedOtherPaymentMethodModelList.size(), actualOtherPaymentMethodModelList.size());

        for (int index = 0; index < actualOtherPaymentMethodModelList.size(); index++) {

            OtherPaymentMethodModel actualOtherPaymentMethodModel =  actualOtherPaymentMethodModelList.get(index);
            OtherPaymentMethodModel expectedOtherPaymentMethodModel =  expectedOtherPaymentMethodModelList.get(index);

            comparePaymentMethodModels(expectedOtherPaymentMethodModel, actualOtherPaymentMethodModel);
        }

    }

    private void comparePaymentMethodModels(OtherPaymentMethodModel expectedOtherPaymentMethodModel,
                                            OtherPaymentMethodModel actualOtherPaymentMethodModel) {


        assertEquals(expectedOtherPaymentMethodModel.getUserId(), actualOtherPaymentMethodModel.getUserId());
        assertEquals(expectedOtherPaymentMethodModel.getReferenceNumber(), actualOtherPaymentMethodModel.getReferenceNumber());
        assertEquals(expectedOtherPaymentMethodModel.getTypePaymentMethod(), actualOtherPaymentMethodModel.getTypePaymentMethod());
        assertEquals(expectedOtherPaymentMethodModel.getAvailableCredit(), actualOtherPaymentMethodModel.getAvailableCredit(), 0);
        assertEquals(expectedOtherPaymentMethodModel.getName(), actualOtherPaymentMethodModel.getName());

    }

    private OtherPaymentMethodModel changeOtherPaymentMethodValues(OtherPaymentMethodModel otherPaymentMethodModel) {

        otherPaymentMethodModel.setUserId(2);
        otherPaymentMethodModel.setAvailableCredit(32000);
        otherPaymentMethodModel.setTypePaymentMethod(TypePaymentMethodEnum.CASH);
        otherPaymentMethodModel.setReferenceNumber("65787439KDUFI");
        otherPaymentMethodModel.setName("Other payment method test");

        return otherPaymentMethodModel;
    }
}
