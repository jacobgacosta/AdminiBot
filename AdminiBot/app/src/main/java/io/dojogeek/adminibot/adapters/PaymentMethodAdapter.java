package io.dojogeek.adminibot.adapters;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.support.v4.content.res.ResourcesCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import io.dojogeek.adminibot.R;
import io.dojogeek.adminibot.enums.TypePaymentMethodEnum;

public class PaymentMethodAdapter extends ArrayAdapter<TypePaymentMethodEnum> {

    private Context mContext;
    private List<TypePaymentMethodEnum> mTypePaymentMethodEnumList;

    public PaymentMethodAdapter(Context context, List<TypePaymentMethodEnum> objects) {
        super(context, 0, objects);

        mContext = context;
        mTypePaymentMethodEnumList = objects;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater layoutInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View rootView = layoutInflater.inflate(R.layout.item_payment_method, parent, false);

        TextView description = (TextView) rootView.findViewById(R.id.payment_method_description);
        description.setText(getResouceStringFromSimpleString(mTypePaymentMethodEnumList.get(position).getName()));

        ImageView imgPaymentMethod = (ImageView) rootView.findViewById(R.id.img_payment_method);
        imgPaymentMethod.setImageDrawable(getDrawableFromName(mTypePaymentMethodEnumList.get(position).getName()));

        return rootView;
    }

    private String getResouceStringFromSimpleString(String resourceId) {

        Resources resources = mContext.getResources();
        int stringId = resources.getIdentifier(resourceId, "string", mContext.getPackageName());

        return resources.getString(stringId);
    }

    private Drawable getDrawableFromName(String nameResource) {

        Resources resources = mContext.getResources();
        int drawableId = resources.getIdentifier(nameResource, "drawable", mContext.getPackageName());

        Drawable drawable = ResourcesCompat.getDrawable(resources, drawableId, null);

        return drawable;
    }
}
