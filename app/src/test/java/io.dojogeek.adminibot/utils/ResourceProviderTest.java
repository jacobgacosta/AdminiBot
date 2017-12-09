package io.dojogeek.adminibot.utils;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.support.v4.content.res.ResourcesCompat;

import org.junit.Test;

import static junit.framework.Assert.assertNotNull;
import static org.mockito.Matchers.anyString;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.powermock.api.mockito.PowerMockito.mockStatic;
import static org.powermock.api.mockito.PowerMockito.verifyStatic;

public class ResourceProviderTest {

    @Test
    public void testGetResource_getAStringValue() {

        Context context = mock(Context.class);

        Resources resources = mock(Resources.class);
        when(context.getResources()).thenReturn(resources);

        int stringId = 0;
        when(resources.getIdentifier(eq("some_string"), eq("string"), anyString())).thenReturn(stringId);

        when(resources.getString(stringId)).thenReturn("value of string");

        String expectedValue = ResourceProvider.getStringFromName(context, "some_string");

        assertNotNull(expectedValue);
        verify(resources).getIdentifier(eq("some_string"), eq("string"), anyString());
        verify(resources).getString(stringId);
    }

    @Test
    public void testGetResource_getADrawable() {

        Context context = mock(Context.class);

        Resources resources = mock(Resources.class);
        when(context.getResources()).thenReturn(resources);

        int drawableId = 0;
        when(resources.getIdentifier(eq("some_string"), eq("drawable"), anyString())).thenReturn(drawableId);

        Drawable drawable = mock(Drawable.class);
        mockStatic(ResourcesCompat.class);
        when(ResourcesCompat.getDrawable(resources, drawableId, null)).thenReturn(drawable);

        Drawable expectedDrawable = ResourceProvider.getDrawableFromName(context, "some_string");

        assertNotNull(expectedDrawable);
        verifyStatic();
        ResourcesCompat.getDrawable(resources, drawableId, null);
    }
}
