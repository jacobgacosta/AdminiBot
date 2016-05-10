package io.dojogeek.adminibot.enums;

public enum CardTypeEnum {

    CREDIT_CARD("credit_card", "some credit card"), DEBIT_CARD("debit_card", "some debit card"),
    PREPAID_CARD("prepaid_card", "some prepaid card");

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
