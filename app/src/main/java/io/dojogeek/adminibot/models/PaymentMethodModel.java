package io.dojogeek.adminibot.models;

import org.joda.time.DateTime;

import io.dojogeek.adminibot.enums.TypePaymentMethodEnum;

public class PaymentMethodModel {

    private long id;
    private String name;
    private DateTime createdAt;
    private DateTime updatedAt;
    private TypePaymentMethodEnum type;

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

    public DateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(DateTime createdAt) {
        this.createdAt = createdAt;
    }

    public DateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(DateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    public TypePaymentMethodEnum getType() {
        return type;
    }

    public void setType(TypePaymentMethodEnum type) {
        this.type = type;
    }
}
