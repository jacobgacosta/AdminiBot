package io.dojogeek.adminibot.views;

import android.os.Bundle;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import io.dojogeek.adminibot.R;
import io.dojogeek.adminibot.adapters.DebitCardAdapter;
import io.dojogeek.adminibot.dtos.DebitCardDto;

public class DebitCardsActivity extends BaseActivity {

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        this.initializeViews();

        this.prepareView();
    }

    @Override
    public int getToolbarTitle() {
        return R.string.title_empty;
    }

    @Override
    public int getLayout() {
        return R.layout.activity_debit_cards;
    }

    private void prepareView() {
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);

        List<DebitCardDto> debitCards = (List<DebitCardDto>) getIntent().getExtras().getSerializable("debit_card");
        DebitCardAdapter debitCardAdapter = new DebitCardAdapter(debitCards);

        mRecyclerView.setAdapter(debitCardAdapter);
    }

    private void initializeViews() {
        mRecyclerView = findViewById(R.id.recycler_view_cards);
    }

}
