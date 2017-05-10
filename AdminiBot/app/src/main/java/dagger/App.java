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

    public static App get(Context context) {
        return (App) context.getApplicationContext();
    }

    public AppComponent component() {
        return component;
    }

    private void setupGraph() {
        component = DaggerAppComponent.builder().appModule(new AppModule()).build();
    }
}