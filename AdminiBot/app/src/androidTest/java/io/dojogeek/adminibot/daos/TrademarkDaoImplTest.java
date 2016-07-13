package io.dojogeek.adminibot.daos;

import android.content.Context;
import android.support.test.runner.AndroidJUnit4;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.List;

import io.dojogeek.adminibot.enums.TrademarkEnum;
import io.dojogeek.adminibot.models.TrademarkModel;
import io.dojogeek.adminibot.sqlite.AdminiBotSQLiteOpenHelper;
import io.dojogeek.adminibot.sqlite.TrademarkContract;

import static android.support.test.InstrumentationRegistry.getTargetContext;

import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;

@RunWith(AndroidJUnit4.class)
public class TrademarkDaoImplTest {

    private Context mContext;
    private TrademarkDao mTrademarkDao;

    @Before
    public void setup() {

        mContext = getTargetContext();
        mTrademarkDao = new TrademarkDaoImpl(mContext);
    }

    @After
    public void finishTest() {
        ((TrademarkDaoImpl) mTrademarkDao).closeConnection();
        mContext.deleteDatabase(AdminiBotSQLiteOpenHelper.DATABASE_NAME);
    }

    @Test
    public void testCreateTrademark_successInsertion() {

        TrademarkEnum [] expectedTrademarks = TrademarkEnum.values();

        List<TrademarkModel> actualTrademarkModels = mTrademarkDao.getTrademarks();

        assertThat(actualTrademarkModels, notNullValue());
        assertThat(actualTrademarkModels.size(), is(expectedTrademarks.length));
        compareTradeMarks(expectedTrademarks, actualTrademarkModels);


    }

    private void compareTradeMarks(TrademarkEnum [] expectedTrademarks,
                                   List<TrademarkModel> actualTrademarkModels) {

        for (int index = 0; index < expectedTrademarks.length; index++) {

            String expectedName = expectedTrademarks[index].getName();
            String expectedImageName = expectedTrademarks[index].getImageName();

            String actualName = actualTrademarkModels.get(index).getName();
            String actualImageName = actualTrademarkModels.get(index).getImageName();

            assertThat(actualName, is(expectedName));
            assertThat(actualImageName, is(expectedImageName));
        }
    }
}

