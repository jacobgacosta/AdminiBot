package io.dojogeek.adminibot.views;

import android.content.Intent;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.support.v4.content.res.ResourcesCompat;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import dagger.AdminiBotComponent;
import dagger.AdminiBotModule;
import dagger.AppComponent;
import io.dojogeek.adminibot.R;
import io.dojogeek.adminibot.adapters.MyCreditCardsAdapter;
import io.dojogeek.adminibot.dtos.DtoCreditCardDetail;
import io.dojogeek.adminibot.factory.ModelsFactory;
import io.dojogeek.adminibot.presenters.CreditCardDetailPresenter;

import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.powermock.api.mockito.PowerMockito.whenNew;

@RunWith(PowerMockRunner.class)
@PrepareForTest(CreditCardDetailActivity.class)
public class CreditCardDetailActivityTest {

    private ModelsFactory factory = new ModelsFactory();

    private TextView mCardName = mock(TextView.class);
    private TextView mBadge = mock(TextView.class);
    private TextView mCurrentAmount = mock(TextView.class);
    private TextView mCardNumber = mock(TextView.class);
    private TextView mBank = mock(TextView.class);
    private TextView mBadgeAvailableCredit = mock(TextView.class);
    private TextView mAvailableCredit = mock(TextView.class);
    private TextView mBadgeCreditCardLimit = mock(TextView.class);
    private TextView mCreditCardLimit = mock(TextView.class);
    private TextView mBadgeCurrentBalance = mock(TextView.class);
    private TextView mCurrentBalance = mock(TextView.class);
    private TextView mCutoffDate = mock(TextView.class);
    private ImageView mTrademarkImage = mock(ImageView.class);
    private ImageView mCutoffDateImage = mock(ImageView.class);
    private TextView mPayDayLimit = mock(TextView.class);
    private Button mMakeSpending = mock(Button.class);

    @Mock
    public CreditCardDetailPresenter creditCardDetailPresenter;

    @InjectMocks
    @Spy
    private CreditCardDetailActivity mCreditCardDetailActivity = new CreditCardDetailActivity();

//    @Test
//    public void testOnCreate_noSetContentView() {
//
//        Bundle bundleMock = mock(Bundle.class);
//
//        BDDMockito.willCallRealMethod().willNothing().given(creditCardDetailActivity).onCreate(bundleMock);
//
//        creditCardDetailActivity.onCreate(bundleMock);
//    }

    @Test
    public void testLoadDataView_getCreditCardById() {

        doNothing().when(mCreditCardDetailActivity).setTitle(R.string.title_activity_credit_card_detail);

        Intent intentMock = mock(Intent.class);
        doReturn(intentMock).when(mCreditCardDetailActivity).getIntent();
        long creditCardIdTest = 1L;
        doReturn(creditCardIdTest).when(intentMock).getLongExtra(MyCreditCardsActivity.CREDIT_CARD_ID, 0);

        mCreditCardDetailActivity.loadDataView();

        verify(mCreditCardDetailActivity).getIntent();
        verify(intentMock).getLongExtra(MyCreditCardsActivity.CREDIT_CARD_ID, 0);
        verify(creditCardDetailPresenter).loadCreditCardDetailById(creditCardIdTest);
    }

    @Test
    public void  testGetLayout_returnCorrectLayout() {

        int actualLayout = mCreditCardDetailActivity.getLayoutActivity();

        assertThat(actualLayout, is(not(0)));
        assertThat(actualLayout, is(R.layout.activity_credit_card_detail));

    }

    @Test
    public void testSetupComponent_injectActivity() throws Exception {

        AdminiBotComponent adminiBotComponent = mock(AdminiBotComponent.class);

        AppComponent appComponentMock = mock(AppComponent.class);

        AdminiBotModule adminiBotModuleMock = mock(AdminiBotModule.class);
        whenNew(AdminiBotModule.class).withArguments(mCreditCardDetailActivity).thenReturn(adminiBotModuleMock);

        when(appComponentMock.plus(adminiBotModuleMock)).thenReturn(adminiBotComponent);

        mCreditCardDetailActivity.setupComponent(appComponentMock);

        verify(appComponentMock).plus(adminiBotModuleMock);
        verify(adminiBotComponent).inject(mCreditCardDetailActivity);
    }

    @Test
    public void testLoadViews_setViews() {

        doReturn(mCardName).when(mCreditCardDetailActivity).findViewById(R.id.credit_card_detail_name);
        doReturn(mBadge).when(mCreditCardDetailActivity).
                findViewById(R.id.credit_card_detail_badge_current_amount);
        doReturn(mCurrentAmount).when(mCreditCardDetailActivity).
                findViewById(R.id.credit_card_detail_current_amount);
        doReturn(mCardNumber).when(mCreditCardDetailActivity).
                findViewById(R.id.credit_card_detail_number);
        doReturn(mBank).when(mCreditCardDetailActivity).
                findViewById(R.id.credit_card_detail_bank);
        doReturn(mTrademarkImage).when(mCreditCardDetailActivity).
                findViewById(R.id.credit_card_detail_trademark);
        doReturn(mBadgeAvailableCredit).when(mCreditCardDetailActivity).
                findViewById(R.id.credit_card_detail_badge_available_credit);
        doReturn(mAvailableCredit).when(mCreditCardDetailActivity).
                findViewById(R.id.credit_card_detail_available_credit);
        doReturn(mBadgeCreditCardLimit).when(mCreditCardDetailActivity).
                findViewById(R.id.credit_card_detail_badge_credit_limit);
        doReturn(mCreditCardLimit).when(mCreditCardDetailActivity).
                findViewById(R.id.credit_card_detail_credit_limit);
        doReturn(mBadgeCurrentBalance).when(mCreditCardDetailActivity).
                findViewById(R.id.credit_card_detail_badge_current_balance);
        doReturn(mCurrentBalance).when(mCreditCardDetailActivity).
                findViewById(R.id.credit_card_detail_current_balance);
        doReturn(mCutoffDate).when(mCreditCardDetailActivity).
                findViewById(R.id.credit_card_detail_cuttoff_date);
        doReturn(mCutoffDate).when(mCreditCardDetailActivity).
                findViewById(R.id.credit_card_detail_cuttoff_date);
        doReturn(mCutoffDateImage).when(mCreditCardDetailActivity).
                findViewById(R.id.credit_card_detail_alarm);
        doReturn(mPayDayLimit).when(mCreditCardDetailActivity).
                findViewById(R.id.credit_card_detail_payday_limit);
        doReturn(mMakeSpending).when(mCreditCardDetailActivity).findViewById(R.id.make_spending);

        mCreditCardDetailActivity.loadViews();

        verify(mCreditCardDetailActivity).findViewById(R.id.credit_card_detail_name);
        verify(mCreditCardDetailActivity).findViewById(R.id.credit_card_detail_badge_current_amount);
        verify(mCreditCardDetailActivity).findViewById(R.id.credit_card_detail_current_amount);
        verify(mCreditCardDetailActivity).findViewById(R.id.credit_card_detail_number);
        verify(mCreditCardDetailActivity).findViewById(R.id.credit_card_detail_bank);
        verify(mCreditCardDetailActivity).findViewById(R.id.credit_card_detail_trademark);
        verify(mCreditCardDetailActivity).findViewById(R.id.credit_card_detail_badge_available_credit);
        verify(mCreditCardDetailActivity).findViewById(R.id.credit_card_detail_available_credit);
        verify(mCreditCardDetailActivity).findViewById(R.id.credit_card_detail_badge_credit_limit);
        verify(mCreditCardDetailActivity).findViewById(R.id.credit_card_detail_credit_limit);
        verify(mCreditCardDetailActivity).findViewById(R.id.credit_card_detail_badge_current_balance);
        verify(mCreditCardDetailActivity).findViewById(R.id.credit_card_detail_current_balance);
        verify(mCreditCardDetailActivity).findViewById(R.id.credit_card_detail_cuttoff_date);
        verify(mCreditCardDetailActivity).findViewById(R.id.credit_card_detail_alarm);
        verify(mCreditCardDetailActivity).findViewById(R.id.credit_card_detail_payday_limit);
        verify(mCreditCardDetailActivity).findViewById(R.id.make_spending);

    }

    @Test
    public void testLoadDataView_setTitle() {

        doNothing().when(mCreditCardDetailActivity).setTitle(R.string.title_activity_credit_card_detail);

        mCreditCardDetailActivity.loadDataView();

        verify(mCreditCardDetailActivity).setTitle(R.string.title_activity_credit_card_detail);

    }

    @Test
    public void testShowCreditCardDetail_showData() {

        DtoCreditCardDetail creditCardDetail = factory.createCreditCardDetail();

        Resources resourcesMock = mock(Resources.class);
        doReturn(resourcesMock).when(mCreditCardDetailActivity).getResources();
        String packageName = "io.dojogeek";
        doReturn(packageName).when(mCreditCardDetailActivity).getPackageName();
        int drawableId = 0;
        when(resourcesMock.getIdentifier(creditCardDetail.getTrademarkImageName(),
                MyCreditCardsAdapter.DRAWABLE, packageName)).
                thenReturn(drawableId);
        PowerMockito.mockStatic(ResourcesCompat.class);
        Drawable mockDrawable = mock(Drawable.class);
        given(ResourcesCompat.getDrawable(resourcesMock, drawableId, null)).willReturn(mockDrawable);

        mCreditCardDetailActivity.showCreditCardDetail(creditCardDetail);

        verify(mCardName).setText(creditCardDetail.getCreditCardName());
        verify(mCurrentAmount).setText(creditCardDetail.getCurrentBalance());
        verify(mCardNumber).setText(creditCardDetail.getCreditCardNumber());
        verify(mBank).setText(creditCardDetail.getBankName());
        verify(mTrademarkImage).setImageDrawable(mockDrawable);
        verify(mAvailableCredit).setText(creditCardDetail.getAvailableCredit());
        verify(mCreditCardLimit).setText(creditCardDetail.getCreditLimit());
        verify(mCurrentBalance).setText(creditCardDetail.getCurrentBalance());
        verify(mCutoffDate).setText(creditCardDetail.getCutoffDate());
        verify(mPayDayLimit).setText(creditCardDetail.getPayDayLimit());

    }

}
