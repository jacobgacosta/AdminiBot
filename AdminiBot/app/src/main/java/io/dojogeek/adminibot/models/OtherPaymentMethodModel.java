package io.dojogeek.adminibot.models;

import java.math.BigDecimal;

import io.dojogeek.adminibot.enums.TypePaymentMethodEnum;

public class OtherPaymentMethodModel {

    private long id;
    private String name;
    private String referenceNumber;
    private TypePaymentMethodEnum typePaymentMethod;
    private BigDecimal availableCredit;
    private long userId;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getReferenceNumber() {
        return referenceNumber;
    }

    public void setReferenceNumber(String referenceNumber) {
        this.referenceNumber = referenceNumber;
    }

    public TypePaymentMethodEnum getTypePaymentMethod() {
        return typePaymentMethod;
    }

    public void setTypePaymentMethod(TypePaymentMethodEnum typePaymentMethod) {
        this.typePaymentMethod = typePaymentMethod;
    }

    public BigDecimal getAvailableCredit() {
        return availableCredit;
    }

    public void setAvailableCredit(BigDecimal availableCredit) {
        this.availableCredit = availableCredit;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }
}
