package io.dojogeek.adminibot.adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import io.dojogeek.adminibot.R;
import io.dojogeek.adminibot.dtos.DebitCardDto;

public class DebitCardAdapter extends RecyclerView.Adapter<DebitCardAdapter.ViewHolder> {

    private List<DebitCardDto> mDebitCardDtos;

    public DebitCardAdapter(List<DebitCardDto> debitCardDtos) {
        this.mDebitCardDtos = debitCardDtos;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater
                .from(parent.getContext())
                .inflate(R.layout.card_bank_card, parent, false);

        ViewHolder viewHolder = new ViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        String number = mDebitCardDtos.get(position).getNumber();

        holder.mCardNumber.setText(
                number.substring(0, 4).concat("    ")
                        + number.substring(4, 8).concat("    ")
                        + number.substring(8, 12).concat("    ")
                        + number.substring(12, 16)
        );
    }

    @Override
    public int getItemCount() {
        return mDebitCardDtos.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView mCardNumber;

        public ViewHolder(View itemView) {
            super(itemView);

            mCardNumber = itemView.findViewById(R.id.text_card_number);
        }
    }

}
