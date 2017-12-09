package io.dojogeek.adminibot.views;

import java.math.BigDecimal;

public interface PaymentMethods {

    void refreshTotalIncome(BigDecimal amount);
}
