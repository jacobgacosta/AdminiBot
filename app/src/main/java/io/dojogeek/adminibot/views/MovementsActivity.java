package io.dojogeek.adminibot.views;

import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.ListView;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import io.dojogeek.adminibot.R;
import io.dojogeek.adminibot.adapters.MovementsAdapter;
import io.dojogeek.adminibot.dtos.DebitCardDto;
import io.dojogeek.adminibot.dtos.MovementDto;
import io.dojogeek.adminibot.enums.TypePaymentMethodEnum;

public class MovementsActivity extends BaseActivity {

    private EditText mIncomeConcept;
    private ListView mIncomeMovements;

    @Override
    public int getToolbarTitle() {
        return R.string.title_empty;
    }

    @Override
    public int getLayout() {
        return R.layout.activity_movements;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        this.initializeViews();

        this.prepareView();
    }

    private void initializeViews() {
        mIncomeConcept = findViewById(R.id.edit_income_concept);
        mIncomeMovements = findViewById(R.id.list_incomes_movements);
    }

    private void prepareView() {
        if (getIntent().getExtras() != null) {
            String incomeConcept = getIntent().getStringExtra("income_concept");

            mIncomeConcept.setText(incomeConcept);
            mIncomeConcept.setSelection(incomeConcept.length());

            List<MovementDto> movements = this.fillListMovementsFrom(getIntent());

            MovementsAdapter movementsAdapter = new MovementsAdapter(this, movements);

            mIncomeMovements.setAdapter(movementsAdapter);
        }
    }

    private List<MovementDto> fillListMovementsFrom(Intent intent) {
        List<MovementDto> movements = new ArrayList<>();
        movements.add(this.createMovementDto(TypePaymentMethodEnum.CASH,
                (BigDecimal) intent.getSerializableExtra("cash"),
                null));
        movements.add(this.createMovementDto(TypePaymentMethodEnum.FOOD_COUPONS,
                (BigDecimal) intent.getSerializableExtra("food_coupons"),
                null));

        List<DebitCardDto> debitCards = (ArrayList) intent.getSerializableExtra("debit_card");

        BigDecimal total = new BigDecimal(0.0);

        for (DebitCardDto debitCardDto : debitCards) {
            total = total.add(new BigDecimal(debitCardDto.getAmount()));
        }
        movements.add(this.createMovementDto(TypePaymentMethodEnum.DEBIT_CARD, total, debitCards));

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
