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

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.BDDMockito;
import org.mockito.Mock;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import java.util.List;

import io.dojogeek.adminibot.R;
import io.dojogeek.adminibot.dtos.DtoCreditCardAdapter;
import io.dojogeek.adminibot.exceptions.RecyclerItemException;
import io.dojogeek.adminibot.factory.ModelsFactory;
import io.dojogeek.adminibot.listeners.RecyclerItemOnClickListener;
import io.dojogeek.adminibot.views.MyCreditCardsActivity;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNotNull;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.powermock.api.mockito.PowerMockito.verifyStatic;
import static org.powermock.api.mockito.PowerMockito.whenNew;

@RunWith(PowerMockRunner.class)
@PrepareForTest({MyCreditCardsAdapter.class, LayoutInflater.class})
public class MyCreditCardsAdapterTest {

    private ModelsFactory factory = new ModelsFactory();

    @Mock
    private List<DtoCreditCardAdapter> dtoCreditCards;

    private Context mContext;

    private RecyclerView.Adapter<MyCreditCardsAdapter.ViewHolder> mMyCreditCardsAdapter;

    private View mMockView;

    @Mock
    private ImageView mCreditCardBankImageName;

    @Mock
    private TextView mCreditCardName;

    @Mock
    private TextView mCreditCardNumber;

    @Before
    public void setupObjects() {

        mContext = mock(MyCreditCardsActivity.class);
        mMockView = mock(View.class);

        try {
            mMyCreditCardsAdapter = new MyCreditCardsAdapter(mContext, dtoCreditCards);
        } catch (RecyclerItemException e) {
            e.printStackTrace();
        }

    }

    @Test
    public void testOnCreateViewHolder_correctCreation() throws Exception {

        ViewGroup parent = mock(ViewGroup.class);

        Context contextMock = mock(Context.class);
        when(parent.getContext()).thenReturn(contextMock);

        PowerMockito.mockStatic(LayoutInflater.class);
        View viewMock = mock(View.class);
        LayoutInflater layoutInflaterMock = mock(LayoutInflater.class);
        BDDMockito.given(LayoutInflater.from(contextMock)).willReturn(layoutInflaterMock);

        when(layoutInflaterMock.inflate(R.layout.my_credit_card_item, parent, false)).thenReturn(viewMock);

        MyCreditCardsAdapter.ViewHolder viewHolder = mock(MyCreditCardsAdapter.ViewHolder.class);

        whenNew(MyCreditCardsAdapter.ViewHolder.class).
                withParameterTypes(View.class).withArguments(viewMock).thenReturn(viewHolder);

        int viewType = 0;
        MyCreditCardsAdapter.ViewHolder actualViewHolder =
                mMyCreditCardsAdapter.onCreateViewHolder(parent, viewType);

        assertNotNull(actualViewHolder);
        assertEquals(viewHolder, actualViewHolder);
        verifyStatic();
        LayoutInflater.from(contextMock);

    }

    @Test
    public void testBuildViewHolder_loadViews() {

        View mockView = mock(View.class);

        when(mockView.findViewById(R.id.credit_card_name)).thenReturn(mCreditCardName);
        when(mockView.findViewById(R.id.credit_card_number)).thenReturn(mCreditCardNumber);
        when(mockView.findViewById(R.id.credit_card_bank_image)).thenReturn(mCreditCardBankImageName);

        MyCreditCardsAdapter.ViewHolder viewHolder = new MyCreditCardsAdapter.ViewHolder(mockView);

        assertEquals(mCreditCardBankImageName, viewHolder.creditCardBankImageName);
        assertEquals(mCreditCardName, viewHolder.creditCardName);
        assertEquals(mCreditCardNumber, viewHolder.creditCardNumber);
        verify(mockView).findViewById(R.id.credit_card_name);
        verify(mockView).findViewById(R.id.credit_card_number);
        verify(mockView).findViewById(R.id.credit_card_bank_image);
    }

    @Test
    public void testOnBindViewHolder_setCorrectDataToCreditCardView() {

        int position = 0;

        ImageView imageViewMock = mock(ImageView.class);
        doReturn(imageViewMock).when(mMockView).findViewById(R.id.credit_card_bank_image);

        TextView creditCardNameTextViewMock = mock(TextView.class);
        doReturn(creditCardNameTextViewMock).when(mMockView).findViewById(R.id.credit_card_name);

        TextView creditCardNumberTextViewMock = mock(TextView.class);
        doReturn(creditCardNumberTextViewMock).when(mMockView).findViewById(R.id.credit_card_number);

        DtoCreditCardAdapter dtoCreditCardAdapter = factory.createDtoCreditCardAdapter();
        when(dtoCreditCards.get(position)).thenReturn(dtoCreditCardAdapter);

        String packageName = "io.dojogeek";
        when(mContext.getPackageName()).thenReturn(packageName);

        Resources resourcesMock = mock(Resources.class);
        when(mContext.getResources()).thenReturn(resourcesMock);
        int drawableId = 0;
        when(resourcesMock.getIdentifier(dtoCreditCardAdapter.getCreditCardBankImageName(),
                MyCreditCardsAdapter.DRAWABLE, packageName)).thenReturn(drawableId);

        PowerMockito.mockStatic(ResourcesCompat.class);
        Drawable mockDrawable = mock(Drawable.class);
        given(ResourcesCompat.getDrawable(resourcesMock, drawableId, null)).willReturn(mockDrawable);

        MyCreditCardsAdapter.ViewHolder viewHolderMock = new MyCreditCardsAdapter.ViewHolder(mMockView);

        mMyCreditCardsAdapter.onBindViewHolder(viewHolderMock, position);

        verify(imageViewMock).setImageDrawable(mockDrawable);
        verify(creditCardNameTextViewMock).setText(dtoCreditCards.get(position).getCreditCardName());
        verify(creditCardNumberTextViewMock).setText(dtoCreditCards.get(position).getCreditCardNumber());
    }

    @Test
    public void testOnclickListener_setListener() {

        MyCreditCardsAdapter.ViewHolder viewHolderMock = new MyCreditCardsAdapter.ViewHolder(mMockView);

        verify(mMockView).setOnClickListener(viewHolderMock);
    }

    @Test
    public void testCallBack_correctCallBack() {

        long cardId = 1;

        MyCreditCardsAdapter.ViewHolder viewHolderMock = new MyCreditCardsAdapter.ViewHolder(mMockView);
        viewHolderMock.cardId = cardId;

        viewHolderMock.onClick(mMockView);
        verify((RecyclerItemOnClickListener) mContext).onClick(mMockView, cardId);
    }

    @Test(expected = RecyclerItemException.class)
    public void testCallBack_viewNoImplementRecyclerItemOnClickListener_isException() throws Exception {

        Context contextWithNoImplementRecyclerItemOnClickListener = mock(Context.class);

        new MyCreditCardsAdapter(contextWithNoImplementRecyclerItemOnClickListener, dtoCreditCards);

        MyCreditCardsAdapter.ViewHolder viewHolderMock = new MyCreditCardsAdapter.ViewHolder(mMockView);

        View itemViewMock = mock(View.class);

        viewHolderMock.onClick(itemViewMock);

    }

    @Test
    public void testGetItemCount_correctCount() {

        int sizeList = 5;

        when(dtoCreditCards.size()).thenReturn(5);

        int count = mMyCreditCardsAdapter.getItemCount();

        assertEquals(count, sizeList);
    }

}
