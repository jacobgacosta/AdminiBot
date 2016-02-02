package io.dojogeek.adminibot.sqlite;

import android.database.sqlite.SQLiteDatabase;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Before;
import org.junit.runner.RunWith;

import io.dojogeek.adminibot.sqlite.utils.DataBaseConfigurationTest;
import io.dojogeek.adminibot.sqlite.utils.PaymentMethodDataUtilTest;

import static android.support.test.InstrumentationRegistry.getTargetContext;

@RunWith(AndroidJUnit4.class)
public class PaymentMethodsSQLiteOpenHelperTest {

    private SQLiteDatabase mSQLiteDatabase;
    private DataBaseConfigurationTest mDataBaseConfigurationTest;
    private PaymentMethodDataUtilTest mPaymentMethodDataUtilTest;

    @Before
    public void setup() {

        mDataBaseConfigurationTest = DataBaseConfigurationTest.getInstance(getTargetContext());
        mDataBaseConfigurationTest.prepareDataBase();

        mSQLiteDatabase = mDataBaseConfigurationTest.getSQLiteDatabase();

        loadPaymentMethodUtil();

    }

    private void loadPaymentMethodUtil() {
        mPaymentMethodDataUtilTest = new PaymentMethodDataUtilTest(mSQLiteDatabase);
    }

}
