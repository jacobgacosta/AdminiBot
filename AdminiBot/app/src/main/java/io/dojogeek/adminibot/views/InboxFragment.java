package io.dojogeek.adminibot.views;

import android.util.Log;

import android.os.Bundle;

import android.support.design.widget.FloatingActionButton;

import android.view.View;
import android.view.ViewGroup;
import android.view.LayoutInflater;

import android.widget.ListView;

import java.util.List;

import javax.inject.Inject;

import dagger.AppComponent;
import dagger.AdminiBotModule;

import io.dojogeek.adminibot.R;
import io.dojogeek.adminibot.models.ExpenseModel;
import io.dojogeek.adminibot.utils.LaunchIntents;
import io.dojogeek.adminibot.presenters.NewPurchasePresenter;

public class InboxFragment extends BaseFragment implements Inbox, View.OnClickListener {

    @Inject
    public NewPurchasePresenter newPurchasePresenter;

    private static final String TAG = "InboxFragment";

    private ListView mExpenses;
    private FloatingActionButton mAddCostButton;


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
            case R.id.record_an_expense:
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
        mAddCostButton = (FloatingActionButton) fragmentView.findViewById(R.id.record_an_expense);
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
