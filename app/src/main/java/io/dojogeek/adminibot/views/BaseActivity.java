package io.dojogeek.adminibot.views;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import io.dojogeek.adminibot.R;

public abstract class BaseActivity extends AppCompatActivity {

    public int getToolbarTitle() {
        throw new RuntimeException("Stub!");
    }

    public int getLayout() {
        throw new RuntimeException("Stub!");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(getLayout());

        this.configToolbar();
    }

    private void configToolbar() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(getToolbarTitle());
    }

}
