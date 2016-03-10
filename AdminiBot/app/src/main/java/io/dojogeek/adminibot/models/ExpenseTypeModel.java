package io.dojogeek.adminibot.models;

import io.dojogeek.adminibot.enums.ExpenseTypeEnum;

public class ExpenseTypeModel {

    private long id;
    private ExpenseTypeEnum name;
    private String description;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public ExpenseTypeEnum getName() {
        return name;
    }

    public void setName(ExpenseTypeEnum name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
