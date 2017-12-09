package io.dojogeek.adminibot.views;

import java.util.List;

import io.dojogeek.adminibot.dtos.DtoCreditCardAdapter;

public interface MyCreditCards {

    void listMyCreditCards(List<DtoCreditCardAdapter> creditCardModels);

}
