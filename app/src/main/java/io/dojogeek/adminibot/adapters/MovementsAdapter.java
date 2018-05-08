package io.dojogeek.adminibot.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import io.dojogeek.adminibot.R;
import io.dojogeek.adminibot.dtos.MovementDto;
import io.dojogeek.adminibot.utils.ResourceProvider;

public class MovementsAdapter extends ArrayAdapter<MovementDto> {

    private Context mContext;
    private List<MovementDto> mPaymentMethods;

    public MovementsAdapter(Context context, List<MovementDto> paymentMethods) {
        super(context, 0, paymentMethods);

        this.mContext = context;
        this.mPaymentMethods = paymentMethods;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View container = inflater.inflate(R.layout.item_income_movement , parent, false);

        MovementDto movementDto = this.mPaymentMethods.get(position);

        ImageView image = container.findViewById(R.id.image_income);
        image.setImageDrawable(ResourceProvider.getDrawableFromName(mContext, movementDto.getType().getResourceName()));

        TextView name = container.findViewById(R.id.text_income_type);
        name.setText(ResourceProvider.getStringFromName(mContext, movementDto.getType().getStringName()));

        TextView totalAmount = container.findViewById(R.id.text_income_total_amount);
        totalAmount.setText("$" + movementDto.getTotal().toString());

        container.setTag(mPaymentMethods.get(position).getType());

        return container;
    }
}
