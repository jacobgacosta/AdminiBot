package io.dojogeek.adminibot.views;

import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import dagger.App;
import dagger.AppComponent;

public abstract class BaseFragment extends Fragment {

    private View mView;

    protected BaseFragment prepareFragment(LayoutInflater inflater, int idLayout, ViewGroup container) {

        mView = inflater.inflate(idLayout, container, false);

//        setupComponent(App.get(this.getActivity()).component());

        return this;
    }

    protected void prepareViewComponentsAndListeners() {

        loadViews(mView);

        addListenersToViews();
    }

    @Override
    public void onPause() {
        super.onPause();

        closeConnections();
    }

    @Override
    public View getView() {
        return mView;
    }

    protected abstract void setupComponent(AppComponent appComponent);

    protected abstract void loadViews(View fragmentView);

    protected abstract void addListenersToViews();

    protected abstract void closeConnections();

}
