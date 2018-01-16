package io.dojogeek.adminibot.dtos;

import java.io.Serializable;

public class DebitCardDto implements Serializable {

    private String mName;
    private String mNumber;
    private String mAmount;


    public String getName() {
        return mName;
    }

    public void setName(String name) {
        this.mName = name;
    }

    public String getNumber() {
        return mNumber;
    }

    public void setNumber(String number) {
        this.mNumber = number;
    }

    public String getAmount() {
        return mAmount;
    }

    public void setAmount(String total) {
        this.mAmount = total;
    }

}
