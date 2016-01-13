package io.dojogeek.adminibot.components;

import android.animation.*;
import android.content.*;
import android.content.res.Resources;
import android.util.*;
import android.view.View;
import android.widget.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.dojogeek.adminibot.R;

public class PaymentMethod extends LinearLayout {

    private static final String TAG = "PaymentMethod";
    private static int DEFAULT_VALUE_SPINNER = R.string.expenses_types_default_value;
    private static int ONE_BEFORE = 1;
    private static int KEY_TAG_BUTTON = R.id.payment_methods_tag_id;

    private Spinner mSpinner;
    private Button mButton;
    private LinearLayout mGroup;
    private int mSpinnerSize = 4;
    private int mCounter = 0;
    private Context mContext;
    private Map<Integer, RelativeLayout> mAddedItems = new HashMap();

    public PaymentMethod(Context context) {
        super(context);
        inflateView(context);
    }

    public PaymentMethod(Context context, AttributeSet attrs) {
        super(context, attrs);
        inflateView(context);
    }

    public PaymentMethod(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        inflateView(context);
    }

    public void setItemsForSpinner(List<String> items) {

        setSpinnerSize(items.size());

        items.add(getResources().getString(DEFAULT_VALUE_SPINNER));

        ArrayAdapter<String> dataAdapter = new ArrayAdapter<>(mContext, android.R.layout.simple_spinner_item, items);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mSpinner.setAdapter(dataAdapter);

    }

    @Override
    protected void onFinishInflate() {

        super.onFinishInflate();

        mSpinner = (Spinner) findViewById(R.id.spinner);
        mButton = (Button) findViewById(R.id.add_payment_method);
        mButton.setOnClickListener(onClickListener());
        mGroup = (LinearLayout) findViewById(R.id.group);

    }

    private void inflateView(Context context) {
        mContext = context;
        inflate(getContext(), R.layout.register_payment_method, this);
    }

    private void setSpinnerSize(int size) {
        mSpinnerSize = size - ONE_BEFORE;
    }

    private OnClickListener onClickListener() {

        OnClickListener onClickListener = new OnClickListener() {
            @Override
            public void onClick(View view) {

                int idView = view.getId();

                switch (idView) {
                    case R.id.add_payment_method:
                        addGroupView();
                        checkItemLimit();
                        break;
                    case R.id.remove_button:
                        int idToRemove = (Integer) view.getTag(KEY_TAG_BUTTON);
                        removeGroupView(idToRemove);
                        decrementCounter();
                        checkAvailableItems();
                        break;
                    default:
                        Log.v(this.getClass().getName(), "no events");
                        break;
                }
            }
        };

        return onClickListener;
    }

    private void addGroupView() {

        int idView = getViewId();

        RelativeLayout relativeLayout = createGroupView(idView);

        addToView(relativeLayout);

        addToCollection(idView, relativeLayout);

    }

    private RelativeLayout createGroupView(int idComponent) {

        RelativeLayout relativeLayout = inflateContainerView(idComponent);

        inflateSpinner(relativeLayout);

        inflateEditText(relativeLayout);

        inflateImageButton(relativeLayout, idComponent);

        return relativeLayout;
    }

    private RelativeLayout inflateContainerView(int id) {

        LayoutParams layoutParams = getGroupViewLayoutParams();

        RelativeLayout containerLayout = (RelativeLayout) inflate(getContext(), R.layout.payment_method_component, null);
        containerLayout.setId(id);
        containerLayout.setLayoutParams(layoutParams);

        return containerLayout;
    }

    private LayoutParams getGroupViewLayoutParams() {

        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
        lp.setMargins(0, dpToPx(8), 0, 0);

        return lp;
    }


    private Spinner inflateSpinner(View parentLayout) {

        LayoutParams layoutParams = getSpinnerLayoutParams();

        Spinner spinner = (Spinner) parentLayout.findViewById(R.id.spinner);
        spinner.setPadding(dpToPx(20), 0, dpToPx(10), 0);
        spinner.setLayoutParams(layoutParams);

        return spinner;
    }

    private LayoutParams getSpinnerLayoutParams() {
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, dpToPx(45));
        lp.setMargins(dpToPx(16), dpToPx(8), dpToPx(16), 0);

        return lp;
    }

    private EditText inflateEditText(View parentLayout) {

        LayoutParams layoutParams = getEditTextLayoutParams();

        EditText editText = (EditText) parentLayout.findViewById(R.id.edit_text);
        editText.setPadding(dpToPx(20), 0, dpToPx(10), 0);
        editText.setLayoutParams(layoutParams);

        return editText;

    }

    private LayoutParams getEditTextLayoutParams() {
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, dpToPx(45));
        lp.setMargins(dpToPx(16), dpToPx(16), dpToPx(16), dpToPx(16));

        return lp;
    }

    private ImageButton inflateImageButton(View parentLayout, int valueTag) {

        ImageButton removeButton = (ImageButton) parentLayout.findViewById(R.id.remove_button);
        removeButton.setOnClickListener(onClickListener());
        removeButton.setTag(KEY_TAG_BUTTON, valueTag);

        return removeButton;
    }

    private int getViewId() {
        return ++mCounter;
    }

    private void addToView(RelativeLayout relativeLayout) {
        mGroup.addView(relativeLayout);
    }

    private void addToCollection(int idView, RelativeLayout relativeLayout) {
        Log.v(TAG, " saved id:" + idView);
        mAddedItems.put(idView, relativeLayout);
    }

    private void checkItemLimit() {
        if (createdItems() == mSpinnerSize) {
            hiddenAddButton();
            return;
        }
    }

    private void hiddenAddButton() {
        mButton.setVisibility(View.GONE);
    }

    private void removeGroupView(int idToremove) {

        final View view = findViewById(idToremove);

        deleteWithAnimationFromView(view);

        removeFromCollection(idToremove);

    }

    private void deleteWithAnimationFromView(final View view) {

        view.setAlpha(1.0f);
        view.animate().translationY(view.getHeight()).alpha(1.0f).setDuration(300).
                setListener(new AnimatorListenerAdapter() {

                    @Override
                    public void onAnimationEnd(Animator animation) {

                        super.onAnimationEnd(animation);

                        mGroup.removeView(view);
                    }
                });
    }

    private void removeFromCollection(int idToremove) {
        Log.v(TAG, " remove id:" + idToremove);
        mAddedItems.remove(idToremove);
    }

    private void decrementCounter() {
        mCounter--;
    }

    private void checkAvailableItems() {
        if (createdItems() < mSpinnerSize) {
            showAddButton();
            return;
        }
    }

    private int createdItems() {
        return mCounter;
    }

    private void showAddButton() {
        mButton.setVisibility(View.VISIBLE);
    }

    private int dpToPx(int dp) {
        return (int) (dp * Resources.getSystem().getDisplayMetrics().density);
    }

    private int pxToDp(int px) {
        return (int) (px / Resources.getSystem().getDisplayMetrics().density);
    }

}
