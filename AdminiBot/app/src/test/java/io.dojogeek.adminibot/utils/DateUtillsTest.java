package io.dojogeek.adminibot.utils;

import org.junit.Test;

import java.text.SimpleDateFormat;
import java.util.Date;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

public class DateUtillsTest {

    @Test
    public void dateUtils_currentDate_isNotNull() {
        assertNotNull(DateUtils.getCurrentData());
    }

    @Test
    public void dateUtils_currentDate_correctFormat() {

        String date = new SimpleDateFormat("yyyy-MM-dd").format(new Date());

        assertEquals(date, DateUtils.getCurrentData());
    }

}
