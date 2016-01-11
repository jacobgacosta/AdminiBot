package io.dojogeek.adminibot.utils;

import android.app.AlertDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;

import io.dojogeek.adminibot.R;


public class AlertDialogs {

    public static void showChangeLangDialog(Context context) {

        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(context);

        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        final View dialogView = layoutInflater.inflate(R.layout.about, null);
        dialogBuilder.setView(dialogView);

        AlertDialog alertDialog = dialogBuilder.create();
        alertDialog.show();

        dialogView.findViewById(R.id.close).setOnClickListener(onClickListener(alertDialog));
    }

    private static View.OnClickListener onClickListener(final AlertDialog alertDialog) {

        return new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                int viewId = view.getId();

                switch (viewId) {
                    case R.id.close:
                        alertDialog.hide();
                        break;
                }
            }
        };

    }

}
