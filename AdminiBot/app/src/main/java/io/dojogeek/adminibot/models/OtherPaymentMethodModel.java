package io.dojogeek.adminibot.models;

import io.dojogeek.adminibot.enums.TypePaymentMethodEnum;

public class OtherPaymentMethodModel {

    public long id;
    public String name;
    public String referenceNumber;
    public TypePaymentMethodEnum typePaymentMethod;
    public double availableCredit;
    public long userId;
}
