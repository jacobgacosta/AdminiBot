package io.dojogeek.adminibot.views;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.util.List;

import io.dojogeek.adminibot.R;
import io.dojogeek.adminibot.models.ExpenseModel;

public class ExpensesInboxActivity extends AppCompatActivity implements ExpensesInbox {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_expenses_inbox);
    }

    @Override
    public void listExpenses(List<ExpenseModel> expenseModelList) {

    }

    @Override
    public void successfulDeletion() {

    }

    @Override
    public void errorDeletion() {

    }

    @Override
    public void showExpenseDetail() {

    }

}
