package io.dojogeek.adminibot.views;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import io.dojogeek.adminibot.R;
import io.dojogeek.adminibot.adapters.SpinnerWithHint;
import io.dojogeek.adminibot.dtos.DebitCardDto;
import io.dojogeek.adminibot.utils.Images;
import io.dojogeek.adminibot.validators.DebitCardValidator;

import static android.content.Intent.FLAG_ACTIVITY_REORDER_TO_FRONT;

public class DebitCardActivity extends BaseActivity {

    private EditText mName;
    private Spinner mBanks;
    private EditText mNumber;
    private EditText mAmount;
    private Button mAccept;

    @Override
    public int getToolbarTitle() {
        return R.string.title_debit_card;
    }

    @Override
    public int getContentView() {
        return R.layout.activity_debit_card;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        this.initializeViews();

        this.setListeners();

        this.prepareView();
    }

    private void initializeViews() {
        mName = findViewById(R.id.edit_card_name);
        mBanks = findViewById(R.id.spinner_banks);
        mNumber = findViewById(R.id.edit_card_number);
        mAmount = findViewById(R.id.edit_card_amount);
        mAccept = findViewById(R.id.btn_add_debit_card);
    }

    private void prepareView() {
        mNumber.setCompoundDrawablesWithIntrinsicBounds(null, null,
                Images.resizeImage(this, R.drawable.ic_bank_card, 110, 105), null);

        SpinnerWithHint adapter = new SpinnerWithHint(this, android.R.layout.simple_spinner_dropdown_item);
        adapter.addAll(getResources().getStringArray(R.array.banks_array));

        mBanks.setAdapter(adapter);
    }

    private void setListeners() {
        mAccept.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                DebitCardActivity.this.processDebitCard();
            }
        });
    }

    private void processDebitCard() {
        DebitCardDto debitCard = new DebitCardDto();
        debitCard.setName(mName.getText().toString());
        debitCard.setNumber(mNumber.getText().toString());
        debitCard.setAmount(mAmount.getText().toString());

        DebitCardValidator validator = new DebitCardValidator(this, debitCard);

        if (validator.isValid()) {
            this.backToPaymentMethodsActivity();
        }

        this.showErrorFrom(validator);

    }

    private void backToPaymentMethodsActivity() {
        Intent intent = new Intent(DebitCardActivity.this, PaymentMethodsActivity.class);
        intent.addFlags(FLAG_ACTIVITY_REORDER_TO_FRONT);
        intent.putExtra("debit_card", new DebitCardDto());
        startActivity(intent);
    }

    private void showErrorFrom(DebitCardValidator validator) {
        if (!validator.isValid("name")) {
            this.showError(mName, R.string.msg_error_empty_name);
        } else if (!validator.isValid("number")) {
            this.showError(mNumber, R.string.msg_error_empty_card_number);
        } else if (!validator.isValid("amount")) {
            this.showError(mAmount, R.string.msg_error_empty_card_amount);
        }
    }

    private void showError(EditText editText, int error) {
        editText.setError(getString(error));
        editText.requestFocus();
    }

}
