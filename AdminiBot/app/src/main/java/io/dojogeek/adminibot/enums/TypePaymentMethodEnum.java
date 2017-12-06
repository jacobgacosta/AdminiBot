package io.dojogeek.adminibot.enums;

public enum TypePaymentMethodEnum {

    CASH("ic_cash", "msg_cash"),
    CREDIT_CARD("ic_card", "msg_credit_card"),
    DEBIT_CARD("ic_card", "msg_debit_card"),
    FOOD_COUPONS("ic_food_coupon", "msg_food_coupons"), ;

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
