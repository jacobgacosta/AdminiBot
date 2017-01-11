package io.dojogeek.adminibot.adapters;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.support.v4.content.res.ResourcesCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.modules.junit4.PowerMockRunner;

import java.util.List;

import io.dojogeek.adminibot.R;
import io.dojogeek.adminibot.enums.TypePaymentMethodEnum;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(PowerMockRunner.class)
public class PaymentMethodAdapterTest {

    @Mock
    private Context mContext;

    @Mock
    private List<TypePaymentMethodEnum> mTypePaymentMethodEnumList;

    @Mock
    private LayoutInflater mLayoutInflater;

    @Mock
    private View mLayoutPaymentMethod;

    @Mock
    private View mConvertView;

    @Mock
    private ViewGroup mParent;

    @Mock
    private ImageView mImagePaymentMethod;

    @Mock
    private TextView mDescriptionPaymentMethod;

    @Mock
    private Resources mResources;

    @Mock
    private Drawable mDrawable;

    @InjectMocks
    private ArrayAdapter<TypePaymentMethodEnum> typePaymentMethodEnumArrayAdapter =
            new PaymentMethodAdapter(mContext, mTypePaymentMethodEnumList);

    @Test
    public void testGetView_mappingData() {

        int position = 0;
        int resourceId = 546785498;
        String stringResource = "Efectivo";

        PowerMockito.mockStatic(ResourcesCompat.class);
        BDDMockito.given(ResourcesCompat.getDrawable(mResources, resourceId, null)).willReturn(mDrawable);

        when(mTypePaymentMethodEnumList.get(position)).thenReturn(TypePaymentMethodEnum.CASH);
        when(mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE)).thenReturn(mLayoutInflater);
        when(mLayoutInflater.inflate(R.layout.item_payment_method, mParent, false)).thenReturn(mLayoutPaymentMethod);
        when(mLayoutPaymentMethod.findViewById(R.id.img_payment_method)).thenReturn(mImagePaymentMethod);
        when(mLayoutPaymentMethod.findViewById(R.id.payment_method_description)).thenReturn(mDescriptionPaymentMethod);
        when(mContext.getResources()).thenReturn(mResources);
        when(mResources.getIdentifier("ic_cash", "string", mContext.getPackageName())).thenReturn(resourceId);
        when(mResources.getString(resourceId)).thenReturn(stringResource);
        when(mResources.getIdentifier("ic_cash", "drawable", mContext.getPackageName())).thenReturn(resourceId);

        View actualView =
                typePaymentMethodEnumArrayAdapter.getView(position, mConvertView, mParent);

        assertNotNull(actualView);
        assertThat(actualView, is(mLayoutPaymentMethod));
        verify(mTypePaymentMethodEnumList, times(3)).get(position);
        verify(mContext).getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        verify(mLayoutInflater).inflate(R.layout.item_payment_method, mParent, false);
        verify(mLayoutPaymentMethod).findViewById(R.id.img_payment_method);
        verify(mLayoutPaymentMethod).findViewById(R.id.payment_method_description);

        verify(mContext, times(2)).getResources();
        verify(mResources).getIdentifier(mTypePaymentMethodEnumList.get(position).getName(), "string", mContext.getPackageName());
        verify(mResources).getString(resourceId);
        verify(mResources).getIdentifier(mTypePaymentMethodEnumList.get(position).getName(), "drawable", mContext.getPackageName());

        verify(mImagePaymentMethod).setImageDrawable(mDrawable);
        verify(mDescriptionPaymentMethod).setText(stringResource);
        verify(mDescriptionPaymentMethod).setTypeface(null, Typeface.BOLD);
        verify(mLayoutPaymentMethod).setTag(mTypePaymentMethodEnumList.get(position));
    }

}
