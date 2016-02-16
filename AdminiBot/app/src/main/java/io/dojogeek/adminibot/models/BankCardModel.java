package io.dojogeek.adminibot.models;

import io.dojogeek.adminibot.enums.CardTypeEnum;

public class BankCardModel {

    public long id;
    public String name;
    public String number;
    public long bankId;
    public long trademarkId;
    public double availableCredit;
    public CardTypeEnum cardType;
    public long userId;

}
