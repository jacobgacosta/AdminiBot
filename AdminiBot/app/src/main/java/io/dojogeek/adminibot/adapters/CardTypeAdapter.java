package io.dojogeek.adminibot.adapters;

import android.content.Context;
import android.content.res.Resources;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import io.dojogeek.adminibot.R;
import io.dojogeek.adminibot.enums.CardTypeEnum;

public class CardTypeAdapter extends ArrayAdapter<CardTypeEnum> {

    private Context mContext;
    private List<CardTypeEnum> mCardTypeEnumList;

    public CardTypeAdapter(Context context, List<CardTypeEnum> objects) {
        super(context, 0, objects);

        mContext = context;
        mCardTypeEnumList = objects;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater layoutInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View rootView = layoutInflater.inflate(R.layout.item_card_types, parent, false);

        TextView cardName = (TextView) rootView.findViewById(R.id.card_name);
        cardName.setText(getResouceStringFromSimpleString(mCardTypeEnumList.get(position).getCardType()));

        return rootView;
    }

    private String getResouceStringFromSimpleString(String resourceId) {

        Resources resources = mContext.getResources();
        int stringId = resources.getIdentifier(resourceId, "string", mContext.getPackageName());

        return resources.getString(stringId);
    }
}
