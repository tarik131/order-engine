package com.test.service;

import com.test.model.BondOrder;
import com.test.model.Trade;
import java.util.List;
public interface TradeService {
    
    Trade createTrade(BondOrder bid, BondOrder offer, Integer quantity);
    List<Trade> getTrades(String ticker);
}
