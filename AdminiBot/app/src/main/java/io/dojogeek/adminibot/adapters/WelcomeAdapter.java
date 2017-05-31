package io.dojogeek.adminibot.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import io.dojogeek.adminibot.R;

public class WelcomeAdapter extends ArrayAdapter<Integer[]> {

    private static final int IMAGE = 0;
    private static final int TITLE = 1;
    private static final int DESCRIPTION = 2;

    private Context mContext;
    private List<Integer []> mItems;

    public WelcomeAdapter(Context context, List<Integer[]> items) {

        super(context, 0, items);

        mContext = context;
        mItems = items;

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View container = inflater.inflate(R.layout.item_welcome, parent, false);

        ImageView image = (ImageView) container.findViewById(R.id.image_option);
        image.setImageResource(mItems.get(position)[IMAGE]);

        TextView title = (TextView) container.findViewById(R.id.text_option_title);
        title.setText(mItems.get(position)[TITLE]);

        TextView description = (TextView) container.findViewById(R.id.text_option_description);
        description.setText(mItems.get(position)[DESCRIPTION]);

        return container;

    }

}
