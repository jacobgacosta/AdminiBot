package io.dojogeek.adminibot.views;

import android.os.Bundle;

import io.dojogeek.adminibot.R;

public class CashIncomeActivity extends BaseActivity {

    @Override
    public int getToolbarTitle() {
        return R.string.title_activity_cash_income;
    }

    @Override
    public int getLayout() {
        return R.layout.activity_cash_income;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

}
