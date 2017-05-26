package io.dojogeek.adminibot.components;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import io.dojogeek.adminibot.R;

public class ConceptOfIncomeDialog extends DialogFragment {

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        final View view = getActivity()
                .getLayoutInflater()
                .inflate(R.layout.dialog_concept_of_income, null);

        final AlertDialog dialog = new AlertDialog.Builder(getActivity())
                .setView(view)
                .setPositiveButton(R.string.concept_of_income_accept, null)
                .setNegativeButton(R.string.concept_of_income_cancel, null)
                .create();

        dialog.setOnShowListener(new DialogInterface.OnShowListener() {

            @Override
            public void onShow(DialogInterface dialogInterface) {

                Button acceptButton = dialog.getButton(AlertDialog.BUTTON_POSITIVE);
                acceptButton.setOnClickListener(new View.OnClickListener() {

                    EditText concept = (EditText) view.findViewById(R.id.concept_of_income);

                    TextInputLayout inputLayoutConcept = (TextInputLayout) view.findViewById(R.id.text_input_layout_concept);

                    @Override
                    public void onClick(View view) {

                        if (TextUtils.isEmpty(concept.getText())) {
                            inputLayoutConcept.setError(getString(R.string.concept_of_income_error));
                            return;
                        }

                        SharedPreferences sharedPreferences = getActivity().getApplicationContext()
                                .getSharedPreferences(getString(R.string.pm_preference_file_key),
                                                      Context.MODE_PRIVATE);

                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putString(getString(R.string.concept_of_income), concept.getText().toString());
                        editor.commit();

                    }

                });

                Button cancelButton = dialog.getButton(AlertDialog.BUTTON_NEGATIVE);
                cancelButton.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View view) {

                        getActivity().onBackPressed();

                    }

                });

            }

        });

        return dialog;

    }

}
