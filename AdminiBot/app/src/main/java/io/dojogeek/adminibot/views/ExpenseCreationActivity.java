package io.dojogeek.adminibot.views;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.util.List;

import io.dojogeek.adminibot.R;
import io.dojogeek.adminibot.enums.CardTypeEnum;
import io.dojogeek.adminibot.enums.TypePaymentMethodEnum;
import io.dojogeek.adminibot.models.BankCardModel;
import io.dojogeek.adminibot.models.OtherPaymentMethodModel;

public class ExpenseCreationActivity extends AppCompatActivity implements ExpenseCreation{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_expense_creation);
    }

    @Override
    public void showNotification(int stringResource) {

    }

    @Override
    public void selectCard(List<BankCardModel> bankCardModelList) {

    }

    @Override
    public void registerBankCard(CardTypeEnum cardTypeEnum) {

    }

    @Override
    public void selectOtherPaymentMethod(List<OtherPaymentMethodModel> otherPaymentMethodModelList) {

    }

    @Override
    public void registerOtherPaymentMethod(TypePaymentMethodEnum typePaymentMethodEnum) {

    }
}
