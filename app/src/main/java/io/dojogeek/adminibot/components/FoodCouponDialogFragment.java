package io.dojogeek.adminibot.components;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import java.math.BigDecimal;

import io.dojogeek.adminibot.R;
import io.dojogeek.adminibot.views.PaymentMethods;

public class FoodCouponDialogFragment extends DialogFragment {

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        this.throwExceptionIfNotAcceptable(getActivity());

        final View view = getActivity().getLayoutInflater().inflate(R.layout.dialog_food_coupon, null);

        final AlertDialog dialog = new AlertDialog.Builder(getActivity(), R.style.AdminiBotAlertDialog)
                .setView(view)
                .setPositiveButton(R.string.msg_accept, null)
                .setNegativeButton(R.string.msg_cancel, null)
                .create();

        dialog.setOnShowListener(new DialogInterface.OnShowListener() {

            @Override
            public void onShow(DialogInterface dialogInterface) {
                final EditText amount = view.findViewById(R.id.edit_food_coupon_amount);

                if (getArguments() != null) {
                    BigDecimal foodCouponsAmount = (BigDecimal) getArguments().getSerializable("food_coupons");

                    if (foodCouponsAmount.compareTo(BigDecimal.ZERO) != 0) {
                        amount.setText(foodCouponsAmount.toString());
                    }
                }

                ((InputMethodManager) getActivity()
                        .getSystemService(Context.INPUT_METHOD_SERVICE))
                        .showSoftInput(amount, InputMethodManager.SHOW_IMPLICIT);

                final TextInputLayout inputLayoutAmount = view
                        .findViewById(R.id.text_input_layout_food_coupon);

                dialog.getButton(AlertDialog.BUTTON_POSITIVE)
                        .setOnClickListener(new View.OnClickListener() {

                            @Override
                            public void onClick(View view) {
                                if (TextUtils.isEmpty(amount.getText())) {
                                    inputLayoutAmount.setError(getString(R.string.error_amount));

                                    return;
                                }

                                BigDecimal bdAmount = new BigDecimal(amount.getText().toString());
                                bdAmount = bdAmount.setScale(2, BigDecimal.ROUND_HALF_EVEN);

                                ((Acceptable) getActivity()).acceptFoodCouponAmount(bdAmount);
                                ((PaymentMethods) getActivity()).refreshTotalIncome(bdAmount);

                                dialog.dismiss();
                            }

                        });

                dialog.getButton(AlertDialog.BUTTON_NEGATIVE)
                        .setOnClickListener(new View.OnClickListener() {

                            @Override
                            public void onClick(View view) {
                                dialog.dismiss();
                            }

                        });
            }
        });

        return dialog;
    }

    private void throwExceptionIfNotAcceptable(FragmentActivity activity) {
        if (!(activity instanceof Acceptable)) {
            throw new RuntimeException("The content view need to implement Decidable interface");
        }
    }

    public interface Acceptable {
        void acceptFoodCouponAmount(BigDecimal amount);
    }

}
