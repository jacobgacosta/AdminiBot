package io.dojogeek.adminibot.views;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.Arrays;
import java.util.List;

import dagger.AdminiBotComponent;
import dagger.AdminiBotModule;
import dagger.AppComponent;
import io.dojogeek.adminibot.R;
import io.dojogeek.adminibot.adapters.CardTypeAdapter;
import io.dojogeek.adminibot.enums.CardTypeEnum;
import io.dojogeek.adminibot.utils.LaunchIntents;

public class CardCreationActivity extends BaseActivity implements CardCreation, AdapterView.OnItemClickListener {

    private ListView mCardTypes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void setupComponent(AppComponent appComponent) {
        AdminiBotComponent adminiBotComponent = appComponent.plus(new AdminiBotModule(this));
        adminiBotComponent.inject(this);
    }

    @Override
    protected void loadViews() {
        mCardTypes = (ListView) this.findViewById(R.id.card_types);
    }

    @Override
    protected void addListenersToViews() {
        mCardTypes.setOnItemClickListener(this);
    }

    @Override
    protected void loadDataView() {

        setTitle(R.string.title_activity_card_types);

        List<CardTypeEnum> cardTypeEnumList = Arrays.asList(CardTypeEnum.values());

        CardTypeAdapter cardTypeAdapter = new CardTypeAdapter(this, cardTypeEnumList);

        mCardTypes.setAdapter(cardTypeAdapter);

    }

    @Override
    protected int getLayoutActivity() {
        return R.layout.activity_card_creation;
    }

    @Override
    protected void closeConnections() {

    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        CardTypeEnum cardTypeEnum = (CardTypeEnum) view.getTag();

        switch (cardTypeEnum) {

            case CREDIT_CARD:
                LaunchIntents.launchIntentClearTop(this, CreditCardActivity.class);
                break;
            case DEBIT_CARD:
                LaunchIntents.launchIntentClearTop(this, CardCreationActivity.class);
                break;
            case PREPAID_CARD:
                LaunchIntents.launchIntentClearTop(this, CardCreationActivity.class);
                break;
            default:
                Log.v(this.getLocalClassName(), "No operations for this selection.");

        }
    }
}
