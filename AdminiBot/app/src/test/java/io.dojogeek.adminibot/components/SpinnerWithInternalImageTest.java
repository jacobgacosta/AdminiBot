package io.dojogeek.adminibot.components;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.support.v4.content.res.ResourcesCompat;
import android.widget.ArrayAdapter;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import io.dojogeek.adminibot.R;
import io.dojogeek.adminibot.adapters.SpinnerBankAdapter;
import io.dojogeek.adminibot.enums.BankEnum;

import static org.hamcrest.Matchers.is;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.anyString;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.powermock.api.mockito.PowerMockito.whenNew;

@RunWith(PowerMockRunner.class)
@PrepareForTest({SpinnerBankAdapter.class, SpinnerWithInternalImage.class})
public class SpinnerWithInternalImageTest {

    private static final int HINT_TEST = R.string.add_card_banks_hint;

    private static final int NUMBER_OF_HINT_ADDED = 1;

    @Mock
    private Context mContext;

    @InjectMocks
    @Spy
    private SpinnerWithInternalImage mSpinner = new SpinnerWithInternalImage(mContext);

    @Test
    public void testCreateSpinner_fullCreation() throws Exception {

        ArrayList<String> resourcesNames = mock(ArrayList.class);
        ArrayList<Integer> textItems = mock(ArrayList.class);

        Map<String, Integer> mapOfSpinnerItems =  createItemsMapFromBankEnum(BankEnum.values());

        Resources resourcesMock = mock(Resources.class);

        int resourceId = 0;

        whenNew(ArrayList.class).withNoArguments().thenReturn(textItems).thenReturn(resourcesNames);
//        when(mContext.getResources()).thenReturn(resourcesMock);
//        when(resourcesMock.getIdentifier(anyString(), anyString(), anyString())).thenReturn(resourceId);

        SpinnerBankAdapter spinnerBankAdapter = mock(SpinnerBankAdapter.class);

        doNothing().when(mSpinner).setAdapter(spinnerBankAdapter);

        whenNew(SpinnerBankAdapter.class).withArguments(mContext, resourcesNames, textItems).thenReturn(spinnerBankAdapter);

        mSpinner.createSpinner(HINT_TEST, mapOfSpinnerItems);

        verify(textItems, times(NUMBER_OF_HINT_ADDED)).add(HINT_TEST);
        verify(mSpinner).setAdapter(spinnerBankAdapter);
        verify(resourcesNames, times(mapOfSpinnerItems.size())).add(anyString());

    }

    @Test
    public void testCreateSpinner_withNoHint() throws Exception {

        ArrayList<String> resourcesNames = mock(ArrayList.class);
        ArrayList<Integer> textItems = mock(ArrayList.class);

        whenNew(ArrayList.class).withNoArguments().thenReturn(textItems).thenReturn(resourcesNames);

        SpinnerBankAdapter spinnerBankAdapter = mock(SpinnerBankAdapter.class);

        doNothing().when(mSpinner).setAdapter(spinnerBankAdapter);

        whenNew(SpinnerBankAdapter.class).withArguments(mContext, resourcesNames, textItems).thenReturn(spinnerBankAdapter);

        mSpinner.createSpinner(0, new HashMap<String, Integer>());

        verify(textItems, never()).add(0);
        verify(mSpinner).setAdapter(spinnerBankAdapter);
        verify(resourcesNames, never()).add(anyString());

    }

    private Map<String, Integer> createItemsMapFromBankEnum(BankEnum [] banks) {

        Map<String, Integer> banksMap = new HashMap<>();

        for (BankEnum bank : banks) {
            banksMap.put(bank.getImageName(), bank.getName());
        }

        return banksMap;
    }

}
