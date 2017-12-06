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

import io.dojogeek.adminibot.R;

public class IncomeConceptDialog extends DialogFragment {

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        this.throwExceptionIfNotAcceptable(getActivity());

        final View view = getActivity().getLayoutInflater().inflate(R.layout.dialog_income_concept, null);

        final AlertDialog dialog = new AlertDialog.Builder(getActivity())
                .setView(view)
                .setPositiveButton(R.string.msg_accept, null)
                .setNegativeButton(R.string.msg_cancel, null)
                .create();

        dialog.setOnShowListener(new DialogInterface.OnShowListener() {

            @Override
            public void onShow(DialogInterface dialogInterface) {

                final EditText concept = (EditText) view.findViewById(R.id.edit_concept_of_income);

                ((InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE))
                        .showSoftInput(concept, InputMethodManager.SHOW_IMPLICIT);

                final TextInputLayout inputLayoutConcept = (TextInputLayout) view
                        .findViewById(R.id.text_input_layout_concept);

                dialog.getButton(AlertDialog.BUTTON_POSITIVE)
                        .setOnClickListener(new View.OnClickListener() {

                            @Override
                            public void onClick(View view) {

                                if (TextUtils.isEmpty(concept.getText())) {
                                    inputLayoutConcept.setError(getString(R.string.error_concept_of_income));

                                    return;
                                }

                                ((Acceptable) getActivity()).acceptIncomeConcept(concept.getText().toString());

                                dialog.dismiss();

                            }

                        });

                dialog.getButton(AlertDialog.BUTTON_NEGATIVE)
                        .setOnClickListener(new View.OnClickListener() {

                            @Override
                            public void onClick(View view) {

                                getActivity().onBackPressed();

                            }

                        });

            }

        });

        return dialog;

    }

    private void throwExceptionIfNotAcceptable(FragmentActivity activity) {

        if (!(activity instanceof Acceptable)) {
            throw new RuntimeException("The content view need to implement Acceptable interface");
        }

    }

    public interface Acceptable {

        void acceptIncomeConcept(String value);

    }

}
