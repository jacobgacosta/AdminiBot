package io.dojogeek.adminibot.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import io.dojogeek.adminibot.R;
import io.dojogeek.adminibot.dtos.DtoSimpleAdapter;
import io.dojogeek.adminibot.listeners.RecyclerItemOnClickListener;

public class SimpleItemAdapter extends RecyclerView.Adapter<SimpleItemAdapter.ViewHolder> {

    private List<DtoSimpleAdapter> mSimpleAdapterList;
    private static Context sContext;

    public SimpleItemAdapter(Context context, List<DtoSimpleAdapter> simpleAdapterList) {
        mSimpleAdapterList = simpleAdapterList;
        sContext = validateListenerContext(context);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View itemView = layoutInflater.inflate(R.layout.item_simple_detail_payment_method, parent, false);

        ViewHolder viewHolder = new ViewHolder(itemView);

        return viewHolder;
    }


    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.itemId = mSimpleAdapterList.get(position).getId();
        holder.title.setText(mSimpleAdapterList.get(position).getTitle());
        holder.subtitle.setText(mSimpleAdapterList.get(position).getSubtitle()) ;

    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    @Override
    public int getItemCount() {
        return mSimpleAdapterList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public long itemId;
        public TextView title;
        public TextView subtitle;

        public ViewHolder(View itemView) {
            super(itemView);
            title = (TextView) itemView.findViewById(R.id.payment_method_title);
            subtitle = (TextView) itemView.findViewById(R.id.payment_method_subtitle);

            setListenerToView(itemView);
        }

        private void setListenerToView(View view) {
            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            ((RecyclerItemOnClickListener) sContext).onClick(view, itemId);
        }
    }

    private Context validateListenerContext(Context context) {

        if (!(context instanceof RecyclerItemOnClickListener)) {
            throw new RuntimeException(context.toString()
                    + "The view must implement RecyclerItemOnClickListener interface.");
        }

        return context;
    }
}
