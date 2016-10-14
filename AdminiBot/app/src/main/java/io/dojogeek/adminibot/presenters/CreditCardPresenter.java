package io.dojogeek.adminibot.presenters;

import io.dojogeek.adminibot.models.BankCardModel;
import io.dojogeek.adminibot.models.CreditCardModel;

public interface CreditCardPresenter {

    void createCreditCard(CreditCardModel creditCardModel);

    void loadBanks();

    void unnusedView();
}
