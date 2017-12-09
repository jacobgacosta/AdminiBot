package dagger;

import android.app.Application;

public class AdminiBot extends Application {

    private AppComponent mComponent;

    @Override
    public void onCreate() {

        super.onCreate();

        mComponent = DaggerAppComponent.builder().build();
    }

    public AppComponent getComponent() {
        return mComponent;
    }
}
