package io.dojogeek.adminibot.components;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.List;

import io.dojogeek.adminibot.R;

public class CustomSpinner extends Spinner {

    private static final String DEFAULT_VALUE_SPINNER = "";
    private static final int LAST_ITEM = 1;
    private List<String> mItemsSpinner;
    private int mHint;
    private Context mContext;

    public CustomSpinner(Context context, int mode) {
        super(context, mode);
        mContext = context;
    }

    public CustomSpinner(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        getAttributes(context, attrs);
    }

    public CustomSpinner(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        getAttributes(context, attrs);
    }

    public void setItems(List<String> items) {
        mItemsSpinner = items;
    }

    public void setHint(int resourceId) {
        mHint = resourceId;
    }

    public void createSpinner() {

        setHintToItemsList();

        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(mContext, android.R.layout.simple_spinner_dropdown_item, mItemsSpinner) {

            @Override
            public View getView(int position, View convertView, ViewGroup parent) {

                View view = super.getView(position, convertView, parent);

                if (position == getCount()) {
                    TextView textView = (TextView) view.findViewById(android.R.id.text1);
                    textView.setTextColor(getContext().getResources().getColor(R.color.light_grey));
                    textView.setHint(getItem(getCount()));
                }

                return view;
            }

            @Override
            public int getCount() {
                return super.getCount() - LAST_ITEM;
            }


        };

        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        this.setAdapter(dataAdapter);
        this.setSelection(dataAdapter.getCount());
    }

    private void setHintToItemsList() {
        if (mHint != 0) {
            mItemsSpinner.add(getResources().getString(mHint));
        }
    }

    private void getAttributes(Context context, AttributeSet attrs) {

        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.CustomSpinner);
        mHint = typedArray.getResourceId(R.styleable.CustomSpinner_hint, 0);
        typedArray.recycle();

    }

}
