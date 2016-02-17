package io.dojogeek.adminibot.daos;

import android.content.Context;
import android.support.test.runner.AndroidJUnit4;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;
import java.util.List;

import io.dojogeek.adminibot.enums.TypePaymentMethodEnum;
import io.dojogeek.adminibot.models.OtherPaymentMethodModel;
import io.dojogeek.adminibot.sqlite.AdminiBotSQLiteOpenHelper;
import io.dojogeek.adminibot.utiltest.CreatorModels;

import static android.support.test.InstrumentationRegistry.getTargetContext;

import static org.junit.Assert.*;

@RunWith(AndroidJUnit4.class)
public class OtherPaymentMethodDaoImplTest {

    private static final int SUCCESS_OPERATION = 1;
    private static final int OPERATIONAL_ERROR = -1;
    private static final int NO_OPERATION = 0;

    private Context mContext;
    private OtherPaymentMethodDao mOtherPaymentMethodDao;

    @Before
    public void setup() {
        mContext = getTargetContext();
        mOtherPaymentMethodDao = new OtherPaymentMethodDaoImpl(mContext);
        mOtherPaymentMethodDao.openConection();
    }

    @After
    public void finishTest() {
        mOtherPaymentMethodDao.closeConection();
        mContext.deleteDatabase(AdminiBotSQLiteOpenHelper.DATABASE_NAME);
    }

    @Test
    public void otherPaymentMethod_creationOtherPaymentMethod_isTrue() {

        OtherPaymentMethodModel otherPaymentMethodModel = CreatorModels.createOtherPaymentMethodModel();

        long insertedRecordId = mOtherPaymentMethodDao.createOtherPaymentMethod(otherPaymentMethodModel);

        assertNotEquals(NO_OPERATION, insertedRecordId);
        assertNotEquals(OPERATIONAL_ERROR, insertedRecordId);
    }

    @Test
    public void otherPaymentMethod_creationAndObtainingOtherPaymentMethodById_isTrue() {

        OtherPaymentMethodModel expectedOtherPaymentMethodModel = CreatorModels.createOtherPaymentMethodModel();

        long insertedRecordId = mOtherPaymentMethodDao.createOtherPaymentMethod(expectedOtherPaymentMethodModel);

        OtherPaymentMethodModel actualoOtherPaymentMethodModel = mOtherPaymentMethodDao.
                getOtherPaymentMethodById(insertedRecordId);

        assertNotNull(actualoOtherPaymentMethodModel);
        assertEquals(expectedOtherPaymentMethodModel.name, actualoOtherPaymentMethodModel.name);
        assertEquals(expectedOtherPaymentMethodModel.availableCredit, actualoOtherPaymentMethodModel.availableCredit, 0);
        assertEquals(expectedOtherPaymentMethodModel.referenceNumber, actualoOtherPaymentMethodModel.referenceNumber);
        assertEquals(expectedOtherPaymentMethodModel.typePaymentMethod, actualoOtherPaymentMethodModel.typePaymentMethod);
        assertEquals(expectedOtherPaymentMethodModel.userId, actualoOtherPaymentMethodModel.userId);
    }

    @Test
    public void otherPaymentMethod_creationAndObtainingAllOtherPaymentMethods_isTrue() {

        int numberOfInsertions = 5;

        List<OtherPaymentMethodModel> expectedOtherPaymentMethodModelList = createOtherPaymentMethods(numberOfInsertions);

        List<OtherPaymentMethodModel> actualOtherPaymentMethodModelList = mOtherPaymentMethodDao.getOtherPaymentMethods();

        compareOtherPaymentMethodModelList(expectedOtherPaymentMethodModelList, actualOtherPaymentMethodModelList);

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


        assertEquals(expectedOtherPaymentMethodModel.userId, actualOtherPaymentMethodModel.userId);
        assertEquals(expectedOtherPaymentMethodModel.referenceNumber, actualOtherPaymentMethodModel.referenceNumber);
        assertEquals(expectedOtherPaymentMethodModel.typePaymentMethod, actualOtherPaymentMethodModel.typePaymentMethod);
        assertEquals(expectedOtherPaymentMethodModel.availableCredit, actualOtherPaymentMethodModel.availableCredit, 0);
        assertEquals(expectedOtherPaymentMethodModel.name, actualOtherPaymentMethodModel.name);


    }
}
