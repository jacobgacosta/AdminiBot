package io.dojogeek.adminibot.views;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import java.util.List;

import io.dojogeek.adminibot.R;
import io.dojogeek.adminibot.enums.CardTypeEnum;
import io.dojogeek.adminibot.enums.TypePaymentMethodEnum;
import io.dojogeek.adminibot.models.BankCardModel;
import io.dojogeek.adminibot.models.OtherPaymentMethodModel;

public class ExpenseCreationActivity extends AppCompatActivity implements ExpenseCreation, View.OnClickListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_expense_creation);
    }

    @Override
    public void successfulExpenseCreation() {

    }

    @Override
    public void errorExpenseCreation() {

    }

    @Override
    public void onClick(View view) {

    }
}
