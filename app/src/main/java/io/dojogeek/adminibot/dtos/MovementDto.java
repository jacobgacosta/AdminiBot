package io.dojogeek.adminibot.dtos;

import java.io.Serializable;
import java.math.BigDecimal;

import io.dojogeek.adminibot.enums.TypePaymentMethodEnum;

public class MovementDto implements Serializable {

    private TypePaymentMethodEnum mType;
    private BigDecimal mTotal = new BigDecimal(0.0);
    private Object mExtra;

    public void setType(TypePaymentMethodEnum type) {
        this.mType = type;
    }

    public TypePaymentMethodEnum getType() {
        return this.mType;
    }

    public void setTotal(BigDecimal total) {
        this.mTotal = total;
    }

    public BigDecimal getTotal() {
        return this.mTotal;
    }

    public void setExtra(Object extra) {
        this.mExtra = extra;
    }

    public Object getExtra() {
        return this.mExtra;
    }

}
