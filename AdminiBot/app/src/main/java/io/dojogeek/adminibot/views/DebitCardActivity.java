package io.dojogeek.adminibot.views;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.Spinner;

import io.dojogeek.adminibot.R;
import io.dojogeek.adminibot.adapters.SpinnerWithHint;
import io.dojogeek.adminibot.utils.Images;

public class DebitCardActivity extends BaseActivity {

    private EditText mName;
    private Spinner mBanks;
    private EditText mNumber;
    private EditText mAmount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        this.instantiateViews();

        this.loadViewData();
    }

    @Override
    public int getToolbarTitle() {
        return R.string.title_debit_card;
    }

    @Override
    public int getContentView() {
        return R.layout.activity_debit_card;
    }

    private void instantiateViews() {
        mName = (EditText) findViewById(R.id.edit_card_name);
        mBanks = (Spinner) findViewById(R.id.spinner_banks);
        mNumber = (EditText) findViewById(R.id.edit_card_number);
        mAmount = (EditText) findViewById(R.id.edit_card_amount);
    }

    private void loadViewData() {
        mNumber.setCompoundDrawablesWithIntrinsicBounds(null, null,
                Images.resizeImage(this, R.drawable.ic_bank_card, 110, 105), null);

        SpinnerWithHint adapter = new SpinnerWithHint(this, android.R.layout.simple_spinner_dropdown_item);
        adapter.addAll(getResources().getStringArray(R.array.banks_array));


        mBanks.setAdapter(adapter);
    }
}
