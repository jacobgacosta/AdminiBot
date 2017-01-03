package io.dojogeek.adminibot.components;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.Context;
import android.content.res.Resources;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.dojogeek.adminibot.R;

public class PaymentMethod extends LinearLayout {

    private static final String TAG = "PaymentMethod";
    private static int KEY_TAG_BUTTON = R.id.payment_methods_tag_id;

    private CustomSpinner mSpinner;
    private Button mButton;
    private LinearLayout mGroup;
    private int mCounter = 0;
    private int mId = 0;
    private Map<Integer, RelativeLayout> mAddedItems = new HashMap();
    private List<String> mSpinnerItems;

    public PaymentMethod(Context context) {
        super(context);
        inflateView();
    }

    public PaymentMethod(Context context, AttributeSet attrs) {
        super(context, attrs);
        inflateView();
    }

    public PaymentMethod(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        inflateView();
    }

    public void createSpinner(List<String>  items, int resourceId) {

        mSpinnerItems = items;

        mSpinner.setItems(mSpinnerItems);
        mSpinner.setHint(resourceId);
        mSpinner.createSpinner();

    }

    public Map<Integer, RelativeLayout> getAddedItems() {
        return mAddedItems;
    }

    @Override
    protected void onFinishInflate() {

        super.onFinishInflate();

        mSpinner = (CustomSpinner) findViewById(R.id.mandayory_payment_method);
        mButton = (Button) findViewById(R.id.add_payment_method);
        mButton.setOnClickListener(onClickListener());
        mGroup = (LinearLayout) findViewById(R.id.group);

    }

    private void inflateView() {
        inflate(getContext(), R.layout.register_payment_method, this);
    }

    private int getSpinnerSize() {
        return mSpinnerItems.size() - 1;
    }

    private OnClickListener onClickListener() {

        OnClickListener onClickListener = new OnClickListener() {
            @Override
            public void onClick(View view) {

                int idView = view.getId();

                switch (idView) {
                    case R.id.add_payment_method:
                        addGroupView();
                        incrementCounter();
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

    private int getViewId() {
        return ++mId;
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

        RelativeLayout containerLayout = (RelativeLayout) inflate(getContext(), R.layout.extra_payment_method, null);
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

        CustomSpinner spinner = (CustomSpinner) parentLayout.findViewById(R.id.added_payment_method);
        spinner.setPadding(dpToPx(20), 0, dpToPx(10), 0);
        spinner.setLayoutParams(layoutParams);
        spinner.setItems(mSpinnerItems);
        spinner.createSpinner();

        return spinner;
    }

    private LayoutParams getSpinnerLayoutParams() {
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, dpToPx(45));
        lp.setMargins(dpToPx(16), dpToPx(8), dpToPx(16), 0);

        return lp;
    }

    private EditText inflateEditText(View parentLayout) {

        LayoutParams layoutParams = getEditTextLayoutParams();

        EditText editText = (EditText) parentLayout.findViewById(R.id.added_amount);
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

    private void addToView(RelativeLayout relativeLayout) {
        mGroup.addView(relativeLayout);
    }

    private void addToCollection(int idView, RelativeLayout relativeLayout) {
        mAddedItems.put(idView, relativeLayout);
        Log.v(TAG, " saved id:" + idView + " size saved items " + mAddedItems.size());
    }

    private void incrementCounter() {
        ++mCounter;
    }

    private void checkItemLimit() {
        if (createdItems() == getSpinnerSize()) {
            hiddenAddButton();
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
        mAddedItems.remove(idToremove);
        Log.v(TAG, " remove id:" + idToremove + " size saved items " + mAddedItems.size());
    }

    private void decrementCounter() {
        mCounter--;
    }

    private void checkAvailableItems() {
        if (createdItems() < getSpinnerSize()) {
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
