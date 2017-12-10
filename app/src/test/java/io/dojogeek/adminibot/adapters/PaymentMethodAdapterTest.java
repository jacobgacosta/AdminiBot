package io.dojogeek.adminibot.adapters;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import java.util.Arrays;
import java.util.List;

import io.dojogeek.adminibot.R;
import io.dojogeek.adminibot.enums.TypePaymentMethodEnum;
import io.dojogeek.adminibot.utils.ResourceProvider;

import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.powermock.api.mockito.PowerMockito.mockStatic;
import static org.powermock.api.mockito.PowerMockito.verifyStatic;

@RunWith(PowerMockRunner.class)
@PrepareForTest(ResourceProvider.class)
public class PaymentMethodAdapterTest {

    @Mock
    private Context mContext;

    private List<TypePaymentMethodEnum> mList = Arrays.asList(TypePaymentMethodEnum.CREDIT_CARD);

    @InjectMocks
    private ArrayAdapter<TypePaymentMethodEnum> mAdapter = new PaymentMethodAdapter(mContext, mList);

    @Test
    public void testAdapter_buildItemView() {

        ViewGroup group = mock(ViewGroup.class);

        View view = mock(View.class);

        LayoutInflater layoutInflater = mock(LayoutInflater.class);
        when(mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE)).thenReturn(layoutInflater);
        when(layoutInflater.inflate(R.layout.item_payment_method, group, false)).thenReturn(view);

        TextView name = mock(TextView.class);
        when(view.findViewById(R.id.text_name_payment_method)).thenReturn(name);

        ImageView image = mock(ImageView.class);
        when(view.findViewById(R.id.image_payment_method)).thenReturn(image);

        mockStatic(ResourceProvider.class);

        when(ResourceProvider.
                getStringFromName(mContext, TypePaymentMethodEnum.CREDIT_CARD.getStringName())).
                thenReturn("some text");

        Drawable drawable = mock(Drawable.class);
        when(ResourceProvider.
                getDrawableFromName(mContext, TypePaymentMethodEnum.CREDIT_CARD.getResourceName())).
                thenReturn(drawable);

        View expectedView = mAdapter.getView(0, mock(View.class), group);

        assertNotNull(expectedView);
        verify(view).findViewById(R.id.text_name_payment_method);
        verifyStatic();
        ResourceProvider.getStringFromName(mContext, TypePaymentMethodEnum.CREDIT_CARD.getStringName());
        verify(name).setText("some text");
        verify(view).findViewById(R.id.image_payment_method);
        verifyStatic();
        ResourceProvider.getDrawableFromName(mContext, TypePaymentMethodEnum.CREDIT_CARD.getResourceName());
        verify(image).setImageDrawable(drawable);
        verify(view).setTag(TypePaymentMethodEnum.CREDIT_CARD);

    }

}
