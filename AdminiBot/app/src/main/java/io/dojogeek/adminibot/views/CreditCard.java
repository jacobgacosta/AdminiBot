package io.dojogeek.adminibot.views;

import java.util.List;
import java.util.Map;

import io.dojogeek.adminibot.models.BankModel;

public interface CreditCard {

    void notifySuccessfulInsertion();

    void notifyErrorInsertion();

    void fillBankItemsSpinner(Map<Long, Map<String, Integer>> items);

    void returnToMyPaymentsMethods();

}
