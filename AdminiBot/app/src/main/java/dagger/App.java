package dagger;

import android.app.Application;
import android.content.Context;

public class App extends Application {

    private AppComponent component;

    @Override
    public void onCreate() {
        super.onCreate();

        setupGraph();
    }

    private void setupGraph() {
        component = DaggerAppComponent.builder().appModule(new AppModule()).build();
    }

    public AppComponent component() {
        return component;
    }

    public static App get(Context context) {
        return (App) context.getApplicationContext();
    }
}