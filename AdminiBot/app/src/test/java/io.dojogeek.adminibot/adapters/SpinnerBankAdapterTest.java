package io.dojogeek.adminibot.adapters;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.support.v4.content.res.ResourcesCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.modules.junit4.PowerMockRunner;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import io.dojogeek.adminibot.R;

import static org.junit.Assert.assertNotNull;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import static org.junit.Assert.assertEquals;

@RunWith(PowerMockRunner.class)
public class SpinnerBankAdapterTest {

    private static final int LAST_ITEM = 1;

    @Mock
    private Context mContext;

    private List<Integer> mItemsText = mock(List.class);

    private List<String> mResourcesImagesNames = mock(List.class);

    @InjectMocks
    @Spy
    private SpinnerBankAdapter mSpinnerBankAdapter = new SpinnerBankAdapter(mContext, mResourcesImagesNames, mItemsText);

    @Test
    public void testGetView() {

        int position = 0;
        int resourcesImagesNamesListSize = 4;
        LayoutInflater mockLayoutInflater = mock(LayoutInflater.class);
        View mockConvertView = mock(View.class);
        ViewGroup mockParentView = mock(ViewGroup.class);
        View mockViewcontainer = mock(View.class);
        TextView mockTextView = mock(TextView.class);
        ImageView mockImageView = mock(ImageView.class);
        Resources mockResources = mock(Resources.class);
        String imageName = "ic_amex";
        int stringResource = R.string.banks_amex;
        int drawableId = 0;
        Drawable mockDrawable = mock(Drawable.class);

        when(mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE)).thenReturn(mockLayoutInflater);
        when(mockLayoutInflater.inflate(R.layout.item_spinner_bank, mockParentView, false)).thenReturn(mockViewcontainer);
        when(mockViewcontainer.findViewById(R.id.imageView)).thenReturn(mockImageView);
        when(mResourcesImagesNames.size()).thenReturn(resourcesImagesNamesListSize);
        when(mockViewcontainer.findViewById(R.id.textView)).thenReturn(mockTextView);
        when(mResourcesImagesNames.get(position)).thenReturn(imageName);
        when(mItemsText.get(position)).thenReturn(stringResource);
        when(mContext.getResources()).thenReturn(mockResources);

        when(mockResources.getIdentifier(imageName, "drawable", mContext.getPackageName())).thenReturn(drawableId);

        PowerMockito.mockStatic(ResourcesCompat.class);
        given(ResourcesCompat.getDrawable(mockResources, drawableId, null)).willReturn(mockDrawable);

        View actualVeiw = mSpinnerBankAdapter.getView(position, mockConvertView, mockParentView);

        verify((BaseAdapter) mSpinnerBankAdapter).getView(position, mockConvertView, mockParentView);
        verify(mContext).getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        verify(mockLayoutInflater).inflate(R.layout.item_spinner_bank, mockParentView, false);
        verify(mockViewcontainer).findViewById(R.id.imageView);
        verify(mResourcesImagesNames).size();
        verify(mockImageView).setImageDrawable(mockDrawable);
        verify(mockViewcontainer).findViewById(R.id.textView);
        verify(mockTextView).setText(stringResource);
        verify(mResourcesImagesNames).get(position);
        verify(mItemsText).get(position);

        assertNotNull(actualVeiw);
        assertEquals(mockViewcontainer, actualVeiw);

    }

    @Test
    public void testGetCount_correctCount() {

        int expectedDummieSize = 5;

        when(mItemsText.size()).thenReturn(expectedDummieSize);

        int actualCount = mSpinnerBankAdapter.getCount();

        assertEquals(expectedDummieSize - LAST_ITEM, actualCount);
    }

    @Test
    public void testSetHint() {

        int expectedDummieSize = 5;
        int lastSpinnerPosition = 4;
        LayoutInflater mockLayoutInflater = mock(LayoutInflater.class);
        View mockConvertView = mock(View.class);
        ViewGroup mockParentView = mock(ViewGroup.class);
        View mockViewcontainer = mock(View.class);
        TextView mockTextView = mock(TextView.class);
        ImageView mockImageView = mock(ImageView.class);
        Resources mockResources = mock(Resources.class);
        String imageName = "ic_amex";
        int stringResource = R.string.banks_amex;
        int drawableId = 0;
        Drawable mockDrawable = mock(Drawable.class);
        int color = 1244;

        when(mItemsText.size()).thenReturn(expectedDummieSize);
        when(mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE)).thenReturn(mockLayoutInflater);
        when(mockLayoutInflater.inflate(R.layout.item_spinner_bank, mockParentView, false)).thenReturn(mockViewcontainer);
        when(mockViewcontainer.findViewById(R.id.imageView)).thenReturn(mockImageView);
        when(mockViewcontainer.findViewById(R.id.textView)).thenReturn(mockTextView);
        when(mResourcesImagesNames.get(lastSpinnerPosition)).thenReturn(imageName);
        when(mItemsText.get(lastSpinnerPosition)).thenReturn(stringResource);
        when(mContext.getResources()).thenReturn(mockResources);

        when(mockResources.getIdentifier(imageName, "drawable", mContext.getPackageName())).thenReturn(drawableId);

        PowerMockito.mockStatic(ResourcesCompat.class);
        given(ResourcesCompat.getDrawable(mockResources, drawableId, null)).willReturn(mockDrawable);

        when(mockResources.getColor(R.color.light_grey)).thenReturn(color);

        mSpinnerBankAdapter.getView(lastSpinnerPosition, mockConvertView, mockParentView);

        verify(mockTextView).setTextColor(color);
        verify(mockTextView).setHint(stringResource);

    }

}
