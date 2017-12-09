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
import io.dojogeek.adminibot.enums.TypePaymentMethodEnum;
import io.dojogeek.adminibot.utils.ResourceProvider;

public class PaymentMethodAdapter extends ArrayAdapter<TypePaymentMethodEnum> {

    private Context mContext;
    private List<TypePaymentMethodEnum> mPaymentMethods;

    public PaymentMethodAdapter(Context context, List<TypePaymentMethodEnum> paymentMethods) {

        super(context, 0, paymentMethods);

        mContext = context;

        mPaymentMethods = paymentMethods;

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        TypePaymentMethodEnum paymentMethod = mPaymentMethods.get(position);

        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View container = inflater.inflate(R.layout.item_payment_method, parent, false);

        TextView name = (TextView) container.findViewById(R.id.text_name_payment_method);
        name.setText(ResourceProvider.getStringFromName(mContext, paymentMethod.getStringName()));

        ImageView image = (ImageView) container.findViewById(R.id.image_payment_method);
        image.setImageDrawable(ResourceProvider.getDrawableFromName(mContext, paymentMethod.getResourceName()));

        container.setTag(mPaymentMethods.get(position));

        return container;

    }

}
