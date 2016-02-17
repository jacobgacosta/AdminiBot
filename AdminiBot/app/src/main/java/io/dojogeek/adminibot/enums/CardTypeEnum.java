package io.dojogeek.adminibot.enums;

public enum CardTypeEnum {

    CREDIT_CARD("card", "some credit card"), DEBIT_CARD("debit", "some debit card"),
    PREPAID_CARD("prepaid", "some prepaid card");

    private String mCardType;
    private String mDescription;

    CardTypeEnum(String cardType, String description) {
        mCardType = cardType;
        mDescription = description;
    }

    public String getCardType() {
        return mCardType;
    }

    public String getDescription() {
        return mDescription;
    }
}
