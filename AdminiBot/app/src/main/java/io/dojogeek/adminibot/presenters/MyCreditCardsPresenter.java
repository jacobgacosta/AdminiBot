package io.dojogeek.adminibot.presenters;

import io.dojogeek.adminibot.daos.BankCardDao;
import io.dojogeek.adminibot.daos.CardDetailDao;

public interface MyCreditCardsPresenter {

    void obtainMyCreditCards();

    void unnusedView();

}
