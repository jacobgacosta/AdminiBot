package io.dojogeek.adminibot.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import io.dojogeek.adminibot.R;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class WelcomeAdapterTest {

    @Test
    public void testGetItemView_successfulConstruction() {

        ViewGroup group = mock(ViewGroup.class);

        View container = mock(View.class);

        ImageView image = mock(ImageView.class);
        when(container.findViewById(R.id.option_image)).thenReturn(image);

        TextView title = mock(TextView.class);
        when(container.findViewById(R.id.option_title)).thenReturn(title);

        TextView description = mock(TextView.class);
        when(container.findViewById(R.id.option_description)).thenReturn(description);

        LayoutInflater inflater =  mock(LayoutInflater.class);
        when(inflater.inflate(R.layout.item_wellcome, group, false)).thenReturn(container);

        Context context = mock(Context.class);
        when(context.getSystemService(Context.LAYOUT_INFLATER_SERVICE)).thenReturn(inflater);

        List<Integer []> items = new ArrayList<>();
        items.add(new Integer[]{R.drawable.ic_cash, R.string.title1, R.string.description1});

        WelcomeAdapter adapter = new WelcomeAdapter(context, items);
        View actualView  = adapter.getView(0, mock(View.class), group);

        assertNotNull(actualView);
        assertEquals(container, actualView);

        verify(context).getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        verify(inflater).inflate(R.layout.item_wellcome, group, false);
        verify(container).findViewById(R.id.option_image);
        verify(container).findViewById(R.id.option_title);
        verify(container).findViewById(R.id.option_description);
        verify(image).setImageResource(R.drawable.ic_cash);
        verify(title).setText(R.string.title1);
        verify(description).setText(R.string.description1);

    }

}
