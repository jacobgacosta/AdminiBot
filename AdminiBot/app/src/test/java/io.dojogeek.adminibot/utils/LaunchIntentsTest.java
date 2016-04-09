package io.dojogeek.adminibot.utils;

import android.content.Context;
import android.content.Intent;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.anyObject;
import static org.mockito.Matchers.anyString;
import static org.mockito.Matchers.notNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.powermock.api.mockito.PowerMockito.whenNew;

@RunWith(PowerMockRunner.class)
@PrepareForTest({LaunchIntents.class})
public class LaunchIntentsTest {

    @Mock
    private Context mContext;

    @Test
    public void testLaunchIntentClearTop() throws Exception {

        Intent intent = mock(Intent.class);

        whenNew(Intent.class).withArguments(mContext, Class.class).thenReturn(intent);

        LaunchIntents.launchIntentClearTop(mContext, Class.class);

        verify(intent).setFlags(anyInt());
        verify(mContext).startActivity(intent);
    }

    @Test
    public void testLaunchIntentWithExtraValues() throws Exception {

        Intent intent = mock(Intent.class);

        whenNew(Intent.class).withArguments(mContext, Class.class).thenReturn(intent);

        Map<String, Serializable> flags = new HashMap<>();
        flags.put("TEST_KEY", "value");
        flags.put("TEST_KEY2", "value2");
        flags.put("TEST_KEY3", "value3");

        LaunchIntents.launchIntentWithExtraValues(mContext, Class.class, flags);

        verify(intent, times(flags.size())).putExtra(anyString(), (Serializable) notNull());
        verify(mContext).startActivity(intent);
    }

}
