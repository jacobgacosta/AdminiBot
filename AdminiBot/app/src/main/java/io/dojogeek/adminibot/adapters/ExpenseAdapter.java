package io.dojogeek.adminibot.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import java.util.List;

import io.dojogeek.adminibot.R;
import io.dojogeek.adminibot.models.ExpenseModel;

public class ExpenseAdapter extends ArrayAdapter<ExpenseModel> {

    public ExpenseAdapter(Context context, int resource, List<ExpenseModel> objects) {
        super(context, resource, objects);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ExpenseModel expense = getItem(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_expense_list , parent, false);
        }

        convertView.findViewById(R.id.expense_description);
        convertView.findViewById(R.id.expense_amount);
        convertView.findViewById(R.id.payment_method_detail);
        convertView.findViewById(R.id.expense_date);

        return convertView;

    }
}
