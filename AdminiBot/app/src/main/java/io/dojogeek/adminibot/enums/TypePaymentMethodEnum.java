package io.dojogeek.adminibot.enums;

public enum TypePaymentMethodEnum {

    FOOD_COUPONS("ic_food_coupon", "food coupons"), /*CHEQUE("ic_cheque", "accounting document"),*/
    CASH("ic_cash", "cash"), CARD("ic_card", "card");

    String mName;
    String mDescription;

    TypePaymentMethodEnum(String name, String description) {
        mName = name;
        mDescription = description;
    }

    public String getName() {
        return  mName;
    }

    public String getDescription() {
        return mDescription;
    }
}
