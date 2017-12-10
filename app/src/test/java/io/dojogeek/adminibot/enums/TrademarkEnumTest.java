package io.dojogeek.adminibot.enums;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@RunWith(MockitoJUnitRunner.class)
public class TrademarkEnumTest {

    @Test
    public void testGetTrademarkImageName_obtainingCorrect() {

        for (TrademarkEnum trademark : TrademarkEnum.values()) {

            String expectedTrademarkImageName = trademark.getImageName();

            String actualTrademark = TrademarkEnum.getTrademarkImageNameFromEnum(trademark.name());

            assertNotNull(actualTrademark);
            assertEquals(expectedTrademarkImageName, actualTrademark);
        }



    }
}
