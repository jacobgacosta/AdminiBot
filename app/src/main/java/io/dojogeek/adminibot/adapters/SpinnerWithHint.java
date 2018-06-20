package io.dojogeek.adminibot.adapters;

import android.content.Context;
import androidx.annotation.NonNull;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

public class SpinnerWithHint extends ArrayAdapter<String> {

    public SpinnerWithHint(Context context, int resource) {
        super(context, resource);
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View view = super.getView(position, convertView, parent);

        view.setPadding(0, view.getPaddingTop(), view.getPaddingRight(), view.getPaddingBottom());

        return view;
    }

    @Override
    public int getCount() {
        return super.getCount() - 1;
    }

}
