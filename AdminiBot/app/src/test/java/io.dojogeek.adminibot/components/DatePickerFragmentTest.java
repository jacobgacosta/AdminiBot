package io.dojogeek.adminibot.components;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.widget.DatePicker;
import android.widget.EditText;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.BDDMockito;
import org.mockito.Spy;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import java.util.Calendar;

import io.dojogeek.adminibot.R;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.powermock.api.mockito.PowerMockito.whenNew;

@RunWith(PowerMockRunner.class)
@PrepareForTest(DatePickerFragment.class)
public class DatePickerFragmentTest {

    @Spy
    private DatePickerFragment datePickerFragment = new DatePickerFragment();

    @Test
    public void onCreateDialog_successCreation() throws Exception {

        Bundle bundlemock = mock(Bundle.class);
        Calendar mockCalendar = mock(Calendar.class);
        DatePickerDialog mockDatePickerDialog = mock(DatePickerDialog.class);

        PowerMockito.mockStatic(Calendar.class);
        BDDMockito.given(Calendar.getInstance()).willReturn(mockCalendar);

        when(mockCalendar.get(Calendar.YEAR)).thenReturn(0);
        when(mockCalendar.get(Calendar.MONTH)).thenReturn(0);
        when(mockCalendar.get(Calendar.DAY_OF_MONTH)).thenReturn(0);
        whenNew(DatePickerDialog.class).
                withArguments(datePickerFragment.getActivity(),
                        datePickerFragment, 0, 0, 0).thenReturn(mockDatePickerDialog);

        Dialog actualDialog = datePickerFragment.onCreateDialog(bundlemock);

        assertNotNull(actualDialog);
        assertEquals(actualDialog, mockDatePickerDialog);
        verify(mockCalendar).get(Calendar.YEAR);
        verify(mockCalendar).get(Calendar.MONTH);
        verify(mockCalendar).get(Calendar.DAY_OF_MONTH);

    }

    @Test
    public void testOnDateSet_setTextToDefaultEditText() {

        DatePicker mockDatePicker = mock(DatePicker.class);
        EditText mockEditText = mock(EditText.class);

        FragmentActivity mockFragmentActivity = mock(FragmentActivity.class);

        when(datePickerFragment.getActivity()).thenReturn(mockFragmentActivity);
        when(mockFragmentActivity.findViewById(R.id.cuttoff_date)).thenReturn(mockEditText);

        String date = "0-0-0";

        PowerMockito.mockStatic(String.class);
        BDDMockito.given(String.format("%1$d-%2$d-%3$d", 0, 0, 0)).willReturn(date);

        datePickerFragment.onDateSet(mockDatePicker, 0, 0, 0);

        verify(mockEditText).setText(date);
        verify(datePickerFragment).getActivity();
        verify(mockFragmentActivity).findViewById(R.id.cuttoff_date);

    }

    @Test
    public void testOnDateSet_setTextToSpecificEditText() {

        DatePicker mockDatePicker = mock(DatePicker.class);
        EditText mockEditText = mock(EditText.class);

        FragmentActivity mockFragmentActivity = mock(FragmentActivity.class);

        when(datePickerFragment.getActivity()).thenReturn(mockFragmentActivity);
        when(mockFragmentActivity.findViewById(R.id.pay_day_limit)).thenReturn(mockEditText);

        String date = "0-0-0";

        PowerMockito.mockStatic(String.class);
        BDDMockito.given(String.format("%1$d-%2$d-%3$d", 0, 0, 0)).willReturn(date);

        datePickerFragment.setIdWidgetContainerDate(R.id.pay_day_limit);
        datePickerFragment.onDateSet(mockDatePicker, 0, 0, 0);


        verify(mockEditText).setText(date);
        verify(datePickerFragment).getActivity();
        verify(mockFragmentActivity).findViewById(R.id.pay_day_limit);

    }
}
