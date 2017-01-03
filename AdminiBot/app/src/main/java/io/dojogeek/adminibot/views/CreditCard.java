package io.dojogeek.adminibot.views;

import java.util.Map;

public interface CreditCard {

    void notifySuccessfulInsertion();

    void notifyErrorInsertion();

    void fillBankItemsSpinner(Map<Long, Map<String, Integer>> items);

    void returnToMyPaymentsMethods();

}
