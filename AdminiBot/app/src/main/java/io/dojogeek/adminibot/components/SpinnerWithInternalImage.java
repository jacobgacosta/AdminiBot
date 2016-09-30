package io.dojogeek.adminibot.components;


import android.content.Context;
import android.content.Intent;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import io.dojogeek.adminibot.R;
import io.dojogeek.adminibot.adapters.SpinnerBankAdapter;

public class SpinnerWithInternalImage extends Spinner {

    private static int NO_RESOURCE = 0;
    private Context mContext;
    private int mHint;

    public SpinnerWithInternalImage(Context context) {
        super(context);
        mContext = context;
    }

    public SpinnerWithInternalImage(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        getAttributes(context, attrs);
    }

    public SpinnerWithInternalImage(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        getAttributes(context, attrs);
    }

    public void createSpinner(int resourceIdHint, Map<Long, Map<String, Integer>> imagesNamesAndTextItemsMap) {

        List<Long> ids = new ArrayList<>();

        List<Integer> textItems = new ArrayList<>();

        List<String> resourcesImages = new ArrayList<>();

        for (Map.Entry<Long, Map<String, Integer>> entry : imagesNamesAndTextItemsMap.entrySet()) {

            ids.add(entry.getKey());

            for (Map.Entry<String, Integer> items : entry.getValue().entrySet()) {
                resourcesImages.add(items.getKey());
                textItems.add(items.getValue());
            }
        }

        if (resourceIdHint != NO_RESOURCE) {
            textItems.add(resourceIdHint);
        }

        SpinnerBankAdapter adapter = new SpinnerBankAdapter(mContext, ids, resourcesImages, textItems);
        setAdapter(adapter);
        setSelection(adapter.getCount());
    }

    private void getAttributes(Context context, AttributeSet attrs) {

        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.CustomSpinner);
        mHint = typedArray.getResourceId(R.styleable.CustomSpinner_hint, 0);
        typedArray.recycle();

    }

}
