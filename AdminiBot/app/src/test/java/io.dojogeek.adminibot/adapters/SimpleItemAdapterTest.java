package io.dojogeek.adminibot.adapters;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import java.util.List;

import io.dojogeek.adminibot.R;
import io.dojogeek.adminibot.dtos.DtoSimpleAdapter;
import io.dojogeek.adminibot.factory.ModelsFactory;
import io.dojogeek.adminibot.listeners.RecyclerItemOnClickListener;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNotNull;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.withSettings;
import static org.powermock.api.mockito.PowerMockito.verifyStatic;
import static org.powermock.api.mockito.PowerMockito.whenNew;

@RunWith(PowerMockRunner.class)
@PrepareForTest({SimpleItemAdapter.class, LayoutInflater.class})
public class SimpleItemAdapterTest {

    private ModelsFactory factory = new ModelsFactory();

    @Mock
    private List<DtoSimpleAdapter> mSimpleAdapterList;

    @Mock
    private Context mContext = mock(Context.class, withSettings().extraInterfaces(RecyclerItemOnClickListener.class));

    @InjectMocks
    @Spy
    private SimpleItemAdapter mSimpleItemAdapter = new SimpleItemAdapter(mContext, mSimpleAdapterList);


    @Test
    public void testOnCreateViewHolder_correctCreation() throws Exception {

        ViewGroup parent = mock(ViewGroup.class);
        when(parent.getContext()).thenReturn(mContext);

        PowerMockito.mockStatic(LayoutInflater.class);

        LayoutInflater layoutInflaterMock = mock(LayoutInflater.class);
        given(LayoutInflater.from(mContext)).willReturn(layoutInflaterMock);

        View viewMock = mock(View.class);
        when(layoutInflaterMock.inflate(R.layout.item_simple_detail_payment_method,
                parent, false)).thenReturn(viewMock);

        SimpleItemAdapter.ViewHolder viewHolderMock = mock(SimpleItemAdapter.ViewHolder.class);
        whenNew(SimpleItemAdapter.ViewHolder.class).withArguments(viewMock).thenReturn(viewHolderMock);

        int viewType = 0;

        SimpleItemAdapter.ViewHolder actualViewHolder =
                mSimpleItemAdapter.onCreateViewHolder(parent, viewType);

        assertNotNull(actualViewHolder);
        assertEquals(viewHolderMock, actualViewHolder);
        verifyStatic();
        LayoutInflater.from(mContext);
        verify(layoutInflaterMock).inflate(R.layout.item_simple_detail_payment_method,
                parent, false);

    }

    @Test
    public void testBuildViewHolder_loadViews() {

        View mockView = mock(View.class);

        TextView textViewTitleMock = mock(TextView.class);
        TextView textViewSubtitleMock = mock(TextView.class);
        ImageView imageViewMock = mock(ImageView.class);

        when(mockView.findViewById(R.id.payment_method_title)).thenReturn(textViewTitleMock);
        when(mockView.findViewById(R.id.payment_method_subtitle)).thenReturn(textViewSubtitleMock);
//        when(mockView.findViewById(R.id.payment_method_icon)).thenReturn(imageViewMock);

        SimpleItemAdapter.ViewHolder viewHolder = new SimpleItemAdapter.ViewHolder(mockView);

        assertEquals(textViewTitleMock, viewHolder.title);
        assertEquals(textViewSubtitleMock, viewHolder.subtitle);

        verify(mockView).findViewById(R.id.payment_method_title);
        verify(mockView).findViewById(R.id.payment_method_subtitle);
//        verify(mockView).findViewById(R.id.payment_method_icon);

    }

    @Test
    public void testOnBindViewHolder_setCorrectDataToSimpleItemView() {

        View mockView = mock(View.class);

        TextView textViewTitleMock = mock(TextView.class);
        TextView textViewSubtitleMock = mock(TextView.class);
        ImageView imageViewMock = mock(ImageView.class);

        when(mockView.findViewById(R.id.payment_method_title)).thenReturn(textViewTitleMock);
        when(mockView.findViewById(R.id.payment_method_subtitle)).thenReturn(textViewSubtitleMock);
//        when(mockView.findViewById(R.id.payment_method_icon)).thenReturn(imageViewMock);

        SimpleItemAdapter.ViewHolder viewHolder = new SimpleItemAdapter.ViewHolder(mockView);

        int position = 0;
        DtoSimpleAdapter dtoSimpleAdapter = factory.createDtoSimpleAdapter();
        when(mSimpleAdapterList.get(position)).thenReturn(dtoSimpleAdapter);

        /*String packageName = "io.dojogeek";
        when(mContext.getPackageName()).thenReturn(packageName);

        Resources resourcesMock = mock(Resources.class);
        when(mContext.getResources()).thenReturn(resourcesMock);
        int drawableId = 0;
        when(resourcesMock.getIdentifier(dtoSimpleAdapter.getIconName(),
                SimpleItemAdapter.DRAWABLE, packageName)).thenReturn(drawableId);

        PowerMockito.mockStatic(ResourcesCompat.class);
        Drawable mockDrawable = mock(Drawable.class);
        given(ResourcesCompat.getDrawable(resourcesMock, drawableId, null)).willReturn(mockDrawable);*/

        mSimpleItemAdapter.onBindViewHolder(viewHolder, position);

        verify(textViewTitleMock).setText(dtoSimpleAdapter.getTitle());
        verify(textViewSubtitleMock).setText(dtoSimpleAdapter.getSubtitle());
//        verify(imageViewMock).setImageDrawable(mockDrawable);

    }

    @Test
    public void test_onAttachedToRecyclerView() {

        RecyclerView recyclerViewMock = mock(RecyclerView.class);

        mSimpleItemAdapter.onAttachedToRecyclerView(recyclerViewMock);

        verify(mSimpleItemAdapter).onAttachedToRecyclerView(recyclerViewMock);
    }

    @Test
    public void testGetItemCount_correctCount() {

        int size = 4;
        when(mSimpleAdapterList.size()).thenReturn(size);

        int actualCount = mSimpleItemAdapter.getItemCount();

        assertEquals(size, actualCount);

        verify(mSimpleAdapterList).size();

    }

    @Test
    public void testOnclickListener_setListenerToItemView() {

        View mockView = mock(View.class);

        SimpleItemAdapter.ViewHolder viewHolderMock = new SimpleItemAdapter.ViewHolder(mockView);

        verify(mockView).setOnClickListener(viewHolderMock);
    }

    @Test
    public void testCallBack_correctCallBack() {

        View mockView = mock(View.class);

        int itemId = 1;

        SimpleItemAdapter.ViewHolder viewHolderMock = new SimpleItemAdapter.ViewHolder(mockView);
        viewHolderMock.itemId = itemId;

        viewHolderMock.onClick(mockView);

        verify((RecyclerItemOnClickListener) mContext).onClick(mockView, itemId);
    }

    @Ignore
    @Test(expected = RuntimeException.class)
    public void testCallBack_viewNoImplementRecyclerItemOnClickListener_isException() {

        Context contextWithNoImplementRecyclerItemOnClickListener = mock(Context.class);

        new SimpleItemAdapter(contextWithNoImplementRecyclerItemOnClickListener, mSimpleAdapterList);

    }

}
