package io.dojogeek.adminibot.adapters;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import io.dojogeek.adminibot.R;
import io.dojogeek.adminibot.adapters.dtos.DtoCreditCardAdapter;
import io.dojogeek.adminibot.exceptions.RecyclerItemException;
import io.dojogeek.adminibot.listeners.RecyclerItemOnClickListener;

public class MyCreditCardsAdapter extends RecyclerView.Adapter<MyCreditCardsAdapter.ViewHolder> {

    public static final String DRAWABLE = "drawable";
    private List<DtoCreditCardAdapter> mDtoCreditCards;
    private static Context sContext;

    public MyCreditCardsAdapter(Context context, List<DtoCreditCardAdapter> creditCards)
            throws RecyclerItemException {

        mDtoCreditCards = creditCards;
        sContext = validateListenerContext(context);

    }

    @Override
    public MyCreditCardsAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).
                inflate(R.layout.my_credit_card_item, parent, false);

        ViewHolder viewHolder = new ViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.cardId = mDtoCreditCards.get(position).getCardId();
        holder.creditCardBankImageName.
                setImageDrawable(getDrawableFromName(mDtoCreditCards.
                        get(position).getCreditCardBankImageName()));
        holder.creditCardName.setText(mDtoCreditCards.get(position).getCreditCardName());
        holder.creditCardNumber.setText(mDtoCreditCards.get(position).getCreditCardNumber());
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    @Override
    public int getItemCount() {
        return mDtoCreditCards.size();
    }


    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public long cardId;
        public ImageView creditCardBankImageName;
        public TextView creditCardName;
        public TextView creditCardNumber;

        public ViewHolder(View itemView) {
            super(itemView);

            creditCardBankImageName = (ImageView) itemView.findViewById(R.id.credit_card_bank_image);
            creditCardName = (TextView) itemView.findViewById(R.id.credit_card_name);
            creditCardNumber = (TextView) itemView.findViewById(R.id.credit_card_number);

            setListenerToView(itemView);
        }

        private void setListenerToView(View itemView) {
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            ((RecyclerItemOnClickListener) sContext).onClick(view, cardId);
        }
    }

    private Drawable getDrawableFromName(String nameResource) {

        Resources resources = sContext.getResources();
        int drawableId = resources.getIdentifier(nameResource, DRAWABLE, sContext.getPackageName());

        Drawable drawable = ResourcesCompat.getDrawable(resources, drawableId, null);

        return drawable;
    }

    private Context validateListenerContext(Context context) throws RecyclerItemException {

        if (!(context instanceof RecyclerItemOnClickListener)) {
            throw new RecyclerItemException("The view must implement RecyclerItemOnClickListener interface.");
        }

        return context;
    }
}
