package io.dojogeek.adminibot.views;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
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

import java.util.ArrayList;
import java.util.List;

import dagger.AdminiBotComponent;
import dagger.AdminiBotModule;
import dagger.AppComponent;
import io.dojogeek.adminibot.R;
import io.dojogeek.adminibot.adapters.SimpleItemAdapter;
import io.dojogeek.adminibot.dtos.DtoSimpleAdapter;
import io.dojogeek.adminibot.presenters.MyFoodCouponsPresenter;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.powermock.api.mockito.PowerMockito.verifyStatic;
import static org.powermock.api.mockito.PowerMockito.whenNew;

@RunWith(PowerMockRunner.class)
@PrepareForTest(MyFoodCouponsActivity.class)
public class MyFoodCouponsActivityTest {

    @Mock
    private RecyclerView mRecyclerView;

    @Mock
    private MyFoodCouponsPresenter mMyFoodCouponsPresenter;

    @InjectMocks
    @Spy
    private MyFoodCouponsActivity mMyFoodCouponsActivity = new MyFoodCouponsActivity();

    @Test
    public void testExtendBaseActivity() {
        assertTrue(mMyFoodCouponsActivity instanceof BaseActivity2);
    }

    @Test
    public void testGetLayoutActivity_returnCorrectLayout() {

        int expectedLayout = R.layout.activity_my_food_coupons;

        int actualLayout = mMyFoodCouponsActivity.getLayoutActivity();

        assertEquals(expectedLayout, actualLayout);
    }

    @Test
    public void testSetupComponent_injectActivity() throws Exception {

        AppComponent appComponentMock = mock(AppComponent.class);

        AdminiBotModule adminiBotModule = mock(AdminiBotModule.class);
        whenNew(AdminiBotModule.class).withArguments(mMyFoodCouponsActivity).thenReturn(adminiBotModule);

        AdminiBotComponent adminiBotComponentMock = mock(AdminiBotComponent.class);
        when(appComponentMock.plus(adminiBotModule)).thenReturn(adminiBotComponentMock);

        mMyFoodCouponsActivity.setupComponent(appComponentMock);

        verify(appComponentMock).plus(adminiBotModule);
        verify(adminiBotComponentMock).inject(mMyFoodCouponsActivity);
    }

    @Test
    public void testLoadViews_instantiateViews() {

        doReturn(mRecyclerView).when(mMyFoodCouponsActivity).findViewById(R.id.my_food_coupons_recycler_view);

        mMyFoodCouponsActivity.loadViews();

        verify(mMyFoodCouponsActivity).findViewById(R.id.my_food_coupons_recycler_view);
    }

    @Test
    public void testLoadDataView_buildToolbar() {

        Toolbar toolbarMock = mock(Toolbar.class);
        doReturn(toolbarMock).when(mMyFoodCouponsActivity).findViewById(R.id.toolbar_with_image);

        doNothing().when(mMyFoodCouponsActivity).setSupportActionBar(toolbarMock);

        ActionBar actionBarMock = mock(ActionBar.class);
        doReturn(actionBarMock).when(mMyFoodCouponsActivity).getSupportActionBar();

        doNothing().when(actionBarMock).setDisplayHomeAsUpEnabled(true);

        ImageView imageViewMock = mock(ImageView.class);
//        doReturn(imageViewMock).when(toolbarMock).findViewById(R.id.toolbar_icon);

        Resources resources = mock(Resources.class);
        doReturn(resources).when(mMyFoodCouponsActivity).getResources();

        Drawable drawableMock = mock(Drawable.class);
        doReturn(drawableMock).when(resources).getDrawable(R.drawable.ic_food_coupon);

        TextView textViewMock = mock(TextView.class);
//        doReturn(textViewMock).when(toolbarMock).findViewById(R.id.toolbar_title);

        mMyFoodCouponsActivity.loadDataView();

        verify(mMyFoodCouponsActivity).findViewById(R.id.toolbar_with_image);
        verify(mMyFoodCouponsActivity).setSupportActionBar(toolbarMock);
        verify(mMyFoodCouponsActivity).getSupportActionBar();
        verify(actionBarMock).setDisplayHomeAsUpEnabled(true);
//        verify(toolbarMock).findViewById(R.id.toolbar_icon);
        verify(mMyFoodCouponsActivity).getResources();
        verify(resources).getDrawable(R.drawable.ic_food_coupon);
//        verify(toolbarMock).findViewById(R.id.toolbar_title);
        verify(imageViewMock).setImageDrawable(drawableMock);
        verify(textViewMock).setText(R.string.title_my_food_coupons_activity);
    }

    @Test
    public void testLoadDataView_requestFoodCouponsList() throws Exception {

        PowerMockito.doNothing().when(mMyFoodCouponsActivity, "setToolBarData");

        mMyFoodCouponsActivity.loadDataView();

        verify(mMyFoodCouponsPresenter).obtainFoodCoupons();
    }

    @Test
    public void testListFoodCoupons_setToAdapter() throws Exception {

        LinearLayoutManager linearLayoutManagerMock = mock(LinearLayoutManager.class);

        whenNew(LinearLayoutManager.class).withParameterTypes(Context.class).
                withArguments(mMyFoodCouponsActivity).thenReturn(linearLayoutManagerMock);

        List<DtoSimpleAdapter> dtoSimpleAdapterList = new ArrayList<>();
        SimpleItemAdapter simpleItemAdapterMock = mock(SimpleItemAdapter.class);
        whenNew(SimpleItemAdapter.class).withArguments(mMyFoodCouponsActivity, dtoSimpleAdapterList).
                thenReturn(simpleItemAdapterMock);

        mMyFoodCouponsActivity.listFoodCoupons(dtoSimpleAdapterList);

        verify(mRecyclerView).setAdapter(simpleItemAdapterMock);
        verify(mRecyclerView).setHasFixedSize(true);
        verify(mRecyclerView).setLayoutManager(linearLayoutManagerMock);
    }

}
