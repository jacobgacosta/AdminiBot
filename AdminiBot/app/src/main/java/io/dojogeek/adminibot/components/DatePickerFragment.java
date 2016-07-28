package io.dojogeek.adminibot.components;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatDialogFragment;
import android.widget.DatePicker;
import android.widget.EditText;

import java.util.Calendar;

import io.dojogeek.adminibot.R;

public class DatePickerFragment extends AppCompatDialogFragment implements DatePickerDialog.OnDateSetListener {

    private int mIdWidgetContainerDate;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        Calendar calendar = Calendar.getInstance();

        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        return new DatePickerDialog(getActivity(), this, year, month, day);
    }

    @Override
    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {

        String date = String.format("%1$d-%2$d-%3$d", year, monthOfYear, dayOfMonth);

        if (mIdWidgetContainerDate == 0) {
            EditText editText = (EditText) getActivity().findViewById(R.id.cuttoff_date);
            editText.setText(date);
        } else {
            EditText editText = (EditText) getActivity().findViewById(mIdWidgetContainerDate);
            editText.setText(date);
        }
    }

    public void setIdWidgetContainerDate(int idWidgetContainerDate) {
        mIdWidgetContainerDate = idWidgetContainerDate;
    }
}
