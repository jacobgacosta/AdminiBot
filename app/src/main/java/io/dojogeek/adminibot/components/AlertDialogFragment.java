package io.dojogeek.adminibot.components;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.TextView;

import io.dojogeek.adminibot.R;

public class AlertDialogFragment extends DialogFragment {

    private int mText;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        final View view = getActivity().getLayoutInflater().inflate(R.layout.dialog_alert, null);
        ((TextView) view.findViewById(R.id.text_alert)).setText(mText);

        final AlertDialog dialog = new AlertDialog.Builder(getActivity())
                .setView(view)
                .setPositiveButton(R.string.msg_accept, null)
                .create();

        dialog.setOnShowListener(new DialogInterface.OnShowListener() {

            @Override
            public void onShow(DialogInterface dialogInterface) {
//                dialog.getButton(AlertDialog.BUTTON_POSITIVE)
//                        .setTextColor(ContextCompat.getColor(getActivity(), R.color.alert_dialog_button_text));
            }

        });

        return dialog;
    }

    public AlertDialogFragment setText(int text) {
        mText = text;

        return this;
    }

}
