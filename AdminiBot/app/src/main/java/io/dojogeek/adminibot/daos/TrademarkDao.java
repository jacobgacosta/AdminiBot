package io.dojogeek.adminibot.daos;

import java.util.List;

import io.dojogeek.adminibot.models.TrademarkModel;

public interface TrademarkDao {

    List<TrademarkModel> getTrademarks();

    TrademarkModel getTrademarkById(long id);

}
