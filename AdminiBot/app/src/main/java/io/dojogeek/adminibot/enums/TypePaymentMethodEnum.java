package io.dojogeek.adminibot.enums;

public enum TypePaymentMethodEnum {

    FOOD_COUPONS("ic_food_coupon", "food_coupons"), CASH("ic_cash", "cash"),
    CREDIT_CARD("ic_card", "credit_card"), DEBIT_CARD("ic_card", "debit_card");

    String mIconName;
    String mStringName;

    TypePaymentMethodEnum(String iconName, String stringName) {
        mIconName = iconName;
        mStringName = stringName;
    }

    public String getResourceName() {
        return mIconName;
    }

    public String getStringName() {
        return mStringName;
    }
}
