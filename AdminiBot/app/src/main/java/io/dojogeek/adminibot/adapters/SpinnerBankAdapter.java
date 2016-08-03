package io.dojogeek.adminibot.adapters;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.support.v4.content.res.ResourcesCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;
import java.util.Map;

import io.dojogeek.adminibot.R;
import io.dojogeek.adminibot.components.SpinnerWithInternalImage;
import io.dojogeek.adminibot.enums.BankEnum;

public class SpinnerBankAdapter extends BaseAdapter {

    private static final String DRAWABLE = "drawable";
    private static final int LAST_ITEM = 1;
    private Context mContext;
    private List<String> mResourcesImagesNames;
    private List<Integer> mText;

    public SpinnerBankAdapter(Context context, List<String> resourcesImagesNames, List<Integer> text) {
        mContext = context;
        mResourcesImagesNames = resourcesImagesNames;
        mText = text;
    }

    @Override
    public int getCount() {
        return mText.size() - LAST_ITEM;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater layoutInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View rootView = layoutInflater.inflate(R.layout.item_spinner_bank, parent, false);

        ImageView image = (ImageView) rootView.findViewById(R.id.imageView);

        if (position < mResourcesImagesNames.size()) {
            image.setImageDrawable(getDrawableFromName(mResourcesImagesNames.get(position)));
            image.setVisibility(View.VISIBLE);
        }

        TextView text = (TextView) rootView.findViewById(R.id.textView);
        text.setText(mText.get(position));

        if (position == getCount()) {
            text.setTextColor(mContext.getResources().getColor(R.color.light_grey));
            text.setHint(mText.get(mText.size() - LAST_ITEM));
        }

        return rootView;
    }

    private Drawable getDrawableFromName(String nameResource) {

        Resources resources = mContext.getResources();
        int drawableId = resources.getIdentifier(nameResource, DRAWABLE, mContext.getPackageName());

        Drawable drawable = ResourcesCompat.getDrawable(resources, drawableId, null);

        return drawable;
    }
}
