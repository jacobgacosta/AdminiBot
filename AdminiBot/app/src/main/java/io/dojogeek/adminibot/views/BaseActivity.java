package io.dojogeek.adminibot.views;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

import io.dojogeek.adminibot.R;

public abstract class BaseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(getContentView());

        this.configToolbar();
    }

    private void configToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setElevation(0);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ((TextView) findViewById(R.id.text_title_toolbar)).setText(getToolbarTitle());
    }

    public int getToolbarTitle() {
        throw new RuntimeException("Stub!");
    }

    public int getContentView() {
        throw new RuntimeException("Stub!");
    }

}
