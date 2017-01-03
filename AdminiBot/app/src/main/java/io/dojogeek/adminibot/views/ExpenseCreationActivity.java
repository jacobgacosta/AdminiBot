package io.dojogeek.adminibot.views;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import io.dojogeek.adminibot.R;

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
