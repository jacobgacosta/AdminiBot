package io.dojogeek.adminibot.models;

import org.joda.time.DateTime;

import java.math.BigDecimal;

public class IncomeModel {

    private long id;
    private String name;
    private DateTime updateAt;
    private DateTime createdAt;
    private DateTime nextEntry;
    private BigDecimal totalAmount;

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

    public DateTime getUpdateAt() {
        return updateAt;
    }

    public void setUpdateAt(DateTime updateAt) {
        this.updateAt = updateAt;
    }

    public DateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(DateTime createdAt) {
        this.createdAt = createdAt;
    }

    public DateTime getNextEntry() {
        return nextEntry;
    }

    public void setNextEntry(DateTime nextEntry) {
        this.nextEntry = nextEntry;
    }

    public BigDecimal getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(BigDecimal totalAmount) {
        this.totalAmount = totalAmount;
    }
}
