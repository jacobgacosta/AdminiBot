package io.dojogeek.adminibot.components;

import android.content.Context;
import android.content.res.Resources;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import io.dojogeek.adminibot.R;
import io.dojogeek.adminibot.adapters.SpinnerBankAdapter;
import io.dojogeek.adminibot.enums.BankEnum;

import static org.mockito.Matchers.anyLong;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.doNothing;
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
        ArrayList<Long> ids = mock(ArrayList.class);

        Map<Long, Map<String, Integer>> mapOfSpinnerItems =  createItemsMapFromBankEnum(BankEnum.values());

        int count = 5;

        whenNew(ArrayList.class).withNoArguments().thenReturn(ids).thenReturn(textItems).
                thenReturn(resourcesNames);

        SpinnerBankAdapter spinnerBankAdapter = mock(SpinnerBankAdapter.class);
        whenNew(SpinnerBankAdapter.class).withArguments(mContext, ids, resourcesNames, textItems).thenReturn(spinnerBankAdapter);
        when(spinnerBankAdapter.getCount()).thenReturn(count);

        doNothing().when(mSpinner).setAdapter(spinnerBankAdapter);
        doNothing().when(mSpinner).setSelection(count);


        mSpinner.createSpinner(HINT_TEST, mapOfSpinnerItems);

        verify(ids, times(mapOfSpinnerItems.size())).add(anyLong());
        verify(textItems, times(NUMBER_OF_HINT_ADDED)).add(HINT_TEST);
        verify(resourcesNames, times(mapOfSpinnerItems.size())).add(anyString());
        verify(mSpinner).setAdapter(spinnerBankAdapter);
        verify(mSpinner).setSelection(count);

    }

    @Test
    public void testCreateSpinner_withNoHint() throws Exception {

        ArrayList<String> resourcesNames = mock(ArrayList.class);
        ArrayList<Integer> textItems = mock(ArrayList.class);
        ArrayList<Long> ids = mock(ArrayList.class);

        whenNew(ArrayList.class).withNoArguments().thenReturn(ids).thenReturn(textItems).
                thenReturn(resourcesNames);

        SpinnerBankAdapter spinnerBankAdapter = mock(SpinnerBankAdapter.class);
        whenNew(SpinnerBankAdapter.class).withArguments(mContext, ids, resourcesNames, textItems).thenReturn(spinnerBankAdapter);

        doNothing().when(mSpinner).setAdapter(spinnerBankAdapter);
        doNothing().when(mSpinner).setSelection(0);


        mSpinner.createSpinner(0, new HashMap<Long, Map<String, Integer>>());

        verify(textItems, never()).add(0);
        verify(mSpinner).setAdapter(spinnerBankAdapter);
        verify(resourcesNames, never()).add(anyString());

    }

    private Map<Long, Map<String, Integer>> createItemsMapFromBankEnum(BankEnum [] banks) {

        Map<Long, Map<String, Integer>> banksMap = new HashMap<>();

        for (int index = 0; index < banks.length; index++) {
            Map<String, Integer> imageNameAndTextItem = new HashMap<>();
            imageNameAndTextItem.put(banks[index].getImageName(), banks[index].getName());
            banksMap.put(new Long(index), imageNameAndTextItem);
        }

        return banksMap;
    }

}
