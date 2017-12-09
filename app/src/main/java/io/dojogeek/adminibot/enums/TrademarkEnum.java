package io.dojogeek.adminibot.enums;

public enum TrademarkEnum {

    VISA("Visa", "ic_visa"), AMERICAN_EXPRESS("American Express", "ic_amex"),
    MASTER_CARD("MasterCard", "ic_mastercard");

    private String mImageName;
    private String mName;

    TrademarkEnum(String name, String imageName) {
        mName = name;
        mImageName = imageName;
    }

    public String getImageName() {
        return mImageName;
    }

    public String getName() {
        return mName;
    }

    public static String getTrademarkImageNameFromEnum(String name) {

        TrademarkEnum trademarkEnum = valueOf(name);

        return trademarkEnum.getImageName();
    }

}
