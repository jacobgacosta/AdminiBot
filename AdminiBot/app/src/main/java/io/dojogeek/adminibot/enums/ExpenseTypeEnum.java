package io.dojogeek.adminibot.enums;

public enum ExpenseTypeEnum {

    CLOTHES("clothes", "clothes"), ALCOHOL("alcohol", "drinks"), MEDICAL("medical", "medical"),
    LUXURIES("luxuries", "luxuries"), LIVING_PLACE("living place", "living place"),
    FOOD("food", "food"), OTHERS("others", "anything");

    private String mName;
    private String mDescription;

    ExpenseTypeEnum(String name, String description) {
        mName = name;
        mDescription = description;
    }

    public String getName() {
        return mName;
    }

    public String getDescription() {
        return mDescription;
    }
}
