package dagger;

import android.app.Application;
import android.content.Context;

import net.danlew.android.joda.JodaTimeAndroid;

public class App extends Application {

    private AppComponent component;

    @Override
    public void onCreate() {
        super.onCreate();

        setupGraph();

        setupJodaTime();
    }

    public static App get(Context context) {
        return (App) context.getApplicationContext();
    }

    public AppComponent component() {
        return component;
    }

    private void setupGraph() {
        component = DaggerAppComponent.builder().appModule(new AppModule()).build();
    }

    private void setupJodaTime() {
        JodaTimeAndroid.init(this);
    }
}