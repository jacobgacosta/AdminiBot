package io.dojogeek.adminibot.views;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.List;

import javax.inject.Inject;

import dagger.AdminiBotModule;
import dagger.AppComponent;
import io.dojogeek.adminibot.R;
import io.dojogeek.adminibot.models.ExpenseModel;
import io.dojogeek.adminibot.presenters.NewPurchasePresenter;
import io.dojogeek.adminibot.utils.LaunchIntents;

public class InboxFragment extends BaseFragment implements Inbox, View.OnClickListener {

    private static final String TAG = "InboxFragment";

    @Inject
    public NewPurchasePresenter newPurchasePresenter;
    private FloatingActionButton mAddCostButton;
    private ListView mExpenses;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        super.prepareFragment(inflater, R.layout.inbox, container).prepareViewComponentsAndListeners();

        return getView();
    }

    @Override
    public void onResume() {
        super.onResume();
        loadInitData();
    }

    @Override
    public void onClick(View view) {
        int idView = view.getId();

        switch (idView) {
            case R.id.add_cost:
                LaunchIntents.launchIntentClearTop(getActivity(), PaymentMethodsActivity.class);
                break;
            default:
                Log.v(TAG, "no events!");
                break;
        }
    }

    @Override
    public void listExpenses(List<ExpenseModel> expenses) {

        Log.v(TAG, "here");

    }

    @Override
    protected void setupComponent(AppComponent appComponent) {
        appComponent.plus(new AdminiBotModule(this)).inject(this);
    }

    @Override
    protected void loadViews(View fragmentView) {
        mAddCostButton = (FloatingActionButton) fragmentView.findViewById(R.id.add_cost);
        mExpenses = (ListView) fragmentView.findViewById(R.id.expenses);
    }

    @Override
    protected void addListenersToViews() {
        mAddCostButton.setOnClickListener(this);
    }

    @Override
    protected void closeConnections() {
        newPurchasePresenter.unnusedView();
    }

    private void loadInitData() {
        newPurchasePresenter.loadExpenses();
    }

}
