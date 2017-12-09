package io.dojogeek.adminibot.views;

import android.content.Intent;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.content.res.ResourcesCompat;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import javax.inject.Inject;

import dagger.AdminiBotComponent;
import dagger.AdminiBotModule;
import dagger.AppComponent;
import io.dojogeek.adminibot.R;
import io.dojogeek.adminibot.dtos.DtoCreditCardDetail;
import io.dojogeek.adminibot.presenters.CreditCardDetailPresenter;

public class CreditCardDetailActivity extends BaseActivity2 implements CreditCardDetail {

    public static final String TAG = "CreditCardDetailActivity";
    public static final String DRAWABLE = "drawable";

    private TextView mCardName;
    private TextView mBadge;
    private TextView mCurrentAmount;
    private TextView mCardNumber;
    private TextView mBank;
    private TextView mBadgeAvailableCredit;
    private TextView mAvailableCredit;
    private TextView mBadgeCreditCardLimit;
    private TextView mCreditCardLimit;
    private TextView mBadgeCurrentBalance;
    private TextView mCurrentBalance;
    private TextView mCutoffDate;
    private ImageView mTrademarkImage;
    private ImageView mCutoffDateImage;
    private TextView mPayDayLimit;
    private Button mMakeSpending;

    @Inject
    public CreditCardDetailPresenter creditCardDetailPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void setupComponent(AppComponent appComponent) {
        AdminiBotComponent adminiBotComponent =
                appComponent.plus(new AdminiBotModule(this));
        adminiBotComponent.inject(this);

    }

    @Override
    protected void loadViews() {



    }

    @Override
    protected void addListenersToViews() {

    }

    @Override
    protected void loadDataView() {

        setTitle(R.string.title_activity_credit_card_detail);

        loadCreditCardDetailById();

    }

    @Override
    protected int getLayoutActivity() {

        return R.layout.activity_credit_card_detail;
    }

    @Override
    protected void closeConnections() {

    }

    private long getIdFromSelectedOption() {
        long defaultValue = 0;
        Intent intent = getIntent();
        long creditCardId = intent.getLongExtra(MyCreditCardsActivity.CREDIT_CARD_ID, defaultValue);
        return creditCardId;
    }

    private void loadCreditCardDetailById() {

        long creditCardId = getIdFromSelectedOption();

        creditCardDetailPresenter.loadCreditCardDetailById(creditCardId);
    }

    @Override
    public void showCreditCardDetail(DtoCreditCardDetail creditCardDetail) {

        mCardName.setText(creditCardDetail.getCreditCardName());
        mCurrentAmount.setText(creditCardDetail.getCurrentBalance());
        mCardNumber.setText(creditCardDetail.getCreditCardNumber());
        mBank.setText(creditCardDetail.getBankName());
        mAvailableCredit.setText(creditCardDetail.getAvailableCredit());
        mTrademarkImage.setImageDrawable(getDrawableFromName(creditCardDetail.getTrademarkImageName()));
        mCreditCardLimit.setText(creditCardDetail.getCreditLimit());
        mCurrentBalance.setText(creditCardDetail.getCurrentBalance());
        mCutoffDate.setText(creditCardDetail.getCutoffDate());
        mPayDayLimit.setText(creditCardDetail.getPayDayLimit());

    }

    private Drawable getDrawableFromName(String nameResource) {

        Resources resources = getResources();
        int drawableId = resources.getIdentifier(nameResource, DRAWABLE, getPackageName());

        Drawable drawable = ResourcesCompat.getDrawable(resources, drawableId, null);

        return drawable;
    }
}
