package io.dojogeek.adminibot.views;


import java.math.BigDecimal;
import java.util.List;

import io.dojogeek.adminibot.dtos.DtoSimpleAdapter;

public interface MyCash {

    void listMyCash(List<DtoSimpleAdapter> cashList);

    void showTotalCash(BigDecimal total);
}
