package io.dojogeek.adminibot.views;

import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import dagger.AdminiBotComponent;
import dagger.AdminiBotModule;
import dagger.AppComponent;
import io.dojogeek.adminibot.R;
import io.dojogeek.adminibot.adapters.CardTypeAdapter;
import io.dojogeek.adminibot.enums.CardTypeEnum;
import io.dojogeek.adminibot.utils.LaunchIntents;

import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.Matchers.notNull;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.powermock.api.mockito.PowerMockito.doNothing;
import static org.powermock.api.mockito.PowerMockito.mockStatic;
import static org.powermock.api.mockito.PowerMockito.verifyStatic;

@RunWith(PowerMockRunner.class)
@PrepareForTest({LaunchIntents.class,  View.OnClickListener.class})
public class CardCreationActivityTest {

    @Mock
    private AppComponent mAppComponent;

    @Mock
    private AdminiBotComponent mAdminiBotComponent;

    @Mock
    private ListView mCardTypes;

    @InjectMocks
    @Spy
    private CardCreationActivity mCardCreationActivity = new CardCreationActivity();

    @Test
    public void  testGetLayout_returnCorrectLayout() {

        int actualLayout = mCardCreationActivity.getLayoutActivity();

        assertThat(actualLayout, is(not(0)));
        assertThat(actualLayout, is(R.layout.activity_card_creation));
    }

    @Test
    public void testSetupComponent_injectActivity() {

        when(mAppComponent.plus((AdminiBotModule) notNull())).thenReturn(mAdminiBotComponent);

        mCardCreationActivity.setupComponent(mAppComponent);

        verify(mAdminiBotComponent).inject(mCardCreationActivity);
    }

    @Test
    public void testLoadViews_setViews() {

        doReturn(mCardTypes).when(mCardCreationActivity).findViewById(R.id.card_types);

        mCardCreationActivity.loadViews();

        verify(mCardCreationActivity).findViewById(R.id.card_types);

    }

    @Test
    public void testLoadCardTypes_listCardTypes() {

        Mockito.doNothing().when(mCardCreationActivity).setTitle(R.string.title_activity_card_types);

        mCardCreationActivity.loadDataView();

        verify(mCardTypes).setAdapter(((CardTypeAdapter) notNull()));

        verify(mCardCreationActivity).setTitle(R.string.title_activity_card_types);
    }

    @Test
    public void testAddListenerToView() {

        mCardCreationActivity.addListenersToViews();

        verify(mCardTypes).setOnItemClickListener(mCardCreationActivity);

    }

    @Test
    public void testOnItemClick_launchCardCreationByType() {

        AdapterView adapterView = mock(AdapterView.class);

        View view = mock(View.class);

        mockStatic(LaunchIntents.class);

        doNothing().when(LaunchIntents.class);

        LaunchIntents.launchIntentClearTop(mCardCreationActivity, Class.class);

        for (CardTypeEnum cardTypeEnum : CardTypeEnum.values()) {

            doReturn(cardTypeEnum).when(view).getTag();

            mCardCreationActivity.onItemClick(adapterView, view, 0, 0);

        }

        verifyStatic();

        LaunchIntents.launchIntentClearTop(mCardCreationActivity, CreditCardActivity.class);

//        verifyStatic();

//        LaunchIntents.launchIntentClearTop(mCardCreationActivity, CardCreationActivity.class);

        verify(view, times(CardTypeEnum.values().length)).getTag();

    }
}
