package io.dojogeek.adminibot.views;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TextInputLayout;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import io.dojogeek.adminibot.R;
import io.dojogeek.adminibot.adapters.MovementsAdapter;
import io.dojogeek.adminibot.components.CashDialogFragment;
import io.dojogeek.adminibot.components.FoodCouponDialogFragment;
import io.dojogeek.adminibot.dtos.DebitCardDto;
import io.dojogeek.adminibot.dtos.MovementDto;
import io.dojogeek.adminibot.enums.TypePaymentMethodEnum;

public class MovementsActivity extends BaseActivity implements View.OnClickListener,
        AdapterView.OnItemClickListener, CashDialogFragment.Acceptable, FoodCouponDialogFragment.Acceptable {

    private EditText mIncomeConcept;
    private TextInputLayout mTextInputLayoutIncomeConcept;
    private ListView mIncomeMovements;
    private FloatingActionButton mUpdateMovements;

    @Override
    public int getToolbarTitle() {
        return R.string.title_empty;
    }

    @Override
    public int getLayout() {
        return R.layout.activity_movements;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.button_update_movements:
                String incomeConcept = mIncomeConcept.getText().toString();

                if (incomeConcept == null || incomeConcept.isEmpty()) {
                    mTextInputLayoutIncomeConcept.setError(getString(R.string.error_concept_of_income));
                    mTextInputLayoutIncomeConcept.requestFocus();
                }
                break;
        }
    }

    public void setListener() {
        mUpdateMovements.setOnClickListener(this);
        mIncomeMovements.setOnItemClickListener(this);
    }

    @Override
    public void acceptCashAmount(BigDecimal amount) {

    }

    @Override
    public void acceptFoodCouponAmount(BigDecimal amount) {

    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        switch ((TypePaymentMethodEnum) view.getTag()) {
            case CASH:
                CashDialogFragment cashDialogFragment = new CashDialogFragment();
                cashDialogFragment.show(getSupportFragmentManager(), "cashDialog");
                Bundle cashBundle = new Bundle();
                cashBundle.putSerializable("cash", getIntent().getSerializableExtra("cash"));
                cashDialogFragment.setArguments(cashBundle);
                break;
            case FOOD_COUPONS:
                FoodCouponDialogFragment foodCouponDialogFragment = new FoodCouponDialogFragment();
                foodCouponDialogFragment.show(getSupportFragmentManager(), "foodCouponsDialog");
                Bundle foodCouponsBundle = new Bundle();
                foodCouponsBundle.putSerializable("food_coupons", getIntent().getSerializableExtra("food_coupons"));
                foodCouponDialogFragment.setArguments(foodCouponsBundle);
                break;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        this.initializeViews();

        this.prepareView();

        this.setListener();
    }

    private void initializeViews() {
        mIncomeConcept = findViewById(R.id.edit_concept_of_income);
        mIncomeMovements = findViewById(R.id.list_incomes_movements);
        mUpdateMovements = findViewById(R.id.button_update_movements);
        mTextInputLayoutIncomeConcept = findViewById(R.id.text_input_layout_concept);
    }

    private void prepareView() {
        if (getIntent().getExtras() != null) {
            String incomeConcept = getIntent().getStringExtra("income_concept");

            if (incomeConcept != null) {
                mIncomeConcept.setText(incomeConcept);
                mIncomeConcept.setSelection(incomeConcept.length());
            }

            List<MovementDto> movements = this.fillListMovementsFrom(getIntent());

            MovementsAdapter movementsAdapter = new MovementsAdapter(this, movements);

            mIncomeMovements.setAdapter(movementsAdapter);
        }
    }

    private List<MovementDto> fillListMovementsFrom(Intent intent) {
        List<MovementDto> movements = new ArrayList<>();

        Serializable cash = intent.getSerializableExtra("cash");

        if (cash != null) {
            movements.add(this.createMovementDto(TypePaymentMethodEnum.CASH, (BigDecimal) cash, null));

        }

        Serializable foodCoupons = intent.getSerializableExtra("food_coupons");

        if (foodCoupons != null) {
            movements.add(this.createMovementDto(TypePaymentMethodEnum.FOOD_COUPONS, (BigDecimal) foodCoupons, null));
        }


        Serializable debitCards = intent.getSerializableExtra("debit_card");

        if (debitCards != null) {
            BigDecimal total = new BigDecimal(0.0);

            for (DebitCardDto debitCardDto : (List<DebitCardDto>) debitCards) {
                total = total.add(new BigDecimal(debitCardDto.getAmount()));
            }
            movements.add(this.createMovementDto(TypePaymentMethodEnum.DEBIT_CARD, total, debitCards));
        }


        return movements;
    }

    private MovementDto createMovementDto(TypePaymentMethodEnum type, BigDecimal total, Object extra) {
        MovementDto movementDto = new MovementDto();
        movementDto.setType(type);
        movementDto.setTotal(
                movementDto.getTotal().setScale(2, BigDecimal.ROUND_HALF_EVEN).add(total)
        );
        movementDto.setExtra(extra);

        return movementDto;
    }

}
