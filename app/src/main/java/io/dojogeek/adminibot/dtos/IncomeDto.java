package io.dojogeek.adminibot.dtos;

import org.joda.time.DateTime;

import java.io.Serializable;
import java.math.BigDecimal;

public class IncomeDto implements Serializable {

    private String name;
    private BigDecimal totalAmount;
    private DateTime nextEntry;
    private BigDecimal totalFoodCoupons;
    private BigDecimal totalCash;
    private DebitCardDto debitCard;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(BigDecimal totalAmount) {
        this.totalAmount = totalAmount;
    }

    public DateTime getNextEntry() {
        return nextEntry;
    }

    public void setNextEntry(DateTime nextEntry) {
        this.nextEntry = nextEntry;
    }

    public BigDecimal getTotalFoodCoupons() {
        return totalFoodCoupons;
    }

    public void setTotalFoodCoupons(BigDecimal totalFoodCoupons) {
        this.totalFoodCoupons = totalFoodCoupons;
    }

    public BigDecimal getTotalCash() {
        return totalCash;
    }

    public void setTotalCash(BigDecimal totalCash) {
        this.totalCash = totalCash;
    }

    public DebitCardDto getDebitCard() {
        return debitCard;
    }

    public void setDebitCard(DebitCardDto debitCard) {
        this.debitCard = debitCard;
    }
}
