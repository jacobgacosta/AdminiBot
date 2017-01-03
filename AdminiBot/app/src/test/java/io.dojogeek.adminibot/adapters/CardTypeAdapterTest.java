package io.dojogeek.adminibot.adapters;

import android.content.Context;
import android.content.res.Resources;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.powermock.modules.junit4.PowerMockRunner;

import java.util.List;

import io.dojogeek.adminibot.R;
import io.dojogeek.adminibot.enums.CardTypeEnum;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Matchers.notNull;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


@RunWith(PowerMockRunner.class)
public class CardTypeAdapterTest {

    @Mock
    private Context mContext;

    @Mock
    private List<CardTypeEnum> mCardTypeEnumList;

    @Mock
    private View mConvertView;

    @Mock
    private ViewGroup mParent;

    @Mock
    private LayoutInflater mLayoutInflater;

    @Mock
    private View mRootView;

    @Mock
    private TextView mDescription;

    @Mock
    private ImageView mImageCard;

    @Mock
    private Resources mResources;

    @InjectMocks
    private CardTypeAdapter mCardTypeAdapter = new CardTypeAdapter(mContext, mCardTypeEnumList);

    @Test
    public void testGetView_mappingData() {

        int position = 0;
        int resourceId = R.string.credit_card;
        String stringResource = "Tarjeta de Crédito";

        doReturn(CardTypeEnum.CREDIT_CARD).when(mCardTypeEnumList).get(position);
        doReturn(mLayoutInflater).when(mContext).getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        doReturn(mRootView).when(mLayoutInflater).inflate(R.layout.item_card_types, mParent, false);
        doReturn(mDescription).when(mRootView).findViewById(R.id.card_name);
        doReturn(mResources).when(mContext).getResources();
        when(mResources.getIdentifier(CardTypeEnum.CREDIT_CARD.getCardType(), "string", mContext.getPackageName())).thenReturn(resourceId);
        doReturn(stringResource).when(mResources).getString(resourceId);


        View actualView = mCardTypeAdapter.getView(position, mConvertView, mParent);

        assertNotNull(actualView);
        assertEquals(mRootView, actualView);
        verify(mContext).getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        verify(mLayoutInflater).inflate(R.layout.item_card_types, mParent, false);
        verify(mRootView).findViewById(R.id.card_name);
        verify(mDescription).setText((String) notNull());
        verify(mResources).getIdentifier("credit_card", "string", mContext.getPackageName());
        verify(mContext).getResources();
        verify(mResources).getString(resourceId);
        verify(mRootView).setTag(mCardTypeEnumList.get(position));
    }

}
