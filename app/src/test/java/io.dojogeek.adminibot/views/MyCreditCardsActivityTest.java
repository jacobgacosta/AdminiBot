package io.dojogeek.adminibot.views;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
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

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import dagger.AdminiBotComponent;
import dagger.AdminiBotModule;
import dagger.AppComponent;
import io.dojogeek.adminibot.R;
import io.dojogeek.adminibot.adapters.MyCreditCardsAdapter;
import io.dojogeek.adminibot.dtos.DtoCreditCardAdapter;
import io.dojogeek.adminibot.presenters.MyCreditCardsPresenter;
import io.dojogeek.adminibot.utils.LaunchIntents;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.notNull;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.powermock.api.mockito.PowerMockito.mockStatic;
import static org.powermock.api.mockito.PowerMockito.verifyStatic;
import static org.powermock.api.mockito.PowerMockito.whenNew;

@RunWith(PowerMockRunner.class)
@PrepareForTest({MyCreditCardsActivity.class, LaunchIntents.class})
public class MyCreditCardsActivityTest {

    @Mock
    private RecyclerView mRecyclerView;

    @Mock
    private RecyclerView.Adapter mAdapter;

    @Mock
    private RecyclerView.LayoutManager mLayoutManager;

    @Mock
    private MyCreditCardsPresenter mMyCreditCardsPresenter;

    @InjectMocks
    @Spy
    private MyCreditCardsActivity mMyCreditCardsActivity = new MyCreditCardsActivity();

    @Test
    public void test_listCreditCards() throws Exception {

        List<DtoCreditCardAdapter> creditCards = new ArrayList<>();

        LinearLayoutManager linearLayoutManagerMock = mock(LinearLayoutManager.class);

        whenNew(LinearLayoutManager.class).withParameterTypes(Context.class).
                withArguments(mMyCreditCardsActivity).thenReturn(linearLayoutManagerMock);

        MyCreditCardsAdapter myCreditCardsAdapterMock = mock(MyCreditCardsAdapter.class);

        whenNew(MyCreditCardsAdapter.class).
                withArguments(mMyCreditCardsActivity, creditCards).thenReturn(myCreditCardsAdapterMock);

        mMyCreditCardsActivity.listMyCreditCards(creditCards);

        verify(mRecyclerView).setHasFixedSize(true);
        verify(mRecyclerView).setLayoutManager(linearLayoutManagerMock);
        verify(mRecyclerView).setAdapter(myCreditCardsAdapterMock);

    }

    @Test
    public void testGetLayoutActivity_returnCorrectLayout() {

        int actualLayoutActivity = mMyCreditCardsActivity.getLayoutActivity();

        assertThat(actualLayoutActivity, is(R.layout.activity_my_credit_cards));
    }

    @Test
    public void testSetupComponent_injectActivity() {

        AppComponent appComponent = mock(AppComponent.class);

        AdminiBotComponent adminiBotComponent = mock(AdminiBotComponent.class);

        when(appComponent.plus((AdminiBotModule) notNull())).thenReturn(adminiBotComponent);
        doNothing().when(adminiBotComponent).inject(mMyCreditCardsActivity);

        mMyCreditCardsActivity.setupComponent(appComponent);

        verify(appComponent).plus((AdminiBotModule) notNull());
        verify(adminiBotComponent).inject(mMyCreditCardsActivity);
    }

    @Test
    public void testLoadViews_setViews() {

        doReturn(mRecyclerView).when(mMyCreditCardsActivity).findViewById(R.id.my_recycler_view);

        mMyCreditCardsActivity.loadViews();

        verify(mMyCreditCardsActivity).findViewById(R.id.my_recycler_view);
    }

    @Test
    public void testLoadDataView_buildToolbar() throws Exception {

        Toolbar toolbarMock = mock(Toolbar.class);
        doReturn(toolbarMock).when(mMyCreditCardsActivity).findViewById(R.id.toolbar_with_image);

        doNothing().when(mMyCreditCardsActivity).setSupportActionBar(toolbarMock);

        ActionBar actionBarMock = mock(ActionBar.class);
        doReturn(actionBarMock).when(mMyCreditCardsActivity).getSupportActionBar();

        doNothing().when(actionBarMock).setDisplayHomeAsUpEnabled(true);

        ImageView imageViewMock = mock(ImageView.class);
//        doReturn(imageViewMock).when(toolbarMock).findViewById(R.id.toolbar_icon);

        Resources resources = mock(Resources.class);
        doReturn(resources).when(mMyCreditCardsActivity).getResources();

        Drawable drawableMock = mock(Drawable.class);
        doReturn(drawableMock).when(resources).getDrawable(R.drawable.ic_card);

        TextView textViewMock = mock(TextView.class);
//        doReturn(textViewMock).when(toolbarMock).findViewById(R.id.toolbar_title);

        mMyCreditCardsActivity.loadDataView();

        verify(mMyCreditCardsActivity).findViewById(R.id.toolbar_with_image);
        verify(mMyCreditCardsActivity).setSupportActionBar(toolbarMock);
        verify(mMyCreditCardsActivity).getSupportActionBar();
        verify(actionBarMock).setDisplayHomeAsUpEnabled(true);
//        verify(toolbarMock).findViewById(R.id.toolbar_icon);
        verify(mMyCreditCardsActivity).getResources();
        verify(resources).getDrawable(R.drawable.ic_card);
//        verify(toolbarMock).findViewById(R.id.toolbar_title);
        verify(imageViewMock).setImageDrawable(drawableMock);
        verify(textViewMock).setText(R.string.title_activity_my_credit_cards);

    }

    @Test
    public void testCloseConnections() {
        mMyCreditCardsActivity.closeConnections();
        verify(mMyCreditCardsPresenter).unnusedView();
    }

    @Test
    public void testOnclick_launchCardDetail() throws Exception {

        View itemViewMock = mock(View.class);
        int selectedRecordId = 1;

        HashMap<String, Serializable> flags = mock(HashMap.class);
        whenNew(HashMap.class).withNoArguments().thenReturn(flags);

        mockStatic(LaunchIntents.class);

        PowerMockito.doNothing().when(LaunchIntents.class);

        LaunchIntents.launchIntentWithExtraValues(mMyCreditCardsActivity, Class.class, flags);

        mMyCreditCardsActivity.onClick(itemViewMock, selectedRecordId);

        verifyStatic();

        LaunchIntents.launchIntentWithExtraValues(mMyCreditCardsActivity, CreditCardDetailActivity.class, flags);
    }

}
