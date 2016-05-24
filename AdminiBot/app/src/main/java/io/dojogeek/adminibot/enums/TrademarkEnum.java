package io.dojogeek.adminibot.enums;

public enum TrademarkEnum {

    VISA("Visa", "ic_visa"), AMERICAN_EXPRESS("American Express", "ic_amex"),
    MASTERCARD("MasterCard", "ic_mc");

    private String mImageName;
    private String mName;

    private TrademarkEnum(String name, String imageName) {
        mImageName = name;
        mName = imageName;
    }

    public String getImageName() {
        return mImageName;
    }

    public String getName() {
        return mName;
    }
}
